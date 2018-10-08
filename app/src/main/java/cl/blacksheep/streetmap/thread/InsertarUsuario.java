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

public class InsertarUsuario extends AsyncTask {
    Activity activity;

    public InsertarUsuario(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Utilidades.insertarUsuario();
        publishProgress(10);
        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        Utilidades.CONT++;
        //Toast.makeText(activity, "usuaio insertado", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

}
