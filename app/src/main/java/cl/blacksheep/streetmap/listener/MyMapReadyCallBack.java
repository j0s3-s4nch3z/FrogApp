package cl.blacksheep.streetmap.listener;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.util.List;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.activity.MapsActivity;
import cl.blacksheep.streetmap.activity.utils.MapsActivityUtils;
import cl.blacksheep.streetmap.adapter.MyCustomInfoWindowAdapter;
import cl.blacksheep.streetmap.utils.Utilidades;

/**
 * Created by elsan on 01-05-2018.
 */

public class MyMapReadyCallBack implements OnMapReadyCallback {

    Activity activity;

    public  MyMapReadyCallBack(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsActivity.mMap = googleMap;
        MapsActivity.mMap.getUiSettings().setMyLocationButtonEnabled(false);
        boolean success =  googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                this.activity, R.raw.map_style));
        MyCameraChangeListener myCameraChangeListener = new MyCameraChangeListener(this.activity);
        MapsActivity.mMap.setOnCameraIdleListener(myCameraChangeListener);
        MapsActivity.mMap.setOnCameraMoveListener(myCameraChangeListener);
        MapsActivity.mMap.setOnCameraMoveStartedListener(myCameraChangeListener);
        MapsActivity.mMap.setOnCameraMoveCanceledListener(myCameraChangeListener);
        MapsActivity.mMap.setInfoWindowAdapter(new MyCustomInfoWindowAdapter(this.activity));

        if (ContextCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this.activity, Manifest.permission.ACCESS_COARSE_LOCATION))
            {
                ActivityCompat.requestPermissions(this.activity, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);
                MyLocationListener.activarGPS(this.activity);
            }

            return;
        } else {
            MapsActivity.mMap.getUiSettings().setMyLocationButtonEnabled(false);
            MyLocationListener.activarGPS(this.activity);
        }

        MapsActivityUtils mapsActivityUtils = new MapsActivityUtils(this.activity);
        MyMarkerClickListener myMarkerClickListener = new MyMarkerClickListener(this.activity);
        MapsActivity.mMap.setOnMarkerClickListener(myMarkerClickListener);
        MyMapLoadedCallback myMapLoadedCallback = new MyMapLoadedCallback(this.activity);
        MapsActivity.mMap.setOnMapLoadedCallback(myMapLoadedCallback);
        MyInfoWindowClickListener myInfoWindowClickListener = new MyInfoWindowClickListener(this.activity);
        MapsActivity.mMap.setOnInfoWindowClickListener(myInfoWindowClickListener);

       // try {
         //   mapsActivityUtils.dibujarParaderos(this.activity.getApplicationContext());

        //} catch (JSONException e) {
        //    e.printStackTrace();
        //}

    }
}
