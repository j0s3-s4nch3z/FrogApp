package cl.blacksheep.streetmap.dao;

import android.app.Activity;

/**
 * Created by elsan on 09-06-2018.
 */

public interface StopDao {
    public void getStops();

    public void getStop(String stopId);

    public void getPosition(String param);

}
