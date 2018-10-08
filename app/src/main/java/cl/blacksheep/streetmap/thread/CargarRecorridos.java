package cl.blacksheep.streetmap.thread;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.activity.MapsActivity;
import cl.blacksheep.streetmap.dao.RouteDao;
import cl.blacksheep.streetmap.dao.StopDao;
import cl.blacksheep.streetmap.dao.impl.RouteDaoImpl;
import cl.blacksheep.streetmap.dao.impl.StopDaoImpl;
import cl.blacksheep.streetmap.utils.Utilidades;

public class CargarRecorridos extends AsyncTask<Integer,Integer,Long> {
    Activity activity;

    public CargarRecorridos(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Long doInBackground(Integer... integers) {
        try {
            RouteDao routeDao = new RouteDaoImpl();
            routeDao.getRoutes();
            publishProgress(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Utilidades.CONT++;
        }
}
