package com.example.cs125final_unnamed;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

public class GPS_SERVICE extends Service {
    private LocationListener listener;
    private LocationManager locationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service setup");

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                System.out.println("location update sent");
                Intent i = new Intent("location_update");
                i.putExtra("coords", location.getLatitude() + "," + location.getLongitude());
                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,1000, 0, listener);
        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 1000, 0,listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (locationManager != null) {
            locationManager.removeUpdates(listener);
        }
    }
}
