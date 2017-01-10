package com.example.babarmustafa.chatapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.babarmustafa.chatapplication.Chat_Work.Chat_Main_View;
import com.example.babarmustafa.chatapplication.Chat_Work.ConversationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progres;
    Button for_sigin_in;
    TextView jump_to_signup;
    EditText s_email;
    EditText s_passs;
    private FirebaseAuth auth;
    String usernameforlogin;
    String passwordforlogin;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        auth = FirebaseAuth.getInstance();


        s_email = (EditText) findViewById(R.id.e_email);
        s_passs = (EditText) findViewById(R.id.e_pass);
        for_sigin_in = (Button) findViewById(R.id.login_buttobn);


        auth = FirebaseAuth.getInstance();
        progres = new ProgressDialog(this);

        for_sigin_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progres.setMessage("Signing in....");
                progres.show();
                if (s_email.getText().toString().length() == 0) {
                    Toast.makeText(MainActivity.this, "You Should to wirte the Email ", Toast.LENGTH_LONG).show();
                    s_email.setError("Enter The User Name ");
                    return;
                }

//                else if (s_passs.getText().toString().length() > 6) {
//                    Toast.makeText(MainActivity.this, "Password Must be 6 digits or more than 6 ", Toast.LENGTH_LONG).show();
//                    s_passs.setError("Enter The password ");
//                    return;
//                }
else if (s_passs.getText().toString().length() == 0) {
                    Toast.makeText(MainActivity.this, "You Must input The Password", Toast.LENGTH_LONG).show();
                    s_passs.setError("Enter The password ");
                    return;
                }


                usernameforlogin = s_email.getText().toString();
                passwordforlogin = s_passs.getText().toString();
                auth.signInWithEmailAndPassword(usernameforlogin, passwordforlogin)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {

                                    // there was an error
                                    Toast.makeText(MainActivity.this, "UserName or Password is in correct", Toast.LENGTH_SHORT).show();

                                } else {
                                    progres.dismiss();
                                    Intent call = new Intent(MainActivity.this, Chat_Main_View.class);
                                    startActivity(call);
                                }

                            }
                        });
            }
        });


        jump_to_signup = (TextView) findViewById(R.id.singup_calling_button);
        jump_to_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Signup_Form.class);
                startActivity(i);
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {

                    // User is signed in


//                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Intent call = new Intent(MainActivity.this,Chat_Main_View.class);
                    call.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(call);
                    Log.d("TAG", "onAuthStateChanged:signed_out");
                }
//                 ...
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(mAuthListener);

    }

    public static interface AnimationForPullerBottomListener {
//        ConversationActivity.AnimationForPullerBottomListener
    }
}
