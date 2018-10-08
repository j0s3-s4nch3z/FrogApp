package cl.blacksheep.streetmap.listener;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.activity.AcercaActivity;
import cl.blacksheep.streetmap.activity.BuscadorActivity;
import cl.blacksheep.streetmap.activity.FavoritosActivity;
import cl.blacksheep.streetmap.activity.MapsActivity;
import cl.blacksheep.streetmap.activity.PlanificacionActivity;
import cl.blacksheep.streetmap.activity.PublicidadActivity;
import cl.blacksheep.streetmap.activity.SplashScreenActivity;
import cl.blacksheep.streetmap.dto.Route;

/**
 * Created by elsan on 01-05-2018.
 */

public class MyNavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

    Activity activity;

    public MyNavigationItemSelectedListener(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mapa) {
            Toast.makeText(this.activity.getApplicationContext(),"",Toast.LENGTH_LONG);
            Route.TIPO = 0;
        }
        else if (id == R.id.planifica) {
            Intent mainIntent = new Intent(this.activity,PlanificacionActivity.class);
            this.activity.startActivity(mainIntent);
        }
        else if (id == R.id.favorito) {
            Intent mainIntent = new Intent(this.activity,FavoritosActivity.class);
            this.activity.startActivity(mainIntent);
        }
        else if (id == R.id.busca) {
            Intent mainIntent = new Intent(this.activity,BuscadorActivity.class);
            this.activity.startActivity(mainIntent);
        }
        else if (id == R.id.acerca) {
            Intent mainIntent = new Intent(this.activity,AcercaActivity.class);
            this.activity.startActivity(mainIntent);
        }
        else if (id == R.id.quitar) {
            Intent mainIntent = new Intent(this.activity,PublicidadActivity.class);
            this.activity.startActivity(mainIntent);
        }

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
