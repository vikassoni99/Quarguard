package com.example.quarguard.RetrofitAPI;

import android.content.ContentValues;
import android.graphics.Bitmap;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/api/v1/upload/location")
    public void insertLocation(
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("authorization") String authorization,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/api/v1/upload/location")
    public void insertQurantineLocation(
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("authorization") String authorization,
            Callback<Response> callback);


    @FormUrlEncoded
    @POST("/api/v1/login")
    public void loginUser(
            @Field("phoneNumber") String phoneNumber,
            @Field("password") String password,
            Callback<Response> callback
    );


    @FormUrlEncoded
    @POST("/api/v1/upload/image")
    public void uploadPhoto(
            @Field("file") String file,
            @Field("authorization") String authorization,
            Callback<Response> callback
            );

    @FormUrlEncoded
    @POST("/api/v1/upload/voicenote")
    public void uploadVoice(
            @Field("file") String file,
            @Field("authorization") String authorization,
            Callback<Response> callback
    );


    @FormUrlEncoded
    @POST("/api/v1/upload/text")
    public void uploadEmergencyText(
            @Field("text") String text,
            @Field("authorization") String authorization,
            Callback<Response> callback
    );


    @FormUrlEncoded
    @POST("/api/v1/outofarea")
    public void uploadLocationContinues(
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("authorization") String authorization,
            Callback<Response> callback
    );


    @FormUrlEncoded
    @POST("/api/v1/register")
    void registerUser(
            @Field("name1") String name1,
            @Field("name2") String name2,
            @Field("phoneNumber1") String phoneNumber1,
            @Field("phoneNumber2") String phoneNumber2,
            @Field("age") String age,
            @Field("gender") String gender,
            @Field("dateAnnounced") String dateAnnounced,
            @Field("currentStatus") String currentStatus,
            @Field("detectedCity") String detectedCity,
            @Field("block") String block,
            @Field("detectedState") String detectedState,
            @Field("nationality") String nationality,
            @Field("address") String address,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("travel") String travel,
            @Field("password") String password,
            @Field("cough") String cough,
            @Field("fever") String fever,
            @Field("breathing") String breathing,
            Callback<Response> callback
    );

}
