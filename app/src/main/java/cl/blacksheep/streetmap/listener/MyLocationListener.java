package cl.blacksheep.streetmap.listener;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.activity.MapsActivity;
import cl.blacksheep.streetmap.activity.utils.MapsActivityUtils;

/**
 * Created by elsan on 27-03-2018.
 */

public class MyLocationListener implements LocationListener {

    Activity activity;

    public MyLocationListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onLocationChanged(Location location) {
        MapsActivity.latitud = location.getLatitude();
        MapsActivity.longitud = location.getLatitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        System.out.println("onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        activarGPS(this.activity);
    }

    @Override
    public void onProviderDisabled(String provider) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.activity);
        dialog.setTitle("Activar Ubicacion");
        dialog.setMessage("¿Desea activar la ubicacion?");
        MyButtonLocationListener myButtonLocationListener = new MyButtonLocationListener(this.activity);
        dialog.setPositiveButton(android.R.string.yes, myButtonLocationListener);
        dialog.setNegativeButton(android.R.string.no, null);
        AlertDialog alertDialog = dialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(this.activity.getResources().getColor(R.color.colorPrimary));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(this.activity.getResources().getColor(R.color.colorPrimary));

    }

    public static void activarGPS(Activity activity) {
        MapsActivity.locationManager = (LocationManager) activity.getApplicationContext().getSystemService(activity.LOCATION_SERVICE);
        List<String> providers = MapsActivity.locationManager.getProviders(false);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(activity, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);
                return;
            }
            Location l = MapsActivity.locationManager.getLastKnownLocation(provider);
            if(l == null)
            {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        if(bestLocation != null) {
            MapsActivity.longitud = bestLocation.getLongitude();
            MapsActivity.latitud = bestLocation.getLatitude();
            LocationListener listener = new MyLocationListener(activity);
            MapsActivity.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, listener);
            LatLng latLng = new LatLng(MapsActivity.latitud, MapsActivity.longitud);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            MapsActivity.mMap.animateCamera(cameraUpdate);
            MapsActivityUtils mapsActivityUtils = new MapsActivityUtils(activity);
            Marker marker = MapsActivity.mMap.addMarker(new MarkerOptions().position(latLng));
            marker.setIcon(mapsActivityUtils.bitmapDescriptorFromVector(activity.getApplicationContext(), R.drawable.ubicacion));
        }
        else
        {
            LocationListener listener = new MyLocationListener(activity);
            MapsActivity.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, listener);
            LatLng latLng = new LatLng(MapsActivity.latitud, MapsActivity.longitud);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(MapsActivity.latitud, MapsActivity.longitud)).zoom(10).build();
            MapsActivity.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}
