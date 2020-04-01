package com.example.quarguard;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.quarguard.CameraActivity.CameraActivity;
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
        edtTravelHistory=findViewById(R.id.EdtTravelHistory);
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
                strTravelHistory=edtTravelHistory.getText().toString();
                strPasswordSignup=edtPasswordSignup.getText().toString();
                //Toast.makeText(getApplicationContext(),strGender+"  "+strStatus+" "+strDate,Toast.LENGTH_SHORT).show();
                Toast.makeText(Activity_Registration.this, latitude, Toast.LENGTH_SHORT).show();
                if (latitude!=null && longitude!=null)
                {
                    registerUser();
                }
                else{
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

                    Toast.makeText(Activity_Registration.this, "Please on your location", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buildGoogleApiClient();

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
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Toast.makeText(Activity_Registration.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                        BufferedReader reader = null;
                        Intent intent_registerToMain =new  Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent_registerToMain);
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