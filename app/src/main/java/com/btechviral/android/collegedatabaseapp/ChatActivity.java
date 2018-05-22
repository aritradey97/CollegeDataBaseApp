package com.btechviral.android.collegedatabaseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private String Uid, Semester, username;
    private ArrayList<String> chats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.d("Uid", Uid);
            final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            databaseReference = FirebaseDatabase.getInstance().getReference();
            chats = new ArrayList<>();
            ListView listView = findViewById(R.id.listview);
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chats);
            listView.setAdapter(adapter);

            final EditText editText = (EditText)findViewById(R.id.editText);
            final Button post = (Button)findViewById(R.id.postbutton);
            final Button cancel = (Button)findViewById(R.id.cancelbutton);
            Semester = "semester";

//            databaseReference.child(Semester).child(Uid).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    username = (String) dataSnapshot.child("username").getValue();
//                    Log.d("User", username);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });


            post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Chat chat = new Chat(editText.getText().toString(), email);
                    databaseReference.child(Semester).child("chats").push().setValue(chat);
                    editText.setText("");
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText("");
                }
            });

            databaseReference.child(Semester).child("chats").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    adapter.add((String)dataSnapshot.child("chat").getValue());
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
