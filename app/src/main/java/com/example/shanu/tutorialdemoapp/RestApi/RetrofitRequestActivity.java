package com.example.shanu.tutorialdemoapp.RestApi;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.shanu.tutorialdemoapp.RestApi.Fragments.FragmentRecyclerView;
import com.example.shanu.tutorialdemoapp.RestApi.Fragments.FragmentSimpleTextView;
import com.example.shanu.tutorialdemoapp.R;
import com.example.shanu.tutorialdemoapp.RestApi.Interfaces.RetrofitClientAPI;
import com.example.shanu.tutorialdemoapp.RestApi.Model.Joke;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequestActivity extends AppCompatActivity {

    private String TAG = RetrofitRequestActivity.class.getSimpleName();

    public static String BASE_JOKE_URL = "https://08ad1pao69.execute-api.us-east-1.amazonaws.com/dev/";

    String randomURL = "https://08ad1pao69.execute-api.us-east-1.amazonaws.com/dev/random_joke";
    String randomTenJokeURL = "https://08ad1pao69.execute-api.us-east-1.amazonaws.com/dev/random_ten";
    RetrofitClientAPI retrofitClientAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_JOKE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitClientAPI = retrofit.create(RetrofitClientAPI.class);

        setTitle(this.getLocalClassName());


    }



    public void onTextRequest(View view) {


        Call<Joke> call = retrofitClientAPI.getRandomJoke();
        Log.d(TAG, "onRetrofit TextRequest: ");


        call.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {

              //  Log.d(TAG, "onResponse: " + response.body());
                Log.d(TAG, "onResponse: message = " + response.message());

                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body());
                    Joke joke = response.body();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("text", joke);

                    FragmentSimpleTextView fragmentSimpleTextView = new FragmentSimpleTextView();
                    fragmentSimpleTextView.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragmentSimpleTextView)
                            .commit();


                }
            }


            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }

        });

    }



    public void onRecyclerRequest(View view) {

        Call<List<Joke>> call = retrofitClientAPI.getTenRandomJokes();
        Log.d(TAG, "onRetrofit onRecyclerRequest: ");


        call.enqueue(new Callback<List<Joke>>() {
            @Override
            public void onResponse(Call<List<Joke>> call, Response<List<Joke>> response) {

             //   Log.d(TAG, "onResponse: " + response.body());
                Log.d(TAG, "onResponse: message = " + response.message());

                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body());
                    List<Joke> joke = response.body();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) joke);

                    FragmentRecyclerView fragmentRecyclerView = new FragmentRecyclerView();
                    fragmentRecyclerView.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fragmentRecyclerView)
                            .commit();


                }
            }


            @Override
            public void onFailure(Call<List<Joke>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }

        });


    }
}
