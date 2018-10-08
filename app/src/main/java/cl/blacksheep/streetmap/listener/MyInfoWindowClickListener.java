package cl.blacksheep.streetmap.listener;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import cl.blacksheep.streetmap.activity.StopActivity;
import cl.blacksheep.streetmap.dao.StopDao;
import cl.blacksheep.streetmap.dao.impl.StopDaoImpl;
import cl.blacksheep.streetmap.dto.Stop;
import cl.blacksheep.streetmap.utils.Utilidades;

/**
 * Created by elsan on 11-07-2018.
 */

public class MyInfoWindowClickListener implements GoogleMap.OnInfoWindowClickListener {

    Activity activity;

    public MyInfoWindowClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String title = marker.getTitle();
        Intent intent = new Intent(this.activity.getApplicationContext() ,StopActivity.class);
        intent.putExtra("titulo", title);
        activity.startActivity(intent);
    }
}
