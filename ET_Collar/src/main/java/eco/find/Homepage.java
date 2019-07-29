//Team Name : Wild Rangers
package eco.find;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Homepage extends AppCompatActivity {
    public ImageButton an1, an2, an3;
    Spinner spinner1;
    TextView tv;
    int id;
    public static String sp_value;
    ArrayList<String> ar = new ArrayList<String>();
    private FirebaseAuth mAuth;
    Spinner spinner;
    ArrayAdapter<String> adp2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChangeBackground();
        SetOrientation();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();
                FragmentManager fragmentManager = getFragmentManager();

                if (id == R.id.map) {
                    Intent intent = new Intent(Homepage.this, MapsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.status) {
                    Intent intent = new Intent(Homepage.this, Status.class);
                    startActivity(intent);
                }  else if (id == R.id.contactus) {
                    Intent intent = new Intent(Homepage.this, Contact.class);
                    startActivity(intent);

                } else if (id == R.id.logout) {
                    finish();
                    Intent intent = new Intent(Homepage.this, Login.class);
                    startActivity(intent);

                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

     AStatus();

    }

    public void SetOrientation(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean orientation = preferences.getBoolean("portrait", true);
        if(orientation)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    public void ChangeBackground()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String bck = preferences.getString("background", "day");
        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.homepage);
        CoordinatorLayout cl = (CoordinatorLayout) findViewById(R.id.app_bar);
        if(bck.equals("day"))
        {
            r1.setBackgroundColor(Color.parseColor("#fffaf0"));
            cl.setBackgroundColor(Color.parseColor("#fffaf0"));
        }
        if(bck.equals("night"))
        {
            r1.setBackgroundColor(Color.parseColor("#808080"));
            cl.setBackgroundColor(Color.parseColor("#808080"));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.settings:

                Intent intent = new Intent(Homepage.this, Settings.class);
                startActivity(intent);
                return true;

            case R.id.help:
                String url = "https://humber.ca/";
                Intent ihelp = new Intent(Intent.ACTION_VIEW);
                ihelp.setData(Uri.parse(url));
                startActivity(ihelp);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed(){

        AlertDialog.Builder alretDialog = new AlertDialog.Builder(Homepage.this);

        alretDialog.setTitle(R.string.app_name);                    //Set title
        alretDialog.setMessage(R.string.exitmessage);                  //set message
        alretDialog.setIcon(R.drawable.warning);                       // ICon

        // Yes Button
        alretDialog.setPositiveButton(R.string.exityes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish(); //Exit app
            }
        });

        // No Button
        alretDialog.setNegativeButton(R.string.exitno, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); //cancel dialog back to app
            }
        });

        alretDialog.show(); //show dialog
    }

    public void AStatus() {
        spinner = (Spinner) findViewById(R.id.spinner1);
        Button getData = (Button) findViewById(R.id.getData);
        Button addData = (Button) findViewById(R.id.addData);

        ar.add("202481592591255");
        ar.add("202481592591255f");
        adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ar);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp2);
        SetList();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

                //    spinner.setSelection(position);
                sp_value = ar.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, Status.class);
                UpdateDefault();
                startActivity(intent);
            }
        });

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addValue();
            }
        });

    }

    private void addValue() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Homepage.this);
        alertDialog.setTitle("Add Device");
        final EditText macAddr = new EditText(Homepage.this);


        //macAddr.setTransformationMethod(PasswordTransformationMethod.getInstance());


        macAddr.setHint("Enter Device MAC Address");

        LinearLayout ll=new LinearLayout(Homepage.this);
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(macAddr);

        alertDialog.setView(ll);
        alertDialog.setPositiveButton("Add",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        add(macAddr.getText().toString());
                        dialog.cancel();
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = alertDialog.create();
        alert11.show();
    }


    public void SetList(){

        mAuth=mAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users").child(user.getUid()+"/Hardware");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ar.clear();
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    for (DataSnapshot rd : dataSnapshot.getChildren()) {
                        //   String hardId= rd.getValue(String.class);
                        ar.add(rd.getValue().toString());
                    }
                    Log.d("Array",ar.toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ar);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp2);


    }



    public void add(final String mac){

        ar.add(mac);
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        mAuth=mAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users").child(user.getUid()+"/Hardware");
        for(int i=0;i<ar.size();i++) {
            ref.child(Integer.toString(i)).setValue(ar.get(i)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                }
            });
        }

        Toast.makeText(Homepage.this,"new device added",Toast.LENGTH_LONG).show();

        spinner.setAdapter(adp2);
    }

    public void UpdateDefault(){
        mAuth=mAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users").child(user.getUid()+"/Default");
        ref.child("HdDef").setValue(sp_value).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failed to set default","Failed");
            }
        });


    }
}