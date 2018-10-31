package com.example.shanu.tutorialdemoapp.RestApi.Interfaces;

import com.example.shanu.tutorialdemoapp.RestApi.Model.Github;
import com.example.shanu.tutorialdemoapp.RestApi.Model.Image;
import com.example.shanu.tutorialdemoapp.RestApi.Model.Joke;
import com.example.shanu.tutorialdemoapp.RestApi.Model.RegistrationResponse;
import com.example.shanu.tutorialdemoapp.RestApi.Model.SignatureResponse;
import com.google.gson.JsonElement;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitClientAPI {

    @GET("random_joke")
    Call<Joke> getRandomJoke();

    @GET("random_ten")
    Call<List<Joke>> getTenRandomJokes();

    @GET("users/{user}/repos")
    Call<List<Github>> getRepos(@Path("user") String user);

    @Multipart
    @POST("image")
    Call<Image> uploadImage(@Part MultipartBody.Part part);

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> registerUser(@Field("name") String name,
                                    @Field("email") String email,
                                    @Field("mob_no") String mob_no,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("api2.php?apicall=upload_signature")
    Call<ResponseBody> uploadSignature(@Field("payment_id") int payment_id,
                                            @Field("logo") String logo);

    @FormUrlEncoded
    @POST("api2.php?apicall=upload_signature")
    Call<ResponseBody> uploadSignature(@Field("payment_id") int payment_id);
}
