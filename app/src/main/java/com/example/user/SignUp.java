package com.example.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUp extends AppCompatActivity {
   private EditText name,email,password,conformpassword;
   private TextView login,forgotpassword;
   private Button signup;
   private ProgressBar progressBar;
   private FirebaseAuth firebaseAuth;
   private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FirebaseUser CurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name=(EditText) findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        conformpassword=(EditText)findViewById(R.id.confirmpass);
//        login=(TextView)findViewById(R.id.signup_login);
        signup=findViewById(R.id.signup);
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        firebaseAuth=FirebaseAuth.getInstance();

//        email.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                cheakInputs();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        name.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                cheakInputs();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        password.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                signup.setEnabled(false);
//                signup.setTextColor(Color.argb(50,250,250,250));
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        conformpassword.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                signup.setEnabled(false);
//                signup.setTextColor(Color.argb(50,250,250,250));
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    private void cheakInputs() {
//        if(!TextUtils.isEmpty(email.getText())) {
//            if(!TextUtils.isEmpty(name.getText())) {
//                if(!TextUtils.isEmpty(password.getText())&& password.length()>=8) {
//                    if(!TextUtils.isEmpty(conformpassword.getText())) {
//                        signup.setEnabled(true);
//                        signup.setTextColor(Color.rgb(250,250,250));
//                    }
//                    else{
//                        signup.setEnabled(false);
//                        signup.setTextColor(Color.argb(50,255,255,255));
//                    }
//                }
//                else{
//                    signup.setEnabled(false);
//                    signup.setTextColor(Color.argb(50,255,255,255));
//
//                }
//            }
//            else{
//                signup.setEnabled(false);
//                signup.setTextColor(Color.argb(50,255,255,255));
//
//            }
//        }
//        else{
//            signup.setEnabled(false);
//            signup.setTextColor(Color.argb(50,250,250,250));
//
//        }
//    }
findViewById(R.id.loginactivity).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent=new Intent(SignUp.this,SignIn.class);
        startActivity(intent);
        finish();
    }
});

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
               Toast.makeText(SignUp.this,"Clicked",Toast.LENGTH_SHORT).show();
             cheakEmailAndPassword();
            }
        });
    }
    private void cheakEmailAndPassword(){
        if(email.getText().toString().matches(emailPattern)){
            if(password.getText().toString().equals(conformpassword.getText().toString())){
                //progressBar.setVisibility(View.VISIBLE);
               // signup.setEnabled(false);
                signup.setTextColor(Color.argb(50,255,255,255));

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(SignUp.this,"User is created",Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    CurrentUser=firebaseAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(""+name.getText().toString())
                                            .build();
                                    CurrentUser.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(SignUp.this,"DisplayName uploaded Updated",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                    CurrentUser.updateEmail(email.getText().toString())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        //mProgress2.setVisibility(View.GONE);
                                                        Toast.makeText(SignUp.this, "Email updated", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                }
                                else{
                                    progressBar.setVisibility(View.GONE);
                                    //progressBar.setVisibility(View.INVISIBLE);
                                    signup.setEnabled(true);
                                    signup.setTextColor(Color.rgb(250,250,250));
                                    String error=task.getException().getMessage();
                                    Toast.makeText(SignUp.this,error,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else{
                conformpassword.setError("password deosn't match!");
                progressBar.setVisibility(View.GONE);
            }
        }
        else{
            email.setError("Envalid Email!");
            progressBar.setVisibility(View.GONE);
        }
    }

}
