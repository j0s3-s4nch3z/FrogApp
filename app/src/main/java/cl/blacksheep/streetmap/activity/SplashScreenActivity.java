package cl.blacksheep.streetmap.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.google.android.gms.ads.MobileAds;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.thread.CargarParaderos;
import cl.blacksheep.streetmap.thread.CargarRecorridos;
import cl.blacksheep.streetmap.thread.InsertarUsuario;
import cl.blacksheep.streetmap.utils.Utilidades;

public class SplashScreenActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 3000;
    public static String ANDROID_ID;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        MapsActivity.adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line);
        ANDROID_ID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        InsertarUsuario insertarUsuario = new InsertarUsuario(this);
        insertarUsuario.execute();
        CargarRecorridos cargarRecorridos = new CargarRecorridos(this);
        cargarRecorridos.execute();
        CargarParaderos cargarParaderos = new CargarParaderos(this);
        cargarParaderos.execute();
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.fondo_splash));
        this.getSupportActionBar().hide();
        setContentView(R.layout.splash_screen);
        MobileAds.initialize(this, "ca-app-pub-6243401123508019~1815369422");
        final ImageView v = findViewById(R.id.imageView5);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                int resourceId = getResources().getIdentifier("semama", "drawable", getPackageName());
                Drawable myDrawable1 = getResources().getDrawable(resourceId);
                v.setImageDrawable(myDrawable1);
            }
        }, 2000);
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                ImageView rana = findViewById(R.id.imageView);
                int resourceId = getResources().getIdentifier("semverde", "drawable", getPackageName());
                Drawable myDrawable1 = getResources().getDrawable(resourceId);
                v.setImageDrawable(myDrawable1);
            }
        }, 3000);
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent mainIntent = null;
                if(Utilidades.CONT == 3)
                {
                    mainIntent = new Intent(SplashScreenActivity.this,MapsActivity.class);
                    Utilidades.CONT = 0;
                }
                else
                {
                    mainIntent = new Intent(SplashScreenActivity.this,LoaderActivity.class);
                }
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
                handler.removeCallbacksAndMessages(null);
            }
        }, 4000);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }



}
