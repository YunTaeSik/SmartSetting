package com.yts.smartsetting.service;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.yts.smartsetting.R;
import com.yts.smartsetting.data.model.Location;
import com.yts.smartsetting.data.realm.RealmService;
import com.yts.smartsetting.receiver.ServiceReceiver;
import com.yts.smartsetting.utill.Distance;
import com.yts.smartsetting.utill.Keys;
import com.yts.smartsetting.utill.Register;
import com.yts.smartsetting.utill.SharedPrefsUtils;
import com.yts.smartsetting.utill.Turn;
import com.yts.smartsetting.view.ui.activity.MainActivity;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class Service extends android.app.Service {
    private ServiceReceiver serviceReceiver;
    private final static int NOTIFICATION_ID = 111;
    private CompositeDisposable compositeDisposable;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        compositeDisposable = new CompositeDisposable();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Intent main = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, main, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                    .setSmallIcon(R.drawable.ic_settings_white_24dp)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(getString(R.string.app_in_use))
                    .setContentText(getString(R.string.app_content))
                    .build();
            startForeground(NOTIFICATION_ID, notification);
        }

        settingEarBlueTooth();
        settingLocation();


        return super.onStartCommand(intent, flags, startId);
    }

    private void settingEarBlueTooth() {
        /*이어폰, 블루투스*/
        serviceReceiver = new ServiceReceiver();
        Register.plug(this, serviceReceiver);
    }

    private void settingLocation() {
        /*위치*/
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest
                    .setInterval(1000 * 60 * 10) //10분
                    .setInterval(1000) //10분
                    .setFastestInterval(0)
                    .setSmallestDisplacement(Keys.DISTANCE_METER) //100m
                    .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());
        }
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(final LocationResult locationResult) {
            boolean enable = SharedPrefsUtils.getBooleanPreference(Service.this, Keys.LOCATION_ENABLE);
            Log.d("onLocationResult", "onLocationResult");
            if (enable) {
                compositeDisposable.add(RealmService.getLocationList().subscribe(new Consumer<List<Location>>() {
                    @Override
                    public void accept(List<Location> locationList) throws Exception {
                        for (Location location : locationList) {
                            if (Distance.get(locationResult.getLastLocation(), location)) {
                                boolean isArriveBlueTooth = location.isArriveBlueTooth();
                                Turn.blueTooth(isArriveBlueTooth);
                                boolean isArriveWifi = location.isArriveWifi();
                                Turn.wifi(getApplicationContext(), isArriveWifi);

                                int soundMode = location.getArriveSoundMode();
                                Turn.soundMode(getApplicationContext(), soundMode);
                            } else {
                                boolean isLeaveBlueTooth = location.isLeaveBlueTooth();
                                Turn.blueTooth(isLeaveBlueTooth);
                                boolean isLeaveWifi = location.isLeaveWifi();
                                Turn.wifi(getApplicationContext(), isLeaveWifi);

                                int soundMode = location.getLeaveSoundMode();
                                Turn.soundMode(getApplicationContext(), soundMode);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        Crashlytics.logException(throwable);
                    }
                }));

            }
            super.onLocationResult(locationResult);
        }
    };

    @Override
    public void onDestroy() {
        try {
            if (serviceReceiver != null) {
                getApplicationContext().unregisterReceiver(serviceReceiver);
            }
            if (mFusedLocationProviderClient != null && locationCallback != null) {
                mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
            }
            if (compositeDisposable != null) {
                compositeDisposable.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
