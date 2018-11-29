package com.example.shanu.tutorialdemoapp;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shanu.tutorialdemoapp.FacebookLogin.FacebookLoginActivity;
import com.example.shanu.tutorialdemoapp.RestApi.RetrofitGithubRequestActivity;
import com.example.shanu.tutorialdemoapp.RestApi.RetrofitRegistrationActivity;
import com.example.shanu.tutorialdemoapp.RestApi.RetrofitRequestActivity;
import com.example.shanu.tutorialdemoapp.RestApi.SimpleHTTPRequestActivity;
import com.example.shanu.tutorialdemoapp.RoomDatabase.RoomDatabaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
     MediaRecorder mediaRecorder;
     String TAG = MainActivity.class.getName();
     String UID_XIAOMI = "dgWoyov449WuSClnosrg6ZnksxR2";
     String UID_EMULATOR = "pwQGX9bsyXN0uerPCUqBkXs2fm82";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        mediaRecorder = new MediaRecorder();
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        FirebaseInstanceId.getInstance().getInstanceId()
               // .add
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {

                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String token = instanceIdResult.getToken();

                        Map<String,String> map =new HashMap<>();
                        map.put("token", token);

                        if(getUid()!=null){

                            FirebaseDatabase.getInstance().getReference("tokens")
                                    .child(getUid())
                                    //.push()
                                    .setValue(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: Token added");
                                        }
                                    });
                        }
                    }
                })
            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if(!task.isSuccessful())
                    Log.d(TAG, "onComplete: "+task.getException());
                }
            })
        ;

    }



    private void insertRandomNotifications(){

       final Map<String, String> map = new HashMap<>();

        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            Toast.makeText(this, "No active login, login first", Toast.LENGTH_SHORT).show();
            return;
        }

       final String targetUID = getUid().equalsIgnoreCase(UID_EMULATOR)?UID_XIAOMI:UID_EMULATOR;

        FirebaseDatabase.getInstance().getReference("tokens").child(targetUID)
                .child("token")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(!dataSnapshot.exists())
                            return;

                        String token = dataSnapshot.getValue(String.class);

                        map.put("from",getUid() );
                        map.put("message", "Test message at "+System.currentTimeMillis());
                        map.put("token", token);
                        map.put("to", targetUID );
                        FirebaseDatabase.getInstance()
                                .getReference("notification")
                                .push()
                                .setValue(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: ");
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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

    public void onRandomNodeInsertion(View view) {
        insertRandomNotifications();
    }


    public void showLoginDialog(final View view){

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Log.d(TAG, "showLoginDialog: no user");
            FirebaseAuth.getInstance().signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    ((Button)view).setText("Logout Anonymous");
                    Toast.makeText(MainActivity.this, "Anonymous login", Toast.LENGTH_SHORT).show();

                }
            })
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            Log.d(TAG, "onComplete: "+task.getException());
                        }
                    });
            }
        else{
            Log.d(TAG, "showLoginDialog: logging out user");
            FirebaseAuth.getInstance().signOut();
            ((Button)view).setText("Login Anonymous");
            Toast.makeText(MainActivity.this, "Anonymous logout", Toast.LENGTH_SHORT).show();
        }


    }


    public static String getUid(){
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
            return null;

        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
