package com.example.shanu.tutorialdemoapp.RestApi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shanu.tutorialdemoapp.R;
import com.example.shanu.tutorialdemoapp.RestApi.Interfaces.RetrofitClientAPI;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRegistrationActivity extends AppCompatActivity {

    RetrofitClientAPI retrofitClientAPI;
    String baseUrl = "http://gateway.anmolenterprises.org/webservices/";
    EditText name, email, password, mob_no;
    String TAG = RetrofitRegistrationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_registration);

        retrofitClientAPI = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitClientAPI.class);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mob_no = findViewById(R.id.mob_no);

    }

    public void onRegister(View view) {


        Call<ResponseBody> registrationResponseCall = retrofitClientAPI
                .registerUser(name.getText().toString(),
                        email.getText().toString(),
                        mob_no.getText().toString(),
                        password.getText().toString());

        registrationResponseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d(TAG, "onResponse: success = "+response.isSuccessful());
                Log.d(TAG, "onResponse: raw = "+response.raw().toString());

                if(response.isSuccessful()){
                    try {
                        Log.d(TAG, "onResponse: "+response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Toast.makeText(RetrofitRegistrationActivity.this, response.body()
                                        .string()
                                , Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RetrofitRegistrationActivity.this,"Error: "+ t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
