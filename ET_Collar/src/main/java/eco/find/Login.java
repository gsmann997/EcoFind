//Team Name : Wild Rangers
package eco.find;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {


    public EditText user;
    public EditText passward;
    //private TextView info;
    public Button login;
    public int attempt = 5;
    private static FirebaseAuth Auth;
    public CheckBox rem_me;
    boolean rem;
    private TextView forgotPassword, signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ViewPager viewPager = (ViewPager)findViewById(R.id.imgSlider);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);

        user = (EditText) findViewById(R.id.username);
        passward = (EditText) findViewById(R.id.passward);
        login = (Button) findViewById(R.id.button);
        forgotPassword = findViewById(R.id.forgot_password);
        signUp = findViewById(R.id.signup);
        FillFields();
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ApplyChanges();
                loginUser(String.valueOf(user.getText()), String.valueOf(passward.getText()));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginPage = new Intent(Login.this, SignUp.class);
                startActivity(loginPage);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginPage = new Intent(Login.this, resetPassward.class);
                startActivity(loginPage);
            }
        });
    }


    protected void loginUser(String username, String password){
        Auth = FirebaseAuth.getInstance();
        Auth.signInWithEmailAndPassword(username,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()){
                    Log.d("msg","signIn Success");
                    FirebaseUser user = Auth.getCurrentUser();
                    Toast.makeText(getApplicationContext(),"You are now logged in",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this,Homepage.class);
                    startActivity(intent);
                } else{
                    attempt--;

                    Toast.makeText(getApplicationContext(),"No of attempts remaining: "+String.valueOf(attempt),Toast.LENGTH_LONG).show();

                    if(attempt == 0){
                        login.setEnabled(false);
                        Toast.makeText(getApplicationContext(),"Please restart the app!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void FillFields(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean rememberme = preferences.getBoolean("rememberme", false);
        String user_fill = preferences.getString("username", "");
        String pass_fill = preferences.getString("password", "");
        user = (EditText) findViewById(R.id.username);
        passward = (EditText) findViewById(R.id.passward);
        if(rememberme) {
            user.setText(user_fill);
            passward.setText(pass_fill);
        }
    }

    public void savePreferences(String Id){
        SharedPreferences mySharedPreferences = getSharedPreferences("MyHardwareId",
                Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("HdId", Id);
        editor.apply();

    }



      public void ApplyChanges(){
        rem_me = (CheckBox) findViewById(R.id.rememberme_checkbox);
        if(rem_me.isChecked())
            rem=true;
        else
            rem=false;
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Login.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String username_fill,pass_fill;
        username_fill = user.getText().toString();
        pass_fill = passward.getText().toString();
        editor.putBoolean("rememberme", rem);
        editor.putString("username",username_fill);
        editor.putString("password",pass_fill);
        editor.apply();
    }
}
