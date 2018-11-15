package com.yts.smartsetting;

import androidx.lifecycle.ViewModelProviders;

import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.transition.TransitionInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.gun0912.tedpermission.PermissionListener;
import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.data.model.Location;
import com.yts.smartsetting.data.realm.RealmService;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.PermissionCheck;
import com.yts.smartsetting.utill.RequestCode;
import com.yts.smartsetting.utill.SendBroadcast;
import com.yts.smartsetting.utill.ServiceUtil;
import com.yts.smartsetting.utill.SharedPrefsUtils;
import com.yts.smartsetting.utill.ShowIntent;
import com.yts.smartsetting.utill.ToastMake;
import com.yts.smartsetting.view.ui.dialog.AlertDialogCreate;
import com.yts.smartsetting.view.ui.dialog.LocationDialog;
import com.yts.smartsetting.view.ui.dialog.LocationListDialog;
import com.yts.smartsetting.view.viewmodel.BaseViewModel;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;


public class BaseActivity extends AppCompatActivity implements BaseCallback {
    private BaseViewModel model;
    public InputMethodManager inputMethodManager;
    public CompositeDisposable compositeDisposable;
    //backpress
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(this).get(BaseViewModel.class);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        compositeDisposable = new CompositeDisposable();

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void onBackPressed() { //두번클릭해야 종료
        int childFragmentSize = getSupportFragmentManager().getFragments().size();
        if (childFragmentSize > 0) { //5개인 이유? MainFragment 4개 + SupportRequestManagerFragment{6d43a4c #5 com.bumptech.glide.manager}{parent=null} 이게 추가되어서.. glide module 떄문에 생성되는건가? 총 5개
            super.onBackPressed();
        } else {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            } else {
                ToastMake.make(this, getString(R.string.msg_quit));
            }
            mBackPressed = System.currentTimeMillis();
        }
    }


    /**
     * 다이얼로그 시작
     */

    /**
     * 다이얼로그 시작
     */

    public void startFragmentDialog(DialogFragment dialogFragment, int transitionId) {
        String fragmentTag = dialogFragment.getClass().getSimpleName();

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogFragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(transitionId));
        }
        fragmentManager.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true)
                .addToBackStack(fragmentTag)
                .replace(android.R.id.content, dialogFragment)
                .commit();
    }

    public void addFragmentDialog(DialogFragment dialogFragment, int transitionId) {
        String fragmentTag = dialogFragment.getClass().getSimpleName();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogFragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(transitionId));
        }
        fragmentManager.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true)
                .addToBackStack(fragmentTag)
                .add(android.R.id.content, dialogFragment)
                .commit();
    }

    @Override
    public void close() {
        hideKeyboard();
        onBackPressed();
    }

    public void hideKeyboard() { //키보드 가리는 함수
        if (inputMethodManager != null && getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void toast(String text) {
        ToastMake.make(this, text);
    }

    @Override
    public void toast(int textId) {
        ToastMake.make(this, textId);
    }


    @Override
    public void save(String kind, ResolveInfo resolveInfo) {
        if (resolveInfo != null) {
            if (kind.equals(Keys.EAR)) {
                String name = resolveInfo.activityInfo.loadLabel(getPackageManager()).toString();
                String packageName = resolveInfo.activityInfo.packageName;
                SharedPrefsUtils.setStringPreference(this, Keys.EAR_NAME, name);
                SharedPrefsUtils.setStringPreference(this, Keys.EAR_PACKAGENAME, packageName);
                SendBroadcast.earEdit(this);
            } else if (kind.equals(Keys.BLUE_TOOTH)) {
                String name = resolveInfo.activityInfo.loadLabel(getPackageManager()).toString();
                String packageName = resolveInfo.activityInfo.packageName;
                SharedPrefsUtils.setStringPreference(this, Keys.BLUE_TOOTH_NAME, name);
                SharedPrefsUtils.setStringPreference(this, Keys.BLUE_TOOTH_PACKAGENAME, packageName);
                SendBroadcast.blueToothEdit(this);
            }
        }
    }

    @Override
    public void save(Location location) {
        SendBroadcast.editLocation(this);
    }

    @Override
    public void deleteLocation(final Location location) {
        AlertDialogCreate.getInstance(this).deleteLocation(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RealmService.deleteLocation(location);
                SendBroadcast.editLocation(BaseActivity.this);
            }
        });
    }

    @Override
    public void saveEnable(String kind, boolean enable) {
        if (kind.equals(Keys.EAR)) {
            SharedPrefsUtils.setBooleanPreference(this, Keys.EAR_ENABLE, enable);
        } else if (kind.equals(Keys.BLUE_TOOTH)) {
            SharedPrefsUtils.setBooleanPreference(this, Keys.BLUE_TOOTH_ENABLE, enable);
        } else if (kind.equals(Keys.LOCATION)) {
            SharedPrefsUtils.setBooleanPreference(this, Keys.LOCATION_ENABLE, enable);
        }
        ServiceUtil.start(this);
    }

    @Override
    public void startLocationListDialog() {
        PermissionCheck.loactionCheck(this, new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (notificationManager != null) {
                    if (Build.VERSION.SDK_INT >= 24 && !notificationManager.isNotificationPolicyAccessGranted()) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                        startActivity(intent);
                        ToastMake.make(BaseActivity.this, R.string.msg_sound_mode);
                        return;
                    }
                }
                LocationListDialog dialog = LocationListDialog.newInstance();
                startFragmentDialog(dialog, android.R.transition.slide_right);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                ToastMake.make(BaseActivity.this, R.string.error_permission);
            }
        });

    }

    @Override
    public void startLocationDialog(Location location) {
        LocationDialog dialog = LocationDialog.newInstance(location);
        startFragmentDialog(dialog, android.R.transition.slide_bottom);
    }

    @Override
    public void startSelectLocation() {
        PermissionCheck.loactionCheck(this, new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                ShowIntent.location(BaseActivity.this, RequestCode.SELECT_LOCATION);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        });
    }

    @Override
    public void invite() {
        ShowIntent.invite(this);
    }

    @Override
    public void contact() {
        ShowIntent.emailSend(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == RequestCode.SELECT_LOCATION) {
                Place place = PlacePicker.getPlace(this, data);
                SendBroadcast.place(this, Keys.SELECT_LOCATION, place);
            }
        }
    }
}
