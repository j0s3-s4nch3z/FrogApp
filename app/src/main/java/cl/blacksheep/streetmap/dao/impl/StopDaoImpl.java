package cl.blacksheep.streetmap.dao.impl;

import android.app.Activity;

import cl.blacksheep.streetmap.activity.utils.MapsActivityUtils;
import cl.blacksheep.streetmap.dao.StopDao;
import cl.blacksheep.streetmap.utils.Utilidades;

/**
 * Created by elsan on 09-06-2018.
 */

public class StopDaoImpl implements StopDao {

    Activity activity;

    public StopDaoImpl()
    {

    }

    public StopDaoImpl(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public void getStops() {
        Utilidades.obtenerParadas(Utilidades.url + "paraderos.php");
    }

    @Override
    public void getStop(String stopId) {
        Utilidades.obtenerParada(Utilidades.url + "paradero.php?paradero="+stopId);
    }
    @Override
    public void getPosition(String param) {
        Utilidades utilidades = new Utilidades(this.activity);
        utilidades.obtenerPosiciones(Utilidades.url + "posicionamiento.php?recorridos="+param);
    }
}
