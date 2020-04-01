package com.example.quarguard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Splash extends AppCompatActivity {
    String access;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);


        SharedPreferences prefs = getSharedPreferences("tokenPre", MODE_PRIVATE);
        access = prefs.getString("token", "none");//"No name defined" is the default value.

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(access!="none"){
                    Intent splashIntent =new Intent(Activity_Splash.this,MainActivity.class);
                    startActivity(splashIntent);
                    finish();
                }
                else{
                    Intent splashIntent =new Intent(Activity_Splash.this,Activity_Login.class);
                    startActivity(splashIntent);
                    finish();
                }

            }
        },1000);

    }
}
