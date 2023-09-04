package com.blind2visionary.andapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText mFullName,mEmail,mPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    //FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn = findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();
        //fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if (fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        mRegisterBtn.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString().trim();

                if(TextUtils.isEmpty(email))// for generating an error message if the emil text field is empty
                {
                    mEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("password is Required");
                    return;
                }
                if (password.length()<6)
                {
                    mPassword.setError("Password length <= than 6");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                //Now registering the user to fire base that is sending the user values to firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //send the verification link
                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Toast.makeText(ngo_Register.this, "Verification Email Has been Sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Log.d("tag","On Failure: Email Not Sent" + e.getMessage());
                                }
                            });
                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();


                            userID = fAuth.getCurrentUser().getUid();
                            //DocumentReference documentReference = fStore.collection("users").document(userID);

                            Map<String,Object> user = new HashMap<>();
                            user.put("fName", fullName);
                            user.put("email", email);

//                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
////                                    Log.d(TAG,"ONsuccess: user stored")
//
//                                }
//                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);

                    }
                });

            }
        });


        mLoginBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),LoginActivity.class)));

    }
}