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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Activity_Registration extends AppCompatActivity  {

    Button createBtn;
    Button loginBtn;
    TextView txtViewDate;
    Calendar cal;
    DatePickerDialog datePickerDialog;
    Spinner genderSpinner;
    Spinner statusSpinner;
    TextInputEditText edtPatientname;
    TextInputEditText edtFamilyname;
    TextInputEditText edtMobileNumberSignup;
    TextInputEditText edtAlternateNumber;
    TextInputEditText edtAge;
    TextInputEditText edtNationality;
    TextInputEditText edtState;
    TextInputEditText edtCity;
    TextInputEditText edtBlock;
    TextInputEditText edtAddress;
    TextInputEditText edtPasswordSignup;
    //all string values (14)
    String strPatientName;
    String strFamilyName;
    String strMobileNumber;
    String strAlternateNumber;
    String strGender;
    String strAge;
    String strDate;
    String strStatus;
    String strNationality;
    String strState;
    String strCity;
    String strBlock;
    String strAddress;
    String strPasswordSignup;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registration);

        createBtn=findViewById(R.id.Btn_CreateAcc);
        loginBtn=findViewById(R.id.TextBtn_login);
        txtViewDate=findViewById(R.id.textFieldDate);

        createBtn=findViewById(R.id.Btn_CreateAcc);
        loginBtn=findViewById(R.id.TextBtn_login);
        txtViewDate=findViewById(R.id.textFieldDate);

        edtPatientname=findViewById(R.id.EdtPatientName);
        edtFamilyname=findViewById(R.id.EdtFamilyName);
        edtMobileNumberSignup=findViewById(R.id.EdtMobileNumberSignup);
        edtAlternateNumber=findViewById(R.id.EdtAlternateNumber);
        edtAge=findViewById(R.id.EdtAge);
        edtNationality=findViewById(R.id.EdtNationality);
        edtState=findViewById(R.id.EdtState);
        edtCity=findViewById(R.id.EdtCity);
        edtBlock=findViewById(R.id.EdtBlock);
        edtAddress=findViewById(R.id.EdtAddress);
        edtPasswordSignup=findViewById(R.id.EdtPasswordSignup);

        final List<String> genderList=new ArrayList();
        genderList.add("Men");
        genderList.add("Women");
        genderList.add("Other");

        final List<String> statusList=new ArrayList();
        statusList.add("Recovered");
        statusList.add("Quarantine");


        //Signup Button
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strPatientName=edtPatientname.getText().toString();
                strFamilyName=edtFamilyname.getText().toString();
                strMobileNumber=edtMobileNumberSignup.getText().toString();
                strAlternateNumber=edtAlternateNumber.getText().toString();
                //strGender DONE
                strAge=edtAge.getText().toString();
                //strDate DONE
                //strStatus DONE
                strNationality=edtNationality.getText().toString();
                strState=edtState.getText().toString();
                strCity=edtCity.getText().toString();
                strBlock=edtBlock.getText().toString();
                strAddress=edtAddress.getText().toString();
                strPasswordSignup=edtPasswordSignup.getText().toString();
                //Toast.makeText(getApplicationContext(),strGender+"  "+strStatus+" "+strDate,Toast.LENGTH_SHORT).show();


                Intent intent_registerToMain =new  Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent_registerToMain);
            }
        });

        //Spinner Gender
        genderSpinner=findViewById(R.id.SpinnerGender);
        ArrayAdapter<CharSequence> genderAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,genderList);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strGender = parent.getItemAtPosition(position).toString();
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
                strStatus = parent.getItemAtPosition(position).toString();
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
                        strDate=mYear+"-"+(mMonth+1)+"-"+mDay;
                        txtViewDate.setText(strDate);

                    }
                },day,month,year);
                datePickerDialog.show();
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