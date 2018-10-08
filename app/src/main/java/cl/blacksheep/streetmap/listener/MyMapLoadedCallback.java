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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;

import org.json.JSONException;

import java.util.List;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.activity.MapsActivity;
import cl.blacksheep.streetmap.activity.utils.MapsActivityUtils;

/**
 * Created by elsan on 06-07-2018.
 */

public class MyMapLoadedCallback implements GoogleMap.OnMapLoadedCallback {

    Activity activity;

    public MyMapLoadedCallback(Activity activity)
    {
        this.activity = activity;
    }
    @Override
    public void onMapLoaded() {
        MyCameraChangeListener myCameraChangeListener = new MyCameraChangeListener(this.activity);
        MapsActivity.mMap.setOnCameraIdleListener(myCameraChangeListener);
        MapsActivity.mMap.setOnCameraMoveListener(myCameraChangeListener);
        MapsActivity.mMap.setOnCameraMoveStartedListener(myCameraChangeListener);
        MapsActivity.mMap.setOnCameraMoveCanceledListener(myCameraChangeListener);



        // Add a marker in Sydney and move the camera
        MapsActivityUtils mapsActivityUtils = new MapsActivityUtils(this.activity);
        MyMarkerClickListener myMarkerClickListener = new MyMarkerClickListener(this.activity);
        MapsActivity.mMap.setOnMarkerClickListener(myMarkerClickListener);
        /*try {
            //CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(MapsActivity.latitud, MapsActivity.longitud)).zoom(17).build();
            //MapsActivity.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
