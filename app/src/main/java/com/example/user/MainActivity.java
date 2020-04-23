package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
FirebaseAuth mAuth;
TextView name,email,uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        uid=findViewById(R.id.uid);

        name.setText(mAuth.getCurrentUser().getDisplayName());
        uid.setText(mAuth.getCurrentUser().getUid());
        email.setText(mAuth.getCurrentUser().getEmail());

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
                Intent intent=new Intent(MainActivity.this,SignIn.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
