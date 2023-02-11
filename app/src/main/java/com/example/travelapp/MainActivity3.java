package com.example.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity3 extends AppCompatActivity {


    EditText email,passw;
    Button register,google,apple,twitter;

    TextView tview;

    FirebaseAuth mAuth;
    FirebaseAuth mUser;

    TextView tlogin;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        register=findViewById(R.id.button);
        email=findViewById(R.id.editTextTextEmailAddress);
        passw=findViewById(R.id.editTextTextPassword);
        tlogin=findViewById(R.id.textView5);
        google=findViewById(R.id.button2);
        mAuth=FirebaseAuth.getInstance();
        mUser=FirebaseAuth.getInstance();

        tlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity3.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func();
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glogin();
            }
        });

    }

    private void glogin() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Intent intent=new Intent(MainActivity3.this,MainActivity2.class);
                startActivity(intent);
            } catch (ApiException e) {
                Toast.makeText(MainActivity3.this,"Try Again",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void func() {
        String Email=email.getText().toString();
        String Passw=passw.getText().toString();

        if(Email.length()==0 && Passw.length()==0){
            Toast.makeText(MainActivity3.this,"Enter Credentials Please",Toast.LENGTH_SHORT).show();
            return;
        }



        if(Email.length()==0){
            Toast.makeText(MainActivity3.this,"Enter Email Please",Toast.LENGTH_SHORT).show();
            return;
        }

        if(Passw.length()==0){
            Toast.makeText(MainActivity3.this,"Enter Password Please",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(Email,Passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    mAuth.createUserWithEmailAndPassword(Email,Passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task1) {
                            if(task1.isSuccessful()){
                                Intent intent=new Intent(MainActivity3.this,MainActivity2.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                Toast.makeText(MainActivity3.this,"Successful Login",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }else{
                    Toast.makeText(MainActivity3.this,"User with is Email Already Exists",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}