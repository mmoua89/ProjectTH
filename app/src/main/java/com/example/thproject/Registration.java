package com.example.thproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button register;
    private EditText userEmail, password, name, phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userEmail = findViewById(R.id.usernameEmail);
        password = findViewById(R.id.registerPassword);
        name = findViewById(R.id.nameOfUser);
        phoneNum = findViewById(R.id.phoneNumber);
        register = findViewById(R.id.register);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String user = userEmail.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String nameOfUser = name.getText().toString().trim();
                String phone = phoneNum.getText().toString().trim();

                mAuth.createUserWithEmailAndPassword(user, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){
                                    User userObject = new User(user, pass, nameOfUser, phone);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(userObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){
                                                Toast.makeText(Registration.this, "Registration is successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                                else {
                                    Toast.makeText(Registration.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

    }
}