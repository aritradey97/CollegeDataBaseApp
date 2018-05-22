package com.btechviral.android.collegedatabaseapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class VideoActivity extends AppCompatActivity {

    private Uri videoUri;
    private static final int REQUEST_CODE = 101;
    private StorageReference videoReference;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private String semester;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        semester = "semester";

        if(Uid == null){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


        videoReference = storageReference.child("/videos" + "/userIntro" + Long.toString(System.currentTimeMillis()) + Calendar.getInstance().getWeekYear()+ ".3gp");

    }
    public void Upload(View view){
        if(videoUri != null){
            UploadTask uploadTask = videoReference.putFile(videoUri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(VideoActivity.this, "Upload failed:" +
                            e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(VideoActivity.this,
                            "Upload Complete", Toast.LENGTH_LONG).show();
                    VideoUpload videoUpload = new VideoUpload(""+System.currentTimeMillis(), taskSnapshot.getDownloadUrl().toString());
                    progressBar.setProgress(0);
                    String uploadId = databaseReference.push().getKey();
                    databaseReference.child(semester).child("videos").child(uploadId).setValue(videoUpload);


                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    updateProgress(taskSnapshot);
                }
            });
        } else {
            Toast.makeText(VideoActivity.this, "Nothing to Upload", Toast.LENGTH_LONG).show();
        }
    }

    public void updateProgress(UploadTask.TaskSnapshot taskSnapshot) {
        long fileSize = taskSnapshot.getTotalByteCount();
        long UploadBytes = taskSnapshot.getBytesTransferred();


        long progress = (100 * UploadBytes) / fileSize;
        progressBar.setProgress((int)progress);
    }

    public void record(View view){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void  download(View view){
        try{
            final File file = File.createTempFile("userIntro", "3gp");
            videoReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(VideoActivity.this, "Download Complete", Toast.LENGTH_SHORT).show();
                    final VideoView videoView = (VideoView)findViewById(R.id.videoView);
                    videoView.setVideoURI(Uri.fromFile(file));
                    videoView.start();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(VideoActivity.this, "Download Failed:" +
                            e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(VideoActivity.this, "Failed to create temp file" +
                    e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        videoUri = intent.getData();
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Toast.makeText(VideoActivity.this, "Video Saved:" +
                        videoUri, Toast.LENGTH_LONG).show();
            } else if(resultCode == RESULT_CANCELED){
                Toast.makeText(VideoActivity.this,
                        "Video recording cancelled" , Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(VideoActivity.this,
                        "Failed to record video" , Toast.LENGTH_LONG).show();
            }
        }
    }
}
