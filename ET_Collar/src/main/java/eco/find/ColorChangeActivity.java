//Team Name : Wild Rangers
package eco.find;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ColorChangeActivity extends AppCompatActivity {
RadioButton day_mode,night_mode;
String color_mode="day";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_change);
        ChangeBackground();
        SetOrientation();
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }



    public void RadioCheck(View v)
    {
        day_mode = (RadioButton)findViewById(R.id.day);
        night_mode = (RadioButton)findViewById(R.id.night);
        if(day_mode.isChecked())
        {
              color_mode = "day";
        }
        else
        {
              color_mode = "night";
        }
    }

    public void Store()
    {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(ColorChangeActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("background", color_mode);
        editor.apply();
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
        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.colorchangepage);
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
