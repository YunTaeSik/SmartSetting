package com.yts.smartsetting;

import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.inputmethod.InputMethodManager;

import com.yts.smartsetting.callback.BaseCallback;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.SendBroadcast;
import com.yts.smartsetting.utill.SharedPrefsUtils;
import com.yts.smartsetting.utill.ToastMake;

import io.reactivex.disposables.CompositeDisposable;


public class BaseActivity extends AppCompatActivity implements BaseCallback {
    public InputMethodManager inputMethodManager;

    public CompositeDisposable compositeDisposable;

    //backpress
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void save(String kind, ResolveInfo resolveInfo) {
        if (resolveInfo != null) {
            if (kind.equals(Keys.EAR)) {
                String name = resolveInfo.activityInfo.loadLabel(getPackageManager()).toString();
                String packageName = resolveInfo.activityInfo.packageName;
                SharedPrefsUtils.setStringPreference(this, Keys.EAR_NAME, name);
                SharedPrefsUtils.setStringPreference(this, Keys.EAR_PACKAGENAME, packageName);
                SendBroadcast.earEdit(this);
            }
        }
    }
}
