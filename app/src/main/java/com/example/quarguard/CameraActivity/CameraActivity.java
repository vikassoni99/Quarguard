package com.example.quarguard.CameraActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.quarguard.Activity_Login;
import com.example.quarguard.MainActivity;
import com.example.quarguard.NoteVoiceOptional.NoteVoiceActivity;
import com.example.quarguard.R;
import com.example.quarguard.RetrofitAPI.RegisterAPI;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CameraActivity extends Activity
{
    private static final String ROOT_URL = "https://quarguard.herokuapp.com";
    final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    final int MY_CAMERA_PERMISSION_CODE = 100;
    Bitmap photo;
    String access;
    TextInputEditText edt_emergency;
    private View btn_sendEmergencyMessage;

    //RadioGroup
    RadioGroup GrpCoughP;
    RadioGroup GrpFeverP;
    RadioGroup GrpHeadacheP;

    String strCoughP= "";
    String strFeverP= "";
    String strHeadacheP= "";

    Button Btn_SendSymptomsPanic;
    private String temp;

    CardView card_clickPicture,card_voiceNote;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        imageView = findViewById(R.id.img_imageview);
        btn_sendEmergencyMessage = findViewById(R.id.Btn_sendEmergencyMsg);
        SharedPreferences prefs = getSharedPreferences("tokenPre", MODE_PRIVATE);
        access = prefs.getString("token", "");//"No name defined" is the default value.
        edt_emergency = findViewById(R.id.edt_emergency_msg);
        Toast.makeText(this, access, Toast.LENGTH_SHORT).show();
        GrpCoughP = findViewById(R.id.radioGrpCoughP);
        GrpFeverP = findViewById(R.id.radioGrpFeverP);
        GrpHeadacheP = findViewById(R.id.radioGrpHeadacheP);
        Btn_SendSymptomsPanic=findViewById(R.id.Btn_Send_Symptoms);
        card_clickPicture=findViewById(R.id.Card_ClickPicture);
        card_voiceNote=findViewById(R.id.Card_VoiceNote);

        //Radio Group
        GrpCoughP.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.CoughPYes:
                        strCoughP = "1";
                        break;
                    case R.id.CoughPNo:
                        strCoughP = "0";
                        break;
                }
            }
        });

        GrpFeverP.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.FeverPYes:
                        strFeverP = "1";
                        break;
                    case R.id.FeverPNo:
                        strFeverP= "0";
                        break;
                }
            }
        });

        GrpHeadacheP.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.HeadachePYes:
                        strHeadacheP = "1";
                        break;
                    case R.id.HeadachePNo:
                        strHeadacheP = "0";
                        break;
                }
            }
        });

        //Button SendSymptoms(Radio)
        Btn_SendSymptomsPanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(CameraActivity.this,strCoughP+strFeverP+strHeadacheP,Toast.LENGTH_SHORT).show();
                if(temp!=null && strCoughP!=null && strFeverP!=null && strHeadacheP!=null){
                    Log.d("access in calling",access);
                    uploadPhotoApi(access,temp,strHeadacheP,strFeverP,strCoughP);
                }
                else{
                    Toast.makeText(CameraActivity.this, "Please fill out all entries", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Button Send Emergency Message
        btn_sendEmergencyMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edt_emergency.getText().toString();
                if(text!=null)
                uploadEmergencyText(text);
                else
                    Toast.makeText(CameraActivity.this,"please Type message",Toast.LENGTH_SHORT).show();
            }
        });



        card_voiceNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, NoteVoiceActivity.class);
                startActivity(intent);
            }
        });


        card_clickPicture.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(CameraActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);

                    }
                }
            }

        });
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            temp = Base64.encodeToString(b, Base64.DEFAULT);
           // uploadPhotoApi(access,temp);
        }
    }



    private void uploadPhotoApi(String access,String temp,String head,String fever,String cough) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build();

        RegisterAPI api = adapter.create(RegisterAPI.class);
        Toast.makeText(this, access, Toast.LENGTH_SHORT).show();
        api.uploadPhoto(
                temp,
                access,
                head,
                fever,
                cough,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Toast.makeText(CameraActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
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

    private void uploadEmergencyText(String text) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        RegisterAPI api = adapter.create(RegisterAPI.class);

        api.uploadEmergencyText(
                text,
                access,
                new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Toast.makeText(CameraActivity.this, "Text Uploaded", Toast.LENGTH_SHORT).show();
                        edt_emergency.setText("Message sent success");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(CameraActivity.this, "Error in uploading text please try again", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }


}