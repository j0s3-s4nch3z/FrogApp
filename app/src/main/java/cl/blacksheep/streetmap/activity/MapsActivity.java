package cl.blacksheep.streetmap.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import cl.blacksheep.streetmap.R;
import cl.blacksheep.streetmap.activity.utils.MapsActivityUtils;
import cl.blacksheep.streetmap.listener.MyAdListener;
import cl.blacksheep.streetmap.listener.MyClickFloatingListener;
import cl.blacksheep.streetmap.listener.MyImageButtonClickListener;
import cl.blacksheep.streetmap.listener.MyItemClickListener;
import cl.blacksheep.streetmap.listener.MyMapReadyCallBack;
import cl.blacksheep.streetmap.listener.MyNavigationItemSelectedListener;
import cl.blacksheep.streetmap.utils.Utilidades;

    public class MapsActivity extends FragmentActivity   {

        public static GoogleMap mMap;
        public static LocationManager locationManager;
        public static double latitud = -33.4466795;
        public static double longitud = -70.6569669;
        public static ArrayAdapter<String> adapter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        MapsActivityUtils mapsActivityUtils = new MapsActivityUtils(this);
        mapsActivityUtils.changeBarColor(this.getWindow());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new MyMapReadyCallBack(this));

        NavigationView  navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationItemSelectedListener(this));
        navigationView.setItemIconTintList(null);

        ImageView imageButton = findViewById(R.id.imageView2);
        MyImageButtonClickListener myImageButtonClickListener = new MyImageButtonClickListener(this);
        imageButton.setOnClickListener(myImageButtonClickListener);

        AutoCompleteTextView textView = findViewById(R.id.multiAutoCompleteTextView);
        textView.setThreshold(1);
        textView.setOnItemClickListener(new MyItemClickListener(this));
        textView.setTextColor(getResources().getColor(android.R.color.holo_green_light));
        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        textView.setDropDownBackgroundResource(R.color.gris);
        textView.setAdapter(MapsActivity.adapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setVisibility(View.GONE);
        floatingActionButton.setOnClickListener(new MyClickFloatingListener(this));

        FloatingActionButton ubicationButton = findViewById(R.id.floatingActionButton3);
        ubicationButton.setVisibility(View.VISIBLE);
        ubicationButton.setOnClickListener(new MyClickFloatingListener(this));

        AdView adView = findViewById(R.id.adView_mapa);
        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("4294D5592E1EE91A45AF948115172B21").build();
        adView.loadAd(adRequest);
        MyAdListener myAdListener = new MyAdListener();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
