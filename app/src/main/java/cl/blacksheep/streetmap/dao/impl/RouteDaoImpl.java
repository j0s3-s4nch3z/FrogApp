package cl.blacksheep.streetmap.dao.impl;

import org.json.JSONArray;

import java.util.List;

import cl.blacksheep.streetmap.dao.RouteDao;
import cl.blacksheep.streetmap.dto.Route;
import cl.blacksheep.streetmap.utils.Utilidades;

/**
 * Created by elsan on 07-04-2018.
 */

public class RouteDaoImpl implements RouteDao{


    @Override
    public Route getRoute(String routeId,String sentido) {
        Route routeSel = null;
        Utilidades.obtenerRecorrido(Utilidades.url + "recorrido.php?recorrido="+routeId+"&sentido="+sentido);
        return null;
    }

    @Override
    public Route getMetro(String routeId) {
        routeId = routeId.replace("linea ","l");
        Utilidades.obtenerRecorrido(Utilidades.url + "metro.php?linea="+routeId.toUpperCase());
        return null;
    }

    @Override
    public Route getMetroTren(String routeId) {
        routeId = routeId.replace("metrotren nos","MT");
        Utilidades.obtenerRecorrido(Utilidades.url + "metro.php?linea="+routeId.toUpperCase());
        return null;
    }

    @Override
    public void getRoutes() {
        Utilidades.obtenerRecorridos(Utilidades.url + "recorridos.php");
    }

    @Override
    public void isEspecialRoute(String route) {
        Utilidades.isEspecialRoute(Utilidades.url + "special_route.php?recorrido="+route);
    }


}
