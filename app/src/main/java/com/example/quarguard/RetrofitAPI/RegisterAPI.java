package com.example.quarguard.RetrofitAPI;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/api/v1/login")
    public void insertLocation(
            @Field("phoneNumber") String latitude,
            @Field("password") String longitude,
            Callback<Response> callback);
}
