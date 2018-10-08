package cl.blacksheep.streetmap.thread;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import org.json.JSONArray;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.dao.StopDao;
import cl.blacksheep.streetmap.dao.impl.StopDaoImpl;
import cl.blacksheep.streetmap.utils.Utilidades;

public class CargarDatosBusquedaThread implements Runnable {

    Activity activity;
    String paradero;
    JSONArray routes;

    public CargarDatosBusquedaThread(Activity activity, String paradero, JSONArray routes) {
        this.activity = activity;
        this.paradero = paradero;
        this.routes = routes;
    }

    @Override
    public void run() {
        try {
            StopDao routeDao = new StopDaoImpl(this.activity);
            routeDao.getStop(paradero);
            StringBuilder data = new StringBuilder();
            for(int i = 0 ; i < routes.length() ; i++)
            {
                data.append(routes.get(i).toString().replaceAll("-",""));
            }
            routeDao.getPosition(data.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        Utilidades.DATA_STOP = false;
    }
}
