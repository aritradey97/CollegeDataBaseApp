package com.btechviral.android.collegedatabaseapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    Button buttonRegister;
    EditText emailText, passwordText;
    TextView signin;
    Intent intent;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String semester, username, Uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        buttonRegister = (Button)findViewById(R.id.registerButton);
        emailText = (EditText)findViewById(R.id.editTextEmail);
        passwordText = (EditText)findViewById(R.id.editTextPassword);
        signin = (TextView)findViewById(R.id.signInText);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //final EditText user = findViewById(R.id.userbutton);
        semester = "semester";



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
//                username = user.getText().toString();
//                databaseReference.child(semester).child(Uid).child("username").setValue(username);
            }
        });
    }

    private void registerUser() {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(SignUpActivity.this, "Enter Username to create new user", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(SignUpActivity.this, "Enter Password to create new user", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    //Uid = firebaseAuth.getUid();
                } else{
                    Toast.makeText(SignUpActivity.this, "Register Unsuccessfull please try again", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

    }
}
