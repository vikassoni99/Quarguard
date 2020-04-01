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
    @POST("")
    public void uploadText(
            @Field("text") String text,
            @Field("authorization") String authorization,
            Callback<Response> callback
    );

}
