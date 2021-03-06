package com.example.quarguard;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.quarguard.CameraActivity.CameraActivity;
import com.example.quarguard.MapsUtils.MapsActivity;
import com.example.quarguard.RetrofitAPI.RegisterAPI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Activity_Registration extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener ,LocationListener{

    private static final String ROOT_URL = "https://quarguard.herokuapp.com";
    Button createBtn;
    Button loginBtn;
    TextView txtViewDate;
    Calendar cal;
    int year,month,day;
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
    TextInputEditText edtTravelHistory;
    TextInputEditText edtPasswordSignup;

    TextInputLayout TxtLayPatientname;
    TextInputLayout TxtLayFamilyname;
    TextInputLayout TxtLayMobileNumberSignup;
    TextInputLayout TxtLayAlternateNumber;
    TextInputLayout TxtLayAge;
    TextInputLayout TxtLayNationality;
    TextInputLayout TxtLayState;
    TextInputLayout TxtLayCity;
    TextInputLayout TxtLayBlock;
    TextInputLayout TxtLayAddress;
    TextInputLayout TxtLayPasswordSignup;
    //all string values (15)
    String strPatientName = "";
    String strFamilyName= "";
    String strMobileNumber= "";
    String strAlternateNumber= "";
    String strGender= "";
    String strAge= "";
    String strDate= "";
    String strStatus= "";
    String strNationality= "";
    String strState= "";
    String strCity= "";
    String strBlock= "";
    String strAddress= "";
    String strTravelHistory= "";
    String strPasswordSignup= "";

    //Location Variables
    private FusedLocationProviderClient fusedLocationProviderClient;
    GoogleApiClient mGoogleApiClient;
    private Location currentLocation;
    LocationRequest mLocationRequest;
    String latitude ,longitude,access;
    private GoogleApiClient googleApiClient;
    private Location mLastLocation;

    //RadioGroup
    RadioGroup GrpCough;
    RadioGroup GrpFever;
    RadioGroup GrpHeadache;

    String strCough= "";
    String strFever= "";
    String strHeadache= "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registration);

        createBtn = findViewById(R.id.Btn_CreateAcc);
        loginBtn = findViewById(R.id.TextBtn_login);
        txtViewDate = findViewById(R.id.textFieldDate);

        TxtLayPatientname=findViewById(R.id.textFieldPatientName);
        TxtLayFamilyname=findViewById(R.id.textFieldFamilyName);
        TxtLayMobileNumberSignup=findViewById(R.id.textFieldMobileNumber);
        TxtLayAlternateNumber=findViewById(R.id.textFieldAlternateNumber);
        TxtLayAge=findViewById(R.id.textFieldAge);
        TxtLayNationality=findViewById(R.id.textFieldNationality);
        TxtLayState=findViewById(R.id.textFieldState);
        TxtLayCity=findViewById(R.id.textFieldCity);
        TxtLayBlock=findViewById(R.id.textFieldBlock);
        TxtLayAddress=findViewById(R.id.textFieldAddress);
        TxtLayPasswordSignup=findViewById(R.id.textFieldPassword);



        createBtn = findViewById(R.id.Btn_CreateAcc);
        loginBtn = findViewById(R.id.TextBtn_login);
        txtViewDate = findViewById(R.id.textFieldDate);

        edtPatientname = findViewById(R.id.EdtPatientName);
        edtFamilyname = findViewById(R.id.EdtFamilyName);
        edtMobileNumberSignup = findViewById(R.id.EdtMobileNumberSignup);
        edtAlternateNumber = findViewById(R.id.EdtAlternateNumber);
        edtAge = findViewById(R.id.EdtAge);
        edtNationality = findViewById(R.id.EdtNationality);
        edtState = findViewById(R.id.EdtState);
        edtCity = findViewById(R.id.EdtCity);
        edtBlock = findViewById(R.id.EdtBlock);
        edtAddress = findViewById(R.id.EdtAddress);
        edtTravelHistory = findViewById(R.id.EdtTravelHistory);
        edtPasswordSignup = findViewById(R.id.EdtPasswordSignup);


        final List<String> genderList = new ArrayList();
        genderList.add("Men");
        genderList.add("Women");
        genderList.add("Other");

        final List<String> statusList = new ArrayList();
        statusList.add("Recovered");
        statusList.add("Quarantine");


        GrpCough = findViewById(R.id.radioGrpCough);
        GrpFever = findViewById(R.id.radioGrpFever);
        GrpHeadache = findViewById(R.id.radioGrpHeadache);

        //Signup Button
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                strPatientName = edtPatientname.getText().toString();
                strFamilyName = edtFamilyname.getText().toString();
                strMobileNumber = edtMobileNumberSignup.getText().toString();
                strAlternateNumber = edtAlternateNumber.getText().toString();
                //strGender DONE
                strAge = edtAge.getText().toString();
                //strDate DONE
                //strStatus DONE
                strNationality = edtNationality.getText().toString();
                strState = edtState.getText().toString();
                strCity = edtCity.getText().toString();
                strBlock = edtBlock.getText().toString();
                strAddress = edtAddress.getText().toString();
                strTravelHistory = edtTravelHistory.getText().toString();
                strPasswordSignup = edtPasswordSignup.getText().toString();
                //Toast.makeText(getApplicationContext(),strGender+"  "+strStatus+" "+strDate,Toast.LENGTH_SHORT).show();
                Toast.makeText(Activity_Registration.this, latitude, Toast.LENGTH_SHORT).show();
                //latitude != null && longitude != null
                if(strPatientName.isEmpty() || strFamilyName.isEmpty() || strMobileNumber.isEmpty() || strAlternateNumber.isEmpty() ||
                strAge.isEmpty() || strNationality.isEmpty() || strState.isEmpty() || strCity.isEmpty() || strBlock.isEmpty() ||
                        strAddress.isEmpty() || strPasswordSignup.isEmpty() ||strDate.isEmpty() || strCough.isEmpty() || strFever.isEmpty() || strHeadache.isEmpty())
                {
                    //Toast.makeText(getApplicationContext()," "+strDate+strCough+strFever+strHeadache,Toast.LENGTH_SHORT).show();
                    if (strDate.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Select date of admit",Toast.LENGTH_SHORT).show();
                    }
                    if (strCough.isEmpty() || strFever.isEmpty() || strHeadache.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Select Symptoms",Toast.LENGTH_SHORT).show();
                    }
                    if (strPatientName.isEmpty()){
                        TxtLayPatientname.setError("This field can't be empty");
                    }else{
                        TxtLayPatientname.setError(null);
                    }

                    if (strFamilyName.isEmpty()){
                        TxtLayFamilyname.setError("This field can't be empty");
                    }else{
                        TxtLayFamilyname.setError(null);
                    }
                    if (strMobileNumber.isEmpty()){
                        TxtLayMobileNumberSignup.setError("This field can't be empty");
                    }else{
                        TxtLayMobileNumberSignup.setError(null);
                    }
                    if (strAlternateNumber.isEmpty()){
                        TxtLayAlternateNumber.setError("This field can't be empty");
                    }else{
                        TxtLayAlternateNumber.setError(null);
                    }
                    if (strAge.isEmpty()){
                        TxtLayAge.setError("This field can't be empty");
                    }else{
                        TxtLayAge.setError(null);
                    }
                    if (strNationality.isEmpty()){
                        TxtLayNationality.setError("This field can't be empty");
                    }else{
                        TxtLayNationality.setError(null);
                    }
                    if (strState.isEmpty()){
                        TxtLayState.setError("This field can't be empty");
                    }else{
                        TxtLayState.setError(null);
                    }
                    if (strCity.isEmpty()){
                        TxtLayCity.setError("This field can't be empty");
                    }else{
                        TxtLayCity.setError(null);
                    }
                    if (strBlock.isEmpty()){
                        TxtLayBlock.setError("This field can't be empty");
                    }else{
                        TxtLayBlock.setError(null);
                    }
                    if (strAddress.isEmpty()){
                        TxtLayAddress.setError("This field can't be empty");
                    }else{
                        TxtLayAddress.setError(null);
                    }
                    if (strPasswordSignup.isEmpty()){
                        TxtLayPasswordSignup.setError("This field can't be empty");
                    }else if(strPasswordSignup.length()<6){
                        TxtLayPasswordSignup.setError("Minimum 6 alphabets required");
                    }else{
                        TxtLayPasswordSignup.setError(null);
                    }

                }
                else
                    {
                    TxtLayPatientname.setError(null);
                    TxtLayFamilyname.setError(null);
                    TxtLayMobileNumberSignup.setError(null);
                    TxtLayAlternateNumber.setError(null);
                    TxtLayAge.setError(null);
                    TxtLayNationality.setError(null);
                    TxtLayState.setError(null);
                    TxtLayCity.setError(null);
                    TxtLayBlock.setError(null);
                    TxtLayAddress.setError(null);
                    TxtLayPasswordSignup.setError(null);

                    if (latitude != null && longitude != null) {
                        Toast.makeText(Activity_Registration.this, "Location Available", Toast.LENGTH_SHORT).show();
                        registerUser();
                    } else {
                        Toast.makeText(Activity_Registration.this, "Please on your location (GPS)", Toast.LENGTH_SHORT).show();
                        PermissionListener permissionlistener = new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                Toast.makeText(Activity_Registration.this, "Location Allowed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionDenied(List<String> deniedPermissions) {
                                Toast.makeText(Activity_Registration.this, "Please on your location(GPS)", Toast.LENGTH_SHORT).show();
                            }
                        };
                        TedPermission.with(Activity_Registration.this)
                                .setPermissionListener(permissionlistener)
                                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                                .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
                                .check();
                    }
                }

            }
        });

        buildGoogleApiClient();

        //Radio Group
        GrpCough.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.CoughYes:
                        strCough = "1";
                        break;
                    case R.id.CoughNo:
                        strCough = "0";
                        break;
                }
            }
        });

        GrpFever.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.FeverYes:
                        strFever = "1";
                        break;
                    case R.id.FeverNo:
                        strFever= "0";
                        break;
                }
            }
        });

        GrpHeadache.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.HeadacheYes:
                        strHeadache = "1";
                        break;
                    case R.id.HeadacheNo:
                        strHeadache = "0";
                        break;
                }
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
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);
        txtViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog=new DatePickerDialog(Activity_Registration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        strDate=mYear+"-"+(mMonth+1)+"-"+mDay;
                        txtViewDate.setText(strDate);
                    }
                },year,month,day);
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
        buildGoogleApiClient();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();




    }

    private void registerUser() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build();

        RegisterAPI api = adapter.create(RegisterAPI.class);

        api.registerUser(
                    strPatientName,
                    strFamilyName,
                    strMobileNumber,
                    strAlternateNumber,
                    strAge,
                    strGender,
                    "2020-04-02T12:10:00.000Z",
                    strStatus,
                    strCity,
                    strBlock,
                    strState,
                    strNationality,
                    strAddress,
                    latitude,
                    longitude,
                    strTravelHistory,
                    strPasswordSignup,
                    strCough,
                    strFever,
                    strHeadache,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Toast.makeText(Activity_Registration.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                            SharedPreferences.Editor editor = getSharedPreferences("tokenPre", MODE_PRIVATE).edit();
                            editor.putString("token", output);
                            editor.apply();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Intent intent_registerToMain =new  Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(intent_registerToMain);
                        //Displaying the output as a toast
                        //Log.d("result",output);

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("errror",String.valueOf(error));
                    }
                }
        );
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            /*Getting the location after aquiring location service*/
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);    // 10 seconds, in milliseconds
            mLocationRequest.setFastestInterval(1000);   // 1 second, in milliseconds
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mLastLocation != null) {

               latitude =  String.valueOf(mLastLocation.getLatitude());
               longitude =  String.valueOf(mLastLocation.getLongitude());
                Toast.makeText(Activity_Registration.this, latitude, Toast.LENGTH_SHORT).show();
            } else {
                /*if there is no last known location. Which means the device has no data for the loction currently.
                 * So we will get the current location.
                 * For this we'll implement Location Listener and override onLocationChanged*/
                Log.i("Current Location", "No data for location found");

                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, Activity_Registration.this);
            }
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(Activity_Registration.this, new
                String[]{ACCESS_FINE_LOCATION}, 100);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        return;
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}