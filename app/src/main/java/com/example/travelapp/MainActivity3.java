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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        register=findViewById(R.id.button);
        email=findViewById(R.id.editTextTextEmailAddress);
        passw=findViewById(R.id.editTextTextPassword);
        tlogin=findViewById(R.id.textView5);
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

        char firstChar = Passw.charAt(0);
        if (!Character.isAlphabetic(firstChar)) {
            Toast.makeText(MainActivity3.this,"Password Not Strong",Toast.LENGTH_SHORT).show();
            return;
        }

        boolean hasLowercase = false, hasUppercase = false, hasDigit = false;
        for (int i = 0; i < Passw.length(); i++) {
            char c = Passw.charAt(i);
            if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        if(!(hasDigit && hasLowercase && hasUppercase)){
            Toast.makeText(MainActivity3.this,"Password Not Strong",Toast.LENGTH_SHORT).show();
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