package com.example.shanu.tutorialdemoapp;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shanu.tutorialdemoapp.FacebookLogin.FacebookLoginActivity;
import com.example.shanu.tutorialdemoapp.RestApi.RetrofitGithubRequestActivity;
import com.example.shanu.tutorialdemoapp.RestApi.RetrofitRegistrationActivity;
import com.example.shanu.tutorialdemoapp.RestApi.RetrofitRequestActivity;
import com.example.shanu.tutorialdemoapp.RestApi.SimpleHTTPRequestActivity;
import com.example.shanu.tutorialdemoapp.RoomDatabase.RoomDatabaseActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
     MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        mediaRecorder = new MediaRecorder();
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

    }




    public void onSimpleHttpRequestClick(View view) {
        startActivity(new Intent(this, SimpleHTTPRequestActivity.class));
    }


    public void onRetrofitRequest(View view) {
        startActivity(new Intent(this, RetrofitRequestActivity.class));
    }

    public void onGithubRequest(View view) {
        startActivity(new Intent(this, RetrofitGithubRequestActivity.class));
    }

    public void onRoomDatabaseClick(View view) {
        startActivity(new Intent(this, RoomDatabaseActivity.class));
    }

    public void onSoundRecordClick(final View view){
        if(true)
            return;

         mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory()+"/rec/"+System.currentTimeMillis()+".3gp");
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();

        Toast.makeText(this, "Recording sound for 5 secs", Toast.LENGTH_SHORT).show();
        view.setEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "REcording stopped", Toast.LENGTH_SHORT).show();
                mediaRecorder.stop();
                view.setEnabled(true);
            }
        },5000);
    }

    public void onRetrofitLogin(View view) {
        startActivity(new Intent(this, RetrofitLoginRegistrationActivity.class));
    }

    public void onRetrofitRegisterOnly(View view) {
        startActivity(new Intent(this, RetrofitRegistrationActivity.class));
    }

    public void onFacebookLogin(View view) {

        startActivity(new Intent(this, FacebookLoginActivity.class));
    }
}
