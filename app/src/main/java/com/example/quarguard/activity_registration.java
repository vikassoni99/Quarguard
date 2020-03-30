package com.example.quarguard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class activity_registration extends AppCompatActivity {

    Button createBtn;
    Button loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registration);
        createBtn=findViewById(R.id.Btn_CreateAcc);
        loginBtn=findViewById(R.id.TextBtn_login);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_registerToMain =new  Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent_registerToMain);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_registerToLogin =new  Intent(getApplicationContext(),activity_login.class);
                startActivity(intent_registerToLogin);
            }
        });




    }
}
