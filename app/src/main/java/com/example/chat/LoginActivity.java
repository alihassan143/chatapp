package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button login,signup;
    FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
    firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.loginemail);
        password=findViewById(R.id.loginpassword);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.orsignup);
        firebaseAuth=FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=email.getText().toString();
                String userpassword=password.getText().toString();
                if (TextUtils.isEmpty(useremail)||TextUtils.isEmpty(userpassword)){
                    Toast.makeText(LoginActivity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.signInWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                               Intent i=new Intent(LoginActivity.this,MainActivity.class);
                               i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                               startActivity(i);
                               finish();
                            }
                        }
                    });
                }

            }

        });
    }
}