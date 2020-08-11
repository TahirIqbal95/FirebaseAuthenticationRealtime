package com.mejrabsoft.firebaseauthenticationrealtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mejrabsoft.firebaseauthenticationrealtime.Model.Student;

public class MainActivity extends AppCompatActivity {

    Button mLogout;

    TextView username, useremail, userpass, userphone;
    //  FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    String user;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mLogout = findViewById(R.id.btn_logout);

        username = findViewById(R.id.txt_user_name);
        useremail = findViewById(R.id.txt_user_email);
        userpass = findViewById(R.id.txt_user_password);
        userphone = findViewById(R.id.txt_user_phone);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser().getUid();


        userRef = FirebaseDatabase.getInstance().getReference("Student").child(user);


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String email = dataSnapshot.child("email").getValue().toString();
                String name = dataSnapshot.child("fullname").getValue().toString();
                String pass = dataSnapshot.child("password").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();

                useremail.setText(email);
                username.setText(name);
                userpass.setText(pass);
                userphone.setText(phone);

                Toast.makeText(MainActivity.this, "show user", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                //  Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

            }
        });


    }
}
