package com.btechviral.android.collegedatabaseapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private String login;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences settings = getSharedPreferences("LOGIN",0);
        login = settings.getString("login","0");
        final ViewGroup viewGroup = findViewById(R.id.view_group);
        final TextView text = viewGroup.findViewById(R.id.text);
        Handler handler = new Handler();
        TransitionManager.beginDelayedTransition(viewGroup);
        text.setVisibility(View.VISIBLE);

        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {

                if(login.equals("1")){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },5000);
    }
}
