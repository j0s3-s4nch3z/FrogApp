package cl.blacksheep.streetmap.listener;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;

import org.json.JSONException;

import cl.blacksheep.streetmap.activity.MapsActivity;
import cl.blacksheep.streetmap.activity.utils.MapsActivityUtils;
import cl.blacksheep.streetmap.utils.Utilidades;

/**
 * Created by elsan on 04-07-2018.
 */

public class MyCameraChangeListener implements GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveCanceledListener{

    Activity activity;

    public MyCameraChangeListener(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onCameraMoveStarted(int i) {
    }

    @Override
    public void onCameraIdle() {
        CameraPosition cameraPosition = MapsActivity.mMap.getCameraPosition();
        if(cameraPosition.zoom < 15.0) {
            //MapsActivity.mMap.clear();
            Toast.makeText(this.activity.getApplicationContext(),"Acerque el mapa para ver paraderos",Toast.LENGTH_SHORT).show();
        } else {
            MapsActivityUtils  mapsActivityUtils = new MapsActivityUtils(null);
            try {
                mapsActivityUtils.dibujarParaderos(this.activity.getApplicationContext());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onCameraMove() {

    }

    @Override
    public void onCameraMoveCanceled() {

    }
}
