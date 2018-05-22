package com.btechviral.android.collegedatabaseapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AssignmentActivity extends AppCompatActivity {

    private TextView notification;
    private Button browse, upload;
    private StorageReference firebaseStorage;
    private DatabaseReference databaseReference;
    private Uri pdfUri;
    private String Semester;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        notification = findViewById(R.id.textView);
        browse = findViewById(R.id.button3);
        upload = findViewById(R.id.button2);
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Semester = "semester";

        progressDialog = new ProgressDialog(AssignmentActivity.this);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(AssignmentActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    selectPdf();
                } else {
                    ActivityCompat.requestPermissions(AssignmentActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pdfUri != null){
                    firebaseStorage = storageReference.child("/Assignments" + "/" + pdfUri.toString());
                    UploadTask uploadTask = firebaseStorage.putFile(pdfUri);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AssignmentActivity.this, "Upload failed:" +
                                    e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AssignmentActivity.this, "Upload Complete", Toast.LENGTH_LONG).show();
                            String docs = new String(taskSnapshot.getDownloadUrl().toString());
                            databaseReference.child(Semester).child("AdminId").child("Assignment").push().setValue(docs);
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.setMessage("Uploading...");
                            progressDialog.show();
                        }
                    });
                } else {
                    Toast.makeText(AssignmentActivity.this, "Choose a file", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectPdf();
        } else {
            Toast.makeText(AssignmentActivity.this, "Please provide permission", Toast.LENGTH_SHORT);
        }
    }

    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("Applications/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 86 && requestCode == RESULT_OK){
            pdfUri = data.getData();
            notification.setText(pdfUri.toString());
        } else {
            Toast.makeText(AssignmentActivity.this, "No file is chosen", Toast.LENGTH_SHORT).show();
        }
    }
}
