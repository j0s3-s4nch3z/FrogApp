package cl.blacksheep.streetmap.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.listener.MyAdListener;
import cl.blacksheep.streetmap.thread.CargarDatosBusquedaThread;
import cl.blacksheep.streetmap.utils.Utilidades;

public class StopActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);
        AdView adView = findViewById(R.id.adView_stop);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("4294D5592E1EE91A45AF948115172B21").build();
        adView.loadAd(adRequest);
        MyAdListener myAdListener = new MyAdListener();
        adView.setAdListener(myAdListener);

        Bundle bundle = getIntent().getExtras();
        TextView textView = findViewById(R.id.textView3);
        String titulo  = bundle.getString("titulo");
        textView.setText(titulo);
        String[] data = titulo.split("-");
        final String paradero = data[0];
        String descripcion = data[1];
        TextView textView1 = findViewById(R.id.textView4);
        StringBuilder cadena = new StringBuilder();
        String query = "";
        JSONArray routes = null;
        try {
            routes = (JSONArray) Utilidades.STOP.get("stop_routes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CargarDatosBusquedaThread run = new CargarDatosBusquedaThread(this,paradero,routes);
        Thread thread = new Thread(run);
        thread.start();
        do {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while(Utilidades.DATA_STOP);
        for (int i = 0; i < routes.length(); i++) {
            String route = null;
            try {
                route = routes.get(i).toString().split("-")[0];
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cadena.append(route);
            cadena.append(" ");
        }
        textView1.setText(cadena);


    }

}
