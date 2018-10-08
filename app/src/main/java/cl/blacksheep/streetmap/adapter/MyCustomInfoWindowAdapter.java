package cl.blacksheep.streetmap.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import cl.blacksheep.streetmap.R;

public class MyCustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private final View mContents;
    private Activity activity;
    public MyCustomInfoWindowAdapter(Activity activity) {
        this.activity = activity;
        mWindow = this.activity.getLayoutInflater().inflate(R.layout.custom_info_window, null);
        mContents = this.activity.getLayoutInflater().inflate(R.layout.custom_info_contents, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        render(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        render(marker, mContents);
        return mContents;
    }

    private void render(Marker marker, View view) {

    }
}
