//Team Name : Wild Rangers
package eco.find;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

        private View view;
        private EditText email, password;
        private Button signUpButton;
        private TextView already_user;

        private FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);



            email = findViewById(R.id.emailet);
            password = findViewById(R.id.passwardet);
            signUpButton = findViewById(R.id.signupbutton);
            already_user = findViewById(R.id.already_user);

            mAuth = FirebaseAuth.getInstance();

            handleLogin();



        }

        private void handleLogin(){

            mAuth = FirebaseAuth.getInstance();

            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createNewUser(String.valueOf(email.getText()), String.valueOf(password.getText()));
                }
            });
            already_user.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    BacktoLogin();
                }
            });


        }

        private void createNewUser(String email, String password){

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Log.d("msg", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(),"User Created",Toast.LENGTH_LONG).show();
                                BacktoLogin();
                            } else {

                                Log.w("msg", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Create new user failed.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

        private void SignoutfromDatabase(){

            mAuth.signOut();

        }
    private void BacktoLogin(){

        Intent intent =  new Intent(SignUp.this,Login.class );
        startActivity(intent);
    }



    }
