package com.blind2visionary.andapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mCreateBtn,forgotpasswordLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar2);
        fAuth =   FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginBtn);
        mCreateBtn = findViewById(R.id.createText);
        forgotpasswordLink = findViewById(R.id.forgotPassword);

        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

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

                //authenticating the user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            progressBar.setVisibility(View.GONE);

                        }
                        else
                        {

                            Toast.makeText(LoginActivity.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


            }
        });
        mCreateBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        forgotpasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  EditText resetMail = new EditText(view.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Receive Reset Link. ");
                passwordResetDialog.setView(resetMail);


                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extracting the email  and sending a reset link
                        String mail  = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                if(mail.isEmpty())
                                {
                                    Toast.makeText(LoginActivity.this, "Enter the Email Address", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Reset Link Sent to Your Email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error! Reset Link is Not Sent"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogI, int which) {
                        //close the dialogue
                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }
}