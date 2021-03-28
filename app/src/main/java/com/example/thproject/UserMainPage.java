package com.example.thproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserMainPage extends AppCompatActivity {

    private TextView name, email;
    private FirebaseUser firebaseUser;
    private DatabaseReference userReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page);

        name = findViewById(R.id.nameOfUser1);
        email = findViewById(R.id.usernameEmailTest);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userReference = FirebaseDatabase.getInstance().getReference("Users");
        String userObj = firebaseUser.getUid();

        userReference.child(userObj).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userObject = snapshot.getValue(User.class);
                if (userObject != null){
                    String nameOfUser = userObject.name;
                    String userEmail = userObject.username;

                    name.setText(nameOfUser);
                    email.setText(userEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}