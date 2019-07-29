//Team Name : Wild Rangers
package eco.find;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static eco.find.MapsActivity.longitude;

public class Status extends AppCompatActivity {


    public static String locatio;
    private TextView time;
    private TextView animalStatus;
    private TextView lux;
    private TextView light;
    private TextView location;


    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance(); //initialize our firebase object
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    String HdId= Homepage.sp_value;
    DatabaseReference  mRootRef = FirebaseDatabase.getInstance().getReference().child("AnimalData/"+HdId);
    DatabaseReference AnimalStatusRef = mRootRef.child("Status");
    DatabaseReference AnimalTimeRef = mRootRef.child("Time");
    DatabaseReference AnimalLiRef = mRootRef.child("Lux");
    DatabaseReference AnimalLitRef = mRootRef.child("Light");
    DatabaseReference AnimallocRef = mRootRef.child("Location");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        time = findViewById(R.id.updateTime);
        animalStatus = findViewById(R.id.Status);
        lux = findViewById(R.id.LuxValue);
        light = findViewById(R.id.LightValue);
        location = findViewById(R.id.LocationValue);


        AnimalTimeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                time.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        AnimalStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                animalStatus.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        AnimalLiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                lux.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        AnimalLitRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                light.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        AnimallocRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                locatio=text;
                String[] words = text.split(", ");
               Double  lon = Double.valueOf(words[0]);
               Double lat = Double.valueOf(words[1]);

                String address=getCompleteAddressString(lon,lat);
                Log.d("Address: ",address);

                location.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//
//
//    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
               // Log.d("My Current loction address", strReturnedAddress.toString());
            } else {
            //    Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
         //   Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }




    public String getHdId(){
        SharedPreferences mySharedPreferences = getSharedPreferences("MyHardwareId",
                Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("HdId", "0"); // getting String
    }
}




