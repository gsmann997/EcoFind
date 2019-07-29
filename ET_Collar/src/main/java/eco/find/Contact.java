//Team Name : Wild Rangers
package eco.find;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ChangeBackground();
        SetOrientation();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_nav_bar);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.phone:
                                item.setCheckable(false);
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:6479212702"));
                                startActivity(intent);
                                break;
                            case R.id.email:
                                item.setCheckable(false);
                                Intent i = new Intent (Contact.this, Mail.class);
                                startActivity(i);
                                break;
                            default:
                                    break;

                        }
                        return true;
                    }
                });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.simple_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
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
        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.contact);
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
}
