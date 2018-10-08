package cl.blacksheep.streetmap.listener;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

public class MyButtonLocationListener implements DialogInterface.OnClickListener {
    Activity activity;

    public MyButtonLocationListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        this.activity.startActivity(intent);
    }
}
