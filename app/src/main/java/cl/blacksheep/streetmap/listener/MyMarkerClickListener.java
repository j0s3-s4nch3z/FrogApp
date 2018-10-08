package cl.blacksheep.streetmap.listener;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.activity.MapsActivity;
import cl.blacksheep.streetmap.activity.utils.MapsActivityUtils;
import cl.blacksheep.streetmap.dao.StopDao;
import cl.blacksheep.streetmap.dao.impl.StopDaoImpl;
import cl.blacksheep.streetmap.dto.Route;
import cl.blacksheep.streetmap.utils.Utilidades;

/**
 * Created by elsan on 01-05-2018.
 */

public class MyMarkerClickListener implements GoogleMap.OnMarkerClickListener {

    Activity activity;
    MapsActivityUtils mapsActivityUtils;

    public MyMarkerClickListener(Activity activity)
    {
        this.activity = activity;
        mapsActivityUtils = new MapsActivityUtils(this.activity);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(!marker.getTitle().startsWith("BUS")) {

            Utilidades.DATA_ROUTE = true;
            String title = marker.getTitle();
            final String[] data = title.split("-");
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        StopDao routeDao = new StopDaoImpl();
                        routeDao.getStop(data[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Utilidades.DATA_ROUTE = false;
                }
            };
            Thread thread = new Thread(run);
            thread.start();
            while (Utilidades.DATA_ROUTE)
            {
                System.out.println("22222222222222222222222");
            }
            StringBuilder cadena = new StringBuilder();
            JSONArray routes = null;
            try {
                routes = (JSONArray) Utilidades.STOP.get("stop_routes");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < routes.length(); i++) {
                String route = null;
                try {
                    route = routes.get(i).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cadena.append(route.split("-")[0]);
                cadena.append(" ");
            }
            marker.setSnippet(cadena.toString());
        }
        marker.showInfoWindow();
        try {
            double lat = Utilidades.STOP.getDouble("stop_lat");
            double lon = Utilidades.STOP.getDouble("stop_lon");
            LatLng coordinate = new LatLng(lat, lon);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLng(coordinate);
            MapsActivity.mMap.animateCamera(yourLocation);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }
}
