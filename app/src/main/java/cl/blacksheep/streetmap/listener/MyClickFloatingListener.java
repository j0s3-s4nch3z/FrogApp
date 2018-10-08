package cl.blacksheep.streetmap.listener;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.activity.MapsActivity;
import cl.blacksheep.streetmap.activity.utils.MapsActivityUtils;
import cl.blacksheep.streetmap.dto.Route;

/**
 * Created by elsan on 01-05-2018.
 */

public class MyClickFloatingListener implements View.OnClickListener {

    Activity activity;
    MapsActivityUtils mapsActivityUtils;

    public MyClickFloatingListener(Activity activity) {
        this.activity = activity;
        mapsActivityUtils = new MapsActivityUtils(this.activity);
    }

    @Override
    public void onClick(View v) {
        FloatingActionButton floatingActionButton = (FloatingActionButton) v;
        if (floatingActionButton.getId() == R.id.floatingActionButton) {
            AutoCompleteTextView textView = (AutoCompleteTextView) activity.findViewById(R.id.multiAutoCompleteTextView);
            if (Route.ESTADO.equals(Route.SENTIDO_IDA)) {
                ((FloatingActionButton) v).setVisibility(View.VISIBLE);
                Route.ESTADO = Route.SENTIDO_VUELTA;
            } else {
                ((FloatingActionButton) v).setVisibility(View.VISIBLE);
                Route.ESTADO = Route.SENTIDO_IDA;
            }
            String recorrido = ((TextView) textView).getText().toString().toLowerCase();
            mapsActivityUtils.dibujarRecorrido(recorrido, Route.ESTADO);
            mapsActivityUtils.hideSoftKeyBoard();
        } else if (floatingActionButton.getId() == R.id.floatingActionButton3) {
            LocationListener listener = new MyLocationListener(this.activity);
            if (ActivityCompat.checkSelfPermission(this.activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this.activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            MapsActivity.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, listener);
            LatLng latLng = new LatLng(MapsActivity.latitud, MapsActivity.longitud);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,17.0f);
            MapsActivity.mMap.animateCamera(cameraUpdate);
        }
    }
}
