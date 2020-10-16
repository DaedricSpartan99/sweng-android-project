package com.github.ancarola.bootcamp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import java.nio.channels.NoConnectionPendingException;
import java.security.NoSuchProviderException;
import java.security.Provider;

public class AndroidLocationService implements LocationService {

    private static final long LOCATION_REFRESH_TIME = 3000;
    private static final float LOCATION_MIN_DISTANCE = 40.0f;
    public static final int PERMISSION_REQUEST_CODE = 0x88;

    LocationManager locman;
    Context context;

    // define a location listener
    private final LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public AndroidLocationService(Context context) {

        locman = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.context = context;
    }

    @SuppressLint("MissingPermission")
    @Override
    public Location get()  {

        if (!hasPermission()) {
            return null;
        }
        
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        criteria.setCostAllowed(false);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);

        String provider = locman.getBestProvider(criteria, true);

        android.location.Location loc = locman.getLastKnownLocation(provider);

        if (loc == null) {  // ask new location

            try {
                locman.requestLocationUpdates(provider, LOCATION_REFRESH_TIME, LOCATION_MIN_DISTANCE, locListener);
                loc = locman.getLastKnownLocation(provider);
            } catch (NoConnectionPendingException ex) {
                ex.printStackTrace();
            }

            if (loc == null) {
                System.err.println("Could not find the current location");
                return null;
            }
        }

        return new Location((float)loc.getLatitude(), (float)loc.getLongitude());
    }

    @Override
    public boolean hasPermission() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean isEnabled() {
        return true; // TODO
    }

    @Override
    public boolean permissionRequest(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
        return hasPermission(); // return success
    }

    @Override
    public boolean enableRequest() {
        return false;
    }



}
