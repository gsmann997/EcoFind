//Team Name : Wild Rangers
package eco.find;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity {
ListView listView;
boolean portrait_mode=true;
boolean remember_password=true ;
CheckBox remember_check;
Switch portrait_check;
FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ChangeBackground();
        SetOrientation();
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        listView = (ListView) findViewById(R.id.list);
        String[] values = new String[]{getString(R.string.bck_color),
                getString(R.string.chng_pass),getString(R.string.about)};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0) {
                    Intent myIntent = new Intent(view.getContext(), ColorChangeActivity.class);
                    startActivityForResult(myIntent, 0);
                }


                if (position == 1) {
                    Change_Password();
                }

                if (position == 2) {
                    Intent myIntent = new Intent(view.getContext(), About.class);
                    startActivityForResult(myIntent, 0);
                }

            }
        });

    }

    public void Store()
    {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Settings.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("portrait", portrait_mode);
        editor.putBoolean("rememberme", remember_password);
        editor.apply();
    }

    public void Check(View v)
    {
        portrait_check = (Switch)findViewById(R.id.portrait);
        remember_check = (CheckBox)findViewById(R.id.rememberme);

        if(portrait_check.isChecked())
            portrait_mode=true;
        else
            portrait_mode=false;

        if(remember_check.isChecked())
            remember_password=true;
        else
            remember_password=false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.changes, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save:
                Store();
                Toast.makeText(this,R.string.changes_saves,Toast.LENGTH_LONG).show();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ChangeBackground()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String bck = preferences.getString("background", "day");
        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.settingspage);
        if(bck.equals("day"))
        {
            r1.setBackgroundColor(Color.parseColor("#fffaf0"));
        }
        if(bck.equals("night"))
        {
            r1.setBackgroundColor(Color.parseColor("#808080"));
        }
    }

    public void SetOrientation(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean orientation = preferences.getBoolean("portrait", true);
        if(orientation)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }




    public void Change_Password(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Settings.this);
        alertDialog.setTitle("CHANGE PASSWORD");
        final EditText oldPass = new EditText(Settings.this);
        final EditText newPass = new EditText(Settings.this);
        final EditText confirmPass = new EditText(Settings.this);


        oldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        oldPass.setHint("Old Password");
        newPass.setHint("New Password");
        confirmPass.setHint("Confirm Password");
        LinearLayout ll=new LinearLayout(Settings.this);
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(oldPass);

        ll.addView(newPass);
        ll.addView(confirmPass);
        alertDialog.setView(ll);
        alertDialog.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        change(newPass.getText().toString());
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

    public void change(final String newPass){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){

            user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Settings.this,getString(R.string.pass_change),Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(Settings.this, getString(R.string.error),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

}
