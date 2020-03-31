package com.example.quarguard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Login extends AppCompatActivity {

    Button createNewBtn;
    Button loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        createNewBtn=findViewById(R.id.TextBtn_createANewAcc);
        loginBtn=findViewById(R.id.Btn_Login);

        createNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_loginToSignup =new  Intent(getApplicationContext(), Activity_Registration.class);
                startActivity(intent_loginToSignup);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_loginToMain =new  Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent_loginToMain);
            }
        });
    }
}
