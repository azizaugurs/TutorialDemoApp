package com.example.shanu.tutorialdemoapp.FacebookLogin;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shanu.tutorialdemoapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FacebookLoginActivity extends AppCompatActivity {

    Button  logout;
    Button login;

    CallbackManager callbackManager;
    private String TAG = "FirebaseFacebook";
     FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  printKeyHash(this);
        setContentView(R.layout.activity_facebook_login);
        Log.d(TAG, "onCreate: facebook init = "+FacebookSdk.isInitialized());

         auth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();



        login = findViewById(R.id.login);
        logout = findViewById(R.id.logout);

        logout.setEnabled(false);


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                Log.i(TAG, "onSuccess: ");

          //      Log.i(TAG, "onSuccess: "+loginResult.getAccessToken().getToken());
           //     Log.i(TAG, "onSuccess: "+loginResult.getAccessToken().getUserId());

              //  login.setEnabled(false);
              //  logout.setEnabled(true);

                handleFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

                Log.i(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {



                Log.e(TAG, "onError: "+error.getMessage() );
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!v.isEnabled()){
                    Toast.makeText(FacebookLoginActivity.this, "Not Allowed...", Toast.LENGTH_SHORT).show();
                    return;
                }



//                testEmailAuth();
//
//                if(true)
//                    return;

                LoginManager.getInstance()
                        .logInWithReadPermissions(FacebookLoginActivity.this,
                                Arrays.asList("email","public_profile"));


            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!v.isEnabled()){
                    Toast.makeText(FacebookLoginActivity.this, "Not Allowed...", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginManager.getInstance()
                        .logOut();

                Toast.makeText(FacebookLoginActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
               // login.setEnabled(true);
              // logout.setEnabled(false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    
    private void testEmailAuth(){
        String random = "test"+System.currentTimeMillis()+"@test.com";
        String randomPass = System.currentTimeMillis()+"##";

        Log.i(TAG, "testEmailAuth: with "+random);
        
        auth.createUserWithEmailAndPassword(random, randomPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            Toast.makeText(FacebookLoginActivity.this, "Login success full", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "onComplete: email = "+task.getResult().getUser().getEmail());
                        }
                        else
                            Toast.makeText(FacebookLoginActivity.this, "Error in login", Toast.LENGTH_SHORT).show();


                    }
                });
    }



    //  Augurs.comp123@gmail.com
    // augurs@123

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
                // String key = pin_map String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
                System.out.println("key                "+key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }


    private void handleFacebook(AccessToken accessToken){




        Log.d(TAG, "handleFacebook: Handling firebase");

        AuthCredential  authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());


            Log.d(TAG, String.valueOf("handleFacebook: authcred = "+authCredential.getSignInMethod()));




                auth.signInWithCredential(authCredential)
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Log.i(TAG, "onFailure: ");
                     }
                 })       
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "onComplete: "+task.isSuccessful());

                        if(task.isSuccessful()){
                            String uid = auth.getCurrentUser().getUid();
                            String email = auth.getCurrentUser().getEmail();
                            String name = auth.getCurrentUser().getDisplayName();



                            Log.i(TAG, "onComplete: uid = "+uid);
                            Log.i(TAG, "onComplete: email = "+email);
                            Log.i(TAG, "onComplete: name = "+name);
                        }
                        else
                            Toast.makeText(FacebookLoginActivity.this, "Firebase facebook Login failed", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Log.i(TAG, "onSuccess: ");
                        
                        Log.i(TAG, "onSuccess: "+authResult.getUser().getUid());
                    }
                })        
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: "+e.getMessage() );
                    }
                })
                ;
    }


}
