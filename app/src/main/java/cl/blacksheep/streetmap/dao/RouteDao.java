package cl.blacksheep.streetmap.dao;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.List;

import cl.blacksheep.streetmap.dto.Route;

/**
 * Created by elsan on 07-04-2018.
 */

public interface RouteDao {

    public Route getRoute(String routeId,String sentido);

    public Route getMetro(String routeId);

    public Route getMetroTren(String routeId);

    public void getRoutes();

    public void isEspecialRoute(String route);
}
