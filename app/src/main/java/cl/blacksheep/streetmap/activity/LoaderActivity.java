package cl.blacksheep.streetmap.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.listener.MyAdListener;
import cl.blacksheep.streetmap.utils.Utilidades;

public class LoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar.setMax(10);
        final Handler handler = new Handler();
        for(int i =  0; i < 30 ; i++)
        {
            handler.postDelayed(new Runnable(){
                @Override
                public void run() {
                    if(Utilidades.CONT == 3)
                    {
                        progressBar.setProgress(10);
                        Intent mainIntent = new Intent(LoaderActivity.this,MapsActivity.class);
                        LoaderActivity.this.startActivity(mainIntent);
                        LoaderActivity.this.finish();
                        Utilidades.CONT = 0;
                        handler.removeCallbacksAndMessages(null);
                    }
                }
            }, i*1000);
        }
    }

}
