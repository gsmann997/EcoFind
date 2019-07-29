//Team Name : Wild Rangers
package eco.find;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        SetOrientation();

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
