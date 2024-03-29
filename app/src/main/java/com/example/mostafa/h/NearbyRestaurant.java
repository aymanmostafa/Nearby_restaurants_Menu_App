package com.example.mostafa.h;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;


public class NearbyRestaurant extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    menudb my;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_restaurant);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap()
    {
        my=new menudb(this);
        GPSTracker myTracker = new GPSTracker(this);
        double lng = myTracker.getLongitude();
        double lat = myTracker.getLatitude();
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("You're Here!"));

        float zoom = (float) 14.0;
        LatLng zoomLatLng = new LatLng(lat,lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zoomLatLng, zoom));

        ArrayList<Double> Latitude=new ArrayList<Double>();
        ArrayList<Double> Longitude=new ArrayList<Double>();
        ArrayList<String> Name=new ArrayList<String>();
        ArrayList<Rest> t;
        t=my.getAllRests();


        for(int i=0;i<t.size();i++){
            Latitude.add(t.get(i).getX());
            Longitude.add(t.get(i).getY());
            Name.add(t.get(i).getName());
        }


        try
        {
            for (int i = 0; i < Latitude.size(); i++)
            {

                if (  Math.abs(lat - Latitude.get(i)) >=0 && Math.abs(lat - Latitude.get(i)) <= 0.03 &&  Math.abs(lng - Longitude.get(i)) <= 0.03 )
                {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Latitude.get(i), Longitude.get(i))).title(Name.get(i)));
                    mMap.setMyLocationEnabled(true);
                }
            }
        }
        catch (Exception e)
        {
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Exception"));
        }

    }
}
