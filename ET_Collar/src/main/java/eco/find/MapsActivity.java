//Team Name : Wild Rangers
package eco.find;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static GoogleMap mMap;
    public static double longitude;
    public static double latitude;


    public static Marker pet_location;

    String HdId= Homepage.sp_value;
    DatabaseReference  mRootRef = FirebaseDatabase.getInstance().getReference().child("AnimalData/"+HdId);
    DatabaseReference AnimallocRef = mRootRef.child("Location");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
        /* flag to indicate google maps is loaded */

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        LatLng location = new LatLng(longitude, latitude);
        pet_location = mMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        pet_location.setTag(0);


        mMap.setOnMarkerClickListener(this); // this will register the clicks



    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        Integer clickedCount = (Integer)marker.getTag();
        if(clickedCount != null){
            clickedCount = clickedCount + 1;

            marker.setTag(clickedCount);
            Toast.makeText(this,"Current Location of the Pet!", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public static void setPos( double longitude, double latitude){

        LatLng location = new LatLng(longitude, latitude);
        pet_location.setPosition(location);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location ,10));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,10));
    }


    @Override
    protected void onStart(){
        super.onStart();

            AnimallocRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String line = dataSnapshot.getValue(String.class);
                String[] words = line.split(", ");
                 longitude = Double.valueOf(words[0]);
                 latitude = Double.valueOf(words[1]);
                 String ln=String.valueOf(longitude);
                String la=String.valueOf(latitude);
                Log.d("Long: ",ln);
                Log.d("Long: ",la);
                setPos(longitude,latitude);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}



