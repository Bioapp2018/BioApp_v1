package com.example.alberwills.bioapp_v1;

import android.location.LocationManager;

/**
 * Created by alberwills on 27/3/18.
 */

public class UtilidadesGPS
{
    LocationManager locationManager;


    public boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
