package com.example.shanu.tutorialdemoapp.RestApi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.shanu.tutorialdemoapp.RestApi.Fragments.FragmentRecyclerView;
import com.example.shanu.tutorialdemoapp.RestApi.Fragments.FragmentSimpleTextView;
import com.example.shanu.tutorialdemoapp.R;
import com.example.shanu.tutorialdemoapp.RestApi.Model.Joke;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SimpleHTTPRequestActivity extends AppCompatActivity {

    private String TAG = SimpleHTTPRequestActivity.class.getSimpleName();
    String randomURL = "https://08ad1pao69.execute-api.us-east-1.amazonaws.com/dev/random_joke";
    String randomTenJokeURL = "https://08ad1pao69.execute-api.us-east-1.amazonaws.com/dev/random_ten";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);

        setTitle(this.getLocalClassName());



    }


    private void sendSimpleHttpJokeRequest(String baseURL){
        try {
            URL url = new URL(baseURL);

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = "";
            StringBuilder response = new StringBuilder();
            while((line = bufferedReader.readLine())!=null){
                response.append(line);
            }


            Gson gson = new GsonBuilder().create();

            if(baseURL.equals(randomURL)) {
                Joke joke = gson.fromJson(response.toString(), Joke.class);

                Bundle bundle = new Bundle();
                bundle.putParcelable("text", joke);

                FragmentSimpleTextView fragmentSimpleTextView = new FragmentSimpleTextView();
                fragmentSimpleTextView.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentSimpleTextView)
                        .commit();
            }
            else {


                JSONArray jsonArray = new JSONArray(response.toString());
                Type listType = new TypeToken<List<Joke>>(){}.getType();

                ArrayList<Joke> jokes;
                jokes = gson.fromJson(response.toString(), listType);

                Bundle bundle = new Bundle();
                bundle.putSerializable("list", jokes);

                FragmentRecyclerView fragmentRecyclerView = new FragmentRecyclerView();
                fragmentRecyclerView.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragmentRecyclerView )
                        .commit();
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public void onRecyclerRequest(View view) {

        Log.d(TAG, "onRecyclerRequest: simple");

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                sendSimpleHttpJokeRequest(randomTenJokeURL);
            }
        });

    }


    public void onTextRequest(View view) {

        Log.d(TAG, "onTextRequest: simple ");
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                sendSimpleHttpJokeRequest(randomURL);
            }
        });
    }
}
