package cl.blacksheep.streetmap.listener;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ToggleButton;

import org.json.JSONException;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.activity.MapsActivity;
import cl.blacksheep.streetmap.activity.utils.MapsActivityUtils;
import cl.blacksheep.streetmap.dao.RouteDao;
import cl.blacksheep.streetmap.dao.impl.RouteDaoImpl;
import cl.blacksheep.streetmap.dto.Route;
import cl.blacksheep.streetmap.utils.Utilidades;

/**
 * Created by elsan on 01-05-2018.
 */

public class MyItemClickListener implements AdapterView.OnItemClickListener {

    Activity activity;
    MapsActivityUtils mapsActivityUtils;

    public MyItemClickListener(Activity activity)
    {
        this.activity = activity;
        mapsActivityUtils = new MapsActivityUtils(this.activity);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AutoCompleteTextView textView = (AutoCompleteTextView) activity.findViewById(R.id.multiAutoCompleteTextView);
        String busqueda = textView.getText().toString().toLowerCase();
        Route.ESTADO = Route.SENTIDO_IDA;
        //Route.ESPECIAL = new RouteDaoImpl
        if(busqueda.startsWith("l")) {
            mapsActivityUtils.dibujarMetro(busqueda);
        }
        else if(busqueda.startsWith("m")) {
            mapsActivityUtils.dibujarMetroTren(busqueda);
        }
        else if(busqueda.startsWith("p"))
        {
            mapsActivityUtils.localizarParadero(busqueda);
        }
        else {
                mapsActivityUtils.dibujarRecorrido(busqueda,Route.SENTIDO_IDA);
                FloatingActionButton f = this.activity.findViewById(R.id.floatingActionButton);
                f.setVisibility(View.VISIBLE);
        }

        mapsActivityUtils.hideSoftKeyBoard();
    }


}
