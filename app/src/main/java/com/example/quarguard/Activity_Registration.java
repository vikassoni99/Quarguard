package com.example.quarguard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;

public class Activity_Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button createBtn;
    Button loginBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registration);

        createBtn=findViewById(R.id.Btn_CreateAcc);
        loginBtn=findViewById(R.id.TextBtn_login);
        //Spinner Gender
        Spinner genderSpinner=findViewById(R.id.SpinnerGender);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,R.array.genderArray,android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(this);


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
                Intent intent_registerToLogin =new  Intent(getApplicationContext(), Activity_Login.class);
                startActivity(intent_registerToLogin);
            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String strGenderVal = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),strGenderVal,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
