package net.effect117.mapping;


import java.security.Security;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by JHilland on 9/2/2015.
 */


public class FilterMapsActivity extends FragmentActivity {

    private final String TAG = getClass().getSimpleName();
    private String[] places;
    private SupportMapFragment mapFragment;
    private GoogleMap map;
    public Location loc;
    protected Bundle intent;
    private LocationManager locManager;
    private static boolean RUN_ONCE = true;
    private static boolean RUN_ONCE_NETWORK = true;
    private LatLng unm = new LatLng(35.0843, -106.62);
    private LatLngBounds.Builder builder;
    private LatLng origin, dest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_map);

        Toast.makeText(FilterMapsActivity.this, "Made it", Toast.LENGTH_SHORT).show();

        places = getResources().getStringArray(R.array.places);
        //currentLocation();



        mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.filterMap));
        map = mapFragment.getMap();
        map.getUiSettings().setZoomControlsEnabled(true);  //********//
        map.getUiSettings().setZoomGesturesEnabled(true);  //********//
        map.getUiSettings().setRotateGesturesEnabled(true); //********//


        map.setMyLocationEnabled(true);

        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        while (map.equals(null))
            map = mapFragment.getMap();

        //getLocationListener(locManager.GPS_PROVIDER);

        if(locManager.isProviderEnabled(locManager.GPS_PROVIDER))
        {
            try {


                Location firstLoc = locManager.getLastKnownLocation(locManager.PASSIVE_PROVIDER);
                if (firstLoc != null)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(firstLoc.getLatitude(), firstLoc.getLongitude()), 17));
                else
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(unm, 17));

            }
            catch (SecurityException e)
            {

            }
        }
        else
        {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(unm, 17));
            //runOnce();
        }


        new GetPlaces(FilterMapsActivity.this, places[2].toLowerCase().replace("-", "_").replace(" ", "_")).execute();

    }

    private class GetPlaces extends AsyncTask<Void, Void, ArrayList<Place>> {

        private ProgressDialog dialog;
        private Context context;
        private String places;

        public GetPlaces(Context context, String places) {
            this.context = context;
            this.places = places;
        }

        @Override
        protected void onPostExecute(ArrayList<Place> result) {
            super.onPostExecute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            for (int i = 0; i < result.size(); i++) {
                map.addMarker(new MarkerOptions()
                        .title(result.get(i).getName())
                        .position(
                                new LatLng(result.get(i).getLatitude(), result
                                        .get(i).getLongitude()))
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.pin))
                        .snippet(result.get(i).getVicinity()));
            }
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(result.get(0).getLatitude(), result
                            .get(0).getLongitude())) // Sets the center of the map to
                            // Mountain View
                    .zoom(14) // Sets the zoom
                    .tilt(30) // Sets the tilt of the camera to 30 degrees
                    .build(); // Creates a CameraPosition from the builder
            map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setCancelable(false);
            dialog.setMessage("Loading..");
            dialog.isIndeterminate();
            dialog.show();
            dialog.dismiss();
        }



        @Override
        protected ArrayList<Place> doInBackground(Void... arg0) {
            PlacesService service = new PlacesService(
                    "AIzaSyBqBvDTdF2YmkkjfzZyWwiNwHKna1akAL0");
            ArrayList<Place> findPlaces = service.findPlaces(loc.getLatitude(), // 28.632808
                    loc.getLongitude(), places); // 77.218276

            //for (int i = 0; i < findPlaces.size(); i++) {

              //  Place placeDetail = findPlaces.get(i);
                //Log.e(TAG, "places : " + placeDetail.getName());
            //}
            return findPlaces;
        }

    }





    private void currentLocation() {
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String provider = locManager
                .getBestProvider(new Criteria(), false);

        try {
            Location location = locManager.getLastKnownLocation(provider);

            if (location == null) {
                locManager.requestLocationUpdates(provider, 0, 0, listener);
            } else {
                loc = location;
                new GetPlaces(FilterMapsActivity.this, places[0].toLowerCase().replace(
                        "-", "_")).execute();
                Log.e(TAG, "location : " + location);
            }
        } catch (SecurityException e) {

        }
    }


    private LocationListener listener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "location update : " + location);
            loc = location;
            try {
                locManager.removeUpdates(listener);
            } catch (SecurityException e) {

            }
        }
    };
}