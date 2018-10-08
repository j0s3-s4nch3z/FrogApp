package cl.blacksheep.streetmap.activity.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.activity.MapsActivity;
import cl.blacksheep.streetmap.dao.RouteDao;
import cl.blacksheep.streetmap.dao.StopDao;
import cl.blacksheep.streetmap.dao.impl.RouteDaoImpl;
import cl.blacksheep.streetmap.dao.impl.StopDaoImpl;
import cl.blacksheep.streetmap.dto.Route;
import cl.blacksheep.streetmap.dto.Shape;
import cl.blacksheep.streetmap.dto.Stop;
import cl.blacksheep.streetmap.listener.MyCameraChangeListener;
import cl.blacksheep.streetmap.listener.MyLocationListener;
import cl.blacksheep.streetmap.listener.MyMapLoadedCallback;
import cl.blacksheep.streetmap.listener.MyMarkerClickListener;
import cl.blacksheep.streetmap.utils.Utilidades;

/**
 * Created by elsan on 01-05-2018.
 */

public class MapsActivityUtils {

    Activity activity;

    public MapsActivityUtils(Activity activity)
    {
        this.activity = activity;
    }

    public void dibujarRecorrido(final String recorrido, final String sentido) {
         new Thread(new Runnable() {
            @Override
            public void run() {
                RouteDao routeDao = new RouteDaoImpl();
                routeDao.getRoute(recorrido, sentido);
            }
        }).start();
            do {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (Utilidades.ROUTE == null);
            MapsActivity.mMap.clear();
            PolylineOptions rectOptions = new PolylineOptions();
            boolean enpezar = false;
            String latAux = "";
            String lonAux = "";
            try {
                LatLng a = null;
                LatLng b = null;
                JSONArray shapes = (JSONArray) Utilidades.ROUTE.get("route_shapes");
                boolean latASpecial = true;
                for (int i = 0; i < shapes.length(); i++) {
                    JSONObject shape = shapes.getJSONObject(i);
                    //for (Shape shape : Utilidades.ROUTE.getShapes()) {
                    if (!Utilidades.ROUTE.get("route_sequence").toString().equals("")) {
                        Route.ESPECIAL = true;
                        if (Route.ESTADO.equals(Route.SENTIDO_IDA)) {
                            double lat = Double.parseDouble(shape.getString("shape_pt_lat"));
                            double lon = Double.parseDouble(shape.getString("shape_pt_lon"));
                            if(i == 0)
                            {
                                a = new LatLng(lat,lon);
                            }
                            rectOptions.add(new LatLng(lat, lon));
                            if (Utilidades.ROUTE.getString("route_sequence").equals(shape.getString("shape_pt_sequence"))) {
                                b = new LatLng(lat,lon);
                                break;
                            }
                        } else if (Route.ESTADO.equals(Route.SENTIDO_VUELTA)) {
                            if (Utilidades.ROUTE.getString("route_sequence").equals(shape.getString("shape_pt_sequence"))) {
                                enpezar = true;
                            }
                            if (enpezar) {
                                double lat = shape.getDouble("shape_pt_lat");
                                double lon = shape.getDouble("shape_pt_lon");
                                if(latASpecial)
                                {
                                    a = new LatLng(lat,lon);
                                }
                                latASpecial = false;
                                if(i == shapes.length() -1)
                                {
                                    b = new LatLng(lat,lon);
                                }
                                if (latAux.equals("") && Route.ESPECIAL) {
                                    latAux = lat + "";
                                    lonAux = lon + "";
                                }
                                rectOptions.add(new LatLng(lat, lon));
                            }

                        }
                    } else
                        {
                            Route.ESPECIAL = false;
                            double lat = Double.parseDouble(shape.getString("shape_pt_lat"));
                            double lon = Double.parseDouble(shape.getString("shape_pt_lon"));
                            if(i == 0)
                            {
                            a = new LatLng(lat,lon);
                            }
                            if(i == shapes.length() -1)
                            {
                            b = new LatLng(lat,lon);
                            }
                            rectOptions.add(new LatLng(lat, lon));
                        }

                            }
                int color = Color.parseColor("#" + Utilidades.ROUTE.getString("route_color"));
                rectOptions.width(15).color(color);
                Polyline polyline = MapsActivity.mMap.addPolyline(rectOptions);
                if (Route.ESPECIAL && latAux.length() > 0) {
                    MapsActivity.mMap.addMarker(new MarkerOptions().position(a).title("A"));
                    LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();
                    latLngBounds.include(a).include(b);
                    MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(),300));
                } else {
                    JSONArray jsonArray = Utilidades.ROUTE.getJSONArray("route_shapes");
                    String latA = jsonArray.getJSONObject(0).get("shape_pt_lat").toString();
                    String lonA = jsonArray.getJSONObject(0).get("shape_pt_lon").toString();
                    MapsActivity.mMap.addMarker(new MarkerOptions().position(a).title("A"));
                    LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();
                    latLngBounds.include(a).include(b);
                    MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(),300));
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
    }

    public void dibujarMetro(final String recorrido) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RouteDao routeDao = new RouteDaoImpl();
                routeDao.getMetro(recorrido);
            }
        }).start();
        do {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (Utilidades.ROUTE == null);
        MapsActivity.mMap.clear();
        PolylineOptions rectOptions = new PolylineOptions();
        boolean enpezar = false;
        String latAux = "";
        String lonAux = "";
        try {
            LatLng a = null;
            LatLng b = null;
            JSONArray shapes = (JSONArray) Utilidades.ROUTE.get("route_shapes");
            for (int i = 0; i < shapes.length(); i++) {
                JSONObject shape = shapes.getJSONObject(i);
                //for (Shape shape : Utilidades.ROUTE.getShapes()) {
                double lat = Double.parseDouble(shape.getString("shape_pt_lat"));
                double lon = Double.parseDouble(shape.getString("shape_pt_lon"));
                rectOptions.add(new LatLng(lat, lon));
                if(i == 0)
                {
                    a = new LatLng(lat,lon);
                }
                if(i == shapes.length() -1)
                {
                    b = new LatLng(lat,lon);
                }
            }
            int color = Color.parseColor("#" + Utilidades.ROUTE.getString("route_color"));
            rectOptions.width(15).color(color);
            Polyline polyline = MapsActivity.mMap.addPolyline(rectOptions);
            JSONArray jsonArray = Utilidades.ROUTE.getJSONArray("route_shapes");
            String latA = jsonArray.getJSONObject(0).get("shape_pt_lat").toString();
            String lonA = jsonArray.getJSONObject(0).get("shape_pt_lon").toString();
            MapsActivity.mMap.addMarker(new MarkerOptions().position(a).title("A"));
            LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();
            latLngBounds.include(a).include(b);
            MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(),300));
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
    public void dibujarMetroTren(final String recorrido) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RouteDao routeDao = new RouteDaoImpl();
                routeDao.getMetroTren(recorrido);
            }
        }).start();
        do {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (Utilidades.ROUTE == null);
        MapsActivity.mMap.clear();
        PolylineOptions rectOptions = new PolylineOptions();
        boolean enpezar = false;
        String latAux = "";
        String lonAux = "";
        try {
            LatLng a = null;
            LatLng b = null;
            JSONArray shapes = (JSONArray) Utilidades.ROUTE.get("route_shapes");
            for (int i = 0; i < shapes.length(); i++) {
                JSONObject shape = shapes.getJSONObject(i);
                //for (Shape shape : Utilidades.ROUTE.getShapes()) {
                double lat = Double.parseDouble(shape.getString("shape_pt_lat"));
                double lon = Double.parseDouble(shape.getString("shape_pt_lon"));
                rectOptions.add(new LatLng(lat, lon));
                if(i == 0)
                {
                    a = new LatLng(lat,lon);
                }
                if(i == shapes.length() -1)
                {
                    b = new LatLng(lat,lon);
                }
            }
            int color = Color.parseColor("#" + Utilidades.ROUTE.getString("route_color"));
            rectOptions.width(15).color(color);
            Polyline polyline = MapsActivity.mMap.addPolyline(rectOptions);
            JSONArray jsonArray = Utilidades.ROUTE.getJSONArray("route_shapes");
            String latA = jsonArray.getJSONObject(0).get("shape_pt_lat").toString();
            String lonA = jsonArray.getJSONObject(0).get("shape_pt_lon").toString();
            MapsActivity.mMap.addMarker(new MarkerOptions().position(a).title("A"));
            LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();
            latLngBounds.include(a).include(b);
            MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds.build(),300));
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void hideSoftKeyBoard()
    {
        View view = this.activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changeBarColor(Window window)
    {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this.activity.getApplicationContext(), R.color.fondo_splash));
    }

    public void dibujarParaderos(Context context) throws JSONException {
        if(Utilidades.DIBUJAR_PARADEROS) {
            VisibleRegion visibleRegion = MapsActivity.mMap.getProjection().getVisibleRegion();
            LatLngBounds latLngBounds = LatLngBounds.builder().include(visibleRegion.latLngBounds.northeast).include(visibleRegion.latLngBounds.southwest).build();
            if (Utilidades.STOPS != null) {
                for (int i = 0; i < Utilidades.STOPS.length(); i++) {
                    JSONObject stop = ((JSONObject) Utilidades.STOPS.get(i));
                    String latA = stop.getString("stop_lat");
                    String lonA = stop.getString("stop_lon");
                    LatLng a = new LatLng(Double.parseDouble(latA), Double.parseDouble(lonA));
                    if (latLngBounds.contains(a)) {
                        String name = stop.getString("stop_name");
                        MarkerOptions markerOption = new MarkerOptions().position(a).title(name);
                        boolean isMetro = true;
                        int id = 0;
                        try
                        {
                            id = Integer.parseInt(stop.getString("stop_id"));
                        }
                        catch (NumberFormatException e)
                        {
                            isMetro = false;
                        }
                        if(isMetro)
                        {
                            markerOption.icon(bitmapDescriptorFromVector(context, R.drawable.metro));
                        }
                        else
                        {
                            markerOption.icon(bitmapDescriptorFromVector(context, R.drawable.recorrido));
                        }
                        MapsActivity.mMap.addMarker(markerOption);
                    }
                }
            }
        }
    }

    public BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context,vectorDrawableResourceId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public static void localizarParadero(final String paradero)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StopDao stopDao = new StopDaoImpl();
                stopDao.getStop(paradero.toUpperCase());
            }
        }).start();
        while (Utilidades.STOP == null);
        String latA = null;
        String lonA = null;
        try {
            latA = Utilidades.STOP.get("stop_lat").toString();
            lonA = Utilidades.STOP.get("stop_lon").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LatLng a = new LatLng(Double.parseDouble(latA), Double.parseDouble(lonA));
        MapsActivity.mMap.addMarker(new MarkerOptions().position(a).title("A"));
        MapsActivity.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(a, 15.0f));
    }
}
