package com.example.quarguard;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Activity_Registration extends AppCompatActivity  {

    Button createBtn;
    Button loginBtn;
    TextInputLayout ageT;
    TextView txtViewDate;
    Calendar cal;
    DatePickerDialog datePickerDialog;
    Spinner genderSpinner;
    Spinner statusSpinner;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registration);

        createBtn=findViewById(R.id.Btn_CreateAcc);
        loginBtn=findViewById(R.id.TextBtn_login);
        ageT = findViewById(R.id.textFieldAge);
        txtViewDate=findViewById(R.id.textFieldDate);
        final List<String> genderList=new ArrayList();
        genderList.add("Men");
        genderList.add("Women");
        genderList.add("Other");

        final List<String> statusList=new ArrayList();
        statusList.add("Recovered");
        statusList.add("Quarantine");

        //Spinner Gender
        genderSpinner=findViewById(R.id.SpinnerGender);
        ArrayAdapter<CharSequence> genderAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,genderList);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String strGenderVal = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(),strGenderVal,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner Status
        statusSpinner=findViewById(R.id.SpinnerStatus);
        ArrayAdapter<CharSequence> statusAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,statusList);
        statusSpinner.setAdapter(statusAdapter);
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String strStatusVal = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(),strStatusVal,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Date Picker for admit
        txtViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal=Calendar.getInstance();
                int day=cal.get(Calendar.DAY_OF_MONTH);
                int month=cal.get(Calendar.MONTH);
                int year=cal.get(Calendar.YEAR);

                datePickerDialog=new DatePickerDialog(Activity_Registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        String s=mDay+" /"+(mMonth+1)+" /"+mYear;
                        txtViewDate.setText(s);

                    }
                },day,month,year);
                datePickerDialog.show();
            }
        });

        //Signup Button
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_registerToMain =new  Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent_registerToMain);
            }
        });
        //Login Button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_registerToLogin =new  Intent(getApplicationContext(), Activity_Login.class);
                startActivity(intent_registerToLogin);
            }
        });




    }


}