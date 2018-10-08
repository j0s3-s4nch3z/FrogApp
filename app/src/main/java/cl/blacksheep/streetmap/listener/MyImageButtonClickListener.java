package cl.blacksheep.streetmap.listener;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.activity.utils.MapsActivityUtils;

/**
 * Created by elsan on 15-05-2018.
 */

public class MyImageButtonClickListener implements View.OnClickListener {

    Activity activity;
    MapsActivityUtils mapsActivityUtils;

    public MyImageButtonClickListener(Activity activity)
    {
        this.activity = activity;
        mapsActivityUtils = new MapsActivityUtils(this.activity);
    }

    @Override
    public void onClick(View v) {
        DrawerLayout drawerLayout = this.activity.findViewById(R.id.drawer_layout);
        NavigationView navigationView = this.activity.findViewById(R.id.nav_view);
        drawerLayout.openDrawer(Gravity.LEFT);
        navigationView.bringToFront();
        drawerLayout.requestLayout();
    }
}
