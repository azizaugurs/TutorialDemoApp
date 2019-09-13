package com.example.shanu.tutorialdemoapp.oauth2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.shanu.tutorialdemoapp.R;

import ca.mimic.oauth2library.OAuth2Client;

public class OAuth2ApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth2_api);

        OAuth2Client oAuth2Client = new OAuth2Client.Builder("81iihp9lae27qw","Yx5ijAVz12tm6iua","https://api.linkedin.com/v2/me")
//                .grantType()
                .build();

        oAuth2Client.requestAccessToken(response -> {
            Log.d("OAuth2ApiActivity", "onCreate: success = "+response.isSuccessful());
            Log.d("OAuth2ApiActivity", "onCreate: "+response.getBody());
       });


    }
}
