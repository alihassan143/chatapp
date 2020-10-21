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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText username,email,password;
    Button signup;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.entername);
        password=findViewById(R.id.enterpassword);
        email=findViewById(R.id.enteremail);
        signup=findViewById(R.id.btnsignup);
        firebaseAuth=FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String useremail=email.getText().toString();
                String userpassword=password.getText().toString();
                if (TextUtils.isEmpty(useremail)||TextUtils.isEmpty(userpassword)||TextUtils.isEmpty(name)){
                    Toast.makeText(RegisterActivity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }else{
                    RegisterUser(name,useremail,userpassword);
                }
            }
        });
    }
    private void RegisterUser(final String username,String userEmail,String userPassword){
        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid=firebaseUser.getUid();
                    databaseReference=FirebaseDatabase.getInstance().getReference("MyUsers").child(userid);
                    HashMap<String,String>hashMap=new HashMap<>();
                    hashMap.put("Id",userid);
                    hashMap.put("Username",username);
                    hashMap.put("ImageUrl","default");
                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            }

                        }
                    });
                }
                else{
                    Toast.makeText(RegisterActivity.this, "RegisterFailed", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}