package cl.blacksheep.streetmap.utils;

import android.app.Activity;
import android.location.Location;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.activity.MapsActivity;
import cl.blacksheep.streetmap.activity.SplashScreenActivity;
import cl.blacksheep.streetmap.dto.Route;
import cl.blacksheep.streetmap.dto.Shape;
import cl.blacksheep.streetmap.dto.Stop;


/**
 * Created by elsan on 25-03-2018.
 */

public class Utilidades {

    public static JSONObject ROUTE = null;
    public static JSONObject STOP = null;
    public static JSONArray POSITION = null;
    public static JSONArray ROUTES = null;
    public static JSONArray STOPS = null;
    public static List<Shape> SHAPES = new ArrayList();

    public static String REGEX = ",";
    public static boolean DATA_ROUTE = false;
    public static boolean DATA_STOP = true;

    public static boolean DIBUJAR_PARADEROS = true;

    public static boolean SIN_CONEXION = false;

    public static int CONT = 0;
       //public static String url = "http://190.162.154.119:85/StreetMapWebService/";
       public static String url = "http://192.168.43.136/StreetMapWebService/";

    Activity activity;

    public Utilidades(Activity activity)
    {
        this.activity = activity;
    }

    public static void obtenerRecorridos(String reqUrl)
    {
        ROUTE = null;
        try {
            ROUTES = obtenerJsonArray(reqUrl);
            for(int i = 0 ; i < ROUTES.length() ; i++)
            {
                String aux = ROUTES.getJSONObject(i).getString("route_id");
                if(aux.contains("R_V40") || aux.contains("V_V40"))
                {
                    continue;
                }
                else if(aux.contains("_V40"))
                {
                    aux = aux.split("_")[0];
                    if(aux.startsWith("L"))
                    {
                        aux = aux.replace("L","Linea ");
                    }
                    else if(aux.startsWith("M"))
                    {
                        aux = aux.replace("MT","Metrotren Nos");
                    }
                    MapsActivity.adapter.add(aux);
                }
                else
                {
                    MapsActivity.adapter.add(aux);
                }
            }
            SIN_CONEXION = false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            SIN_CONEXION = true;
        }
    }

    public static void obtenerParadas(String reqUrl)
    {
        try {
            STOPS = obtenerJsonArray(reqUrl);
            for(int i = 0 ; i < STOPS.length() ; i++)
            {
                String aux = STOPS.getJSONObject(i).getString("stop_id");
                MapsActivity.adapter.add(aux);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void obtenerParada(String reqUrl)
    {
        try {
            STOP = obtenerJsonObject(reqUrl);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public static void cerrar(BufferedReader b)
    {
        try {
            if(b != null)
            {
                b.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void cerrar(InputStream is)
    {
        try {
            if(is != null)
            {
                is.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void leerRuta2(InputStream is)
    {
        SHAPES.clear();
        String cadena;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((cadena = br.readLine()) != null) {
                Shape shape = new Shape();
                String[] data = cadena.split(REGEX);
                shape.setRouteId(data[0]);
                shape.setLatitud(data[1]);
                shape.setLongitud(data[2]);
                shape.setSequence(data[3]);
                SHAPES.add(shape);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            cerrar(br);
            cerrar(is);
        }
    }

    public static JSONArray obtenerJsonArray(String urlDest)
    {
        JSONArray json= null;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlDest);
        HttpResponse response = null;
        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(
                            response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                json = new JSONArray(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JSONObject obtenerJsonObject(String urlDest)
    {
        JSONObject json= null;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlDest);
        HttpResponse response = null;
        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(
                            response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                json = new JSONObject(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void obtenerRecorrido(String reqUrl)
    {
        ROUTE = null;
        try {
            ROUTE = obtenerJsonObject(reqUrl);
        }
        catch(Exception e) {
            Utilidades.ROUTE = null;
            e.printStackTrace();
        }
    }

    public void obtenerPosiciones(String reqUrl) {
        Utilidades.POSITION= null;
        final ListView listView = this.activity.findViewById(R.id.listView);
        final List<String> lista = new ArrayList();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String> (this.activity, android.R.layout.simple_list_item_1);
        try {
            final LatLng paradero = new LatLng(STOP.getDouble("stop_lat"), STOP.getDouble("stop_lon"));
            Utilidades.POSITION = Utilidades.obtenerJsonArray(reqUrl);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                    for (int i = 0; i < Utilidades.POSITION.length(); i++) {
                        try {
                            JSONObject json = ((JSONObject) Utilidades.POSITION.get(i));
                            double latA = json.getDouble("latitud");
                            double lonA = json.getDouble("longitud");
                            double direccion = json.getDouble("direccion_geografica");
                            double velocidad = json.getDouble("velocidad");
                            String name = json.getString("recorrido");
                            String patente = json.getString("patente");
                            if(direccion == Route.NORTE || direccion == Route.NORESTE || direccion == Route.NOROESTE)
                            {
                                if(latA > STOP.getDouble("stop_lat"))
                                {
                                    continue;
                                }
                            }
                            LatLng a = new LatLng(latA, lonA);
                            if(velocidad == 0.0)
                            {
                                velocidad = 14.0;
                            }
                            double velocidadF = velocidad / 36;
                            MarkerOptions markerOption = new MarkerOptions().position(a);
                            //markerOption.icon(bitmapDescriptorFromVector(context,R.drawable.paradero));
                            float[] distancia = new float[1];
                            Location.distanceBetween(paradero.latitude,paradero.longitude,a.latitude,a.longitude,distancia);
                            double tiempo = (distancia[0]  / velocidadF) / 10;
                            int time = (int) tiempo;
                            String cadena = "Bus: "+name + " Patente: "+ patente + " Distancia:" + distancia[0] + " - Velocidad: "+ velocidad+"KM/H - " +" T: "+segundosATiempoTheRial(time)+" horas";
                            MapsActivity.mMap.addMarker(markerOption).setTitle(cadena);
                            lista.add(cadena);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                            arrayAdapter.addAll(lista);
                            listView.setAdapter(arrayAdapter);
                }
            };
            this.activity.runOnUiThread(runnable);
        }
        catch(Exception e)
        {
            Utilidades.ROUTE = null;
            e.printStackTrace();

        }
    }
    public static String segundosATiempoTheRial(int segundos) {
        int hours = (int) Math.floor(segundos / 3600);
        String minutes = (int) Math.floor(segundos % 3600 / 60) + "";
        String seconds = segundos % 60 + "";
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        seconds = seconds.length() == 1 ? "0" + seconds : seconds;
        return hours + ":" + minutes + ":" + seconds;
    }

    public static void isEspecialRoute(String reqUrl) {
        try {
            ROUTE = obtenerJsonObject(reqUrl);
        }
        catch(Exception e) {
            Utilidades.ROUTE = null;
            e.printStackTrace();
        }
    }

    public static void insertarUsuario() {
        try {
            JSONObject existe = obtenerJsonObject(url + "obtenerUsuario.php?usuario=" + SplashScreenActivity.ANDROID_ID);
            if (existe.get("usuario").equals("no")) {
                List<NameValuePair> params = new ArrayList();
                params.add(new BasicNameValuePair("usuario", SplashScreenActivity.ANDROID_ID));
                enviarPost(url + "insertarUsuario.php", params);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void enviarPost(String urlDest,List<NameValuePair> params) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(urlDest);

        post.setHeader("User-Agent", "");
        try {
            post.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(post);
            System.out.println("Response Code : "
                    + response.getStatusLine().getStatusCode());
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





