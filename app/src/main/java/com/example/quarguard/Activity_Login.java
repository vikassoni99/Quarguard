package com.example.quarguard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quarguard.MapsUtils.MapsActivity;
import com.example.quarguard.RetrofitAPI.RegisterAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Activity_Login extends AppCompatActivity {

    private static final String ROOT_URL = "https://quarguard.herokuapp.com";
    Button createNewBtn;
    Button loginBtn;
    TextInputEditText phone_number,pass_word;
    String phoneNumber,password;
    TextInputLayout TxtLayPhone,TxtLayPass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        createNewBtn=findViewById(R.id.TextBtn_createANewAcc);
        loginBtn=findViewById(R.id.Btn_Login);
        phone_number = findViewById(R.id.edt_phone_number);
        pass_word = findViewById(R.id.edt_password);

        TxtLayPhone=findViewById(R.id.textFieldPhone);
        TxtLayPass=findViewById(R.id.textFieldPassword);

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
                phoneNumber = phone_number.getText().toString();
                password = pass_word.getText().toString();
                if(phoneNumber.isEmpty() || password.isEmpty()){
                    if(phoneNumber.isEmpty()){
                        TxtLayPhone.setError("Please enter registered mobile number");
                    }else{
                        TxtLayPhone.setError(null);
                    }
                    if(password.isEmpty()){
                        TxtLayPass.setError("Please enter password");
                    }else{
                        TxtLayPass.setError(null);
                    }
                }
                else{
                    TxtLayPhone.setError(null);
                    TxtLayPass.setError(null);
                    loginUser();
                }

            }
        });
    }

    private void loginUser() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build();

        RegisterAPI api = adapter.create(RegisterAPI.class);

        api.loginUser(phoneNumber,
                password,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        quarantinecheck(output);
//                        Intent intent_loginToMain =new  Intent(getApplicationContext(),MainActivity.class);
//                        startActivity(intent_loginToMain);
                        //Displaying the output as a toast
                        //Log.d("result",output);
                        SharedPreferences.Editor editor = getSharedPreferences("tokenPre", MODE_PRIVATE).edit();
                        editor.putString("token", output);
                        editor.apply();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(Activity_Login.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void quarantinecheck(String access) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build();

        RegisterAPI api = adapter.create(RegisterAPI.class);

        api.checkQuarantine(
                access,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (Integer.parseInt(output) == 0){
                            SharedPreferences.Editor editor = getSharedPreferences("tokenPre", MODE_PRIVATE).edit();
                            editor.putString("token", access);
                            editor.apply();
                            Intent i = new Intent(Activity_Login.this,MapsActivity.class);
                            startActivity(i);
                        }
                        else{
                            SharedPreferences.Editor editor = getSharedPreferences("tokenPre", MODE_PRIVATE).edit();
                            editor.putString("token", access);
                            editor.apply();
                            Intent i = new Intent(Activity_Login.this, MainActivity.class);
                            startActivity(i);
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(Activity_Login.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


}

