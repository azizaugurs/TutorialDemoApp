package com.example.shanu.tutorialdemoapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shanu.tutorialdemoapp.RestApi.Interfaces.RetrofitClientAPI;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitLoginRegistrationActivity extends AppCompatActivity {
    String TAG = RetrofitLoginRegistrationActivity.class.getSimpleName();
    ImageView imageView;

    RetrofitClientAPI retrofitClientAPI ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_login_registration);

        imageView = findViewById(R.id.imageView);

        retrofitClientAPI = new Retrofit.Builder()
                .baseUrl("http://gateway.anmolenterprises.org/webservices/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitClientAPI.class);




    }

    public void pickImage(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,101);
    }

    Bitmap bitmap;
    String imgPath;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "onActivityResult: ");


        if(requestCode == 101 && resultCode == Activity.RESULT_OK){
            Uri uri = data.getData();
            if (uri != null) {

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                    findViewById(R.id.uploadImg).setEnabled(true);

                    Log.d(TAG, "onActivityResult: path = "+getRealPath(uri));
                    imgPath = getRealPath(uri) ;
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
            else
                Log.d(TAG, "onActivityResult: uri is null" );
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void uploadImage(View view) {

        if(bitmap!=null){
            Toast.makeText(this, "uploading...", Toast.LENGTH_SHORT).show();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            String encodedString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
         //   Log.d(TAG, "uploadImage: "+encodedString);



//            RequestParams requestParams = new RequestParams();
//            requestParams.put("payment_id",11);
//            requestParams.put("logo", encodedString);
//            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//            asyncHttpClient.post("http://gateway.anmolenterprises.org/webservices/api2.php?apicall=upload_signature",
//                    requestParams, new AsyncHttpResponseHandler() {
//                        @Override
//                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//
//                            Log.d(TAG, "onSuccess: statusCode = "+statusCode);
//                            Log.d(TAG, "onSuccess: "+responseBody);
//
//                        }
//
//                        @Override
//                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//                            Log.d(TAG, "onFailure: code = "+statusCode);
//                            Log.d(TAG, "onFailure: error = "+error.getMessage());
//                            Log.d(TAG, "onFailure: "+responseBody.toString());
//
//                        }
//                    });
//
//
//
//
//
//
//            if(true)
//                return;

            Call<ResponseBody> responseCall = retrofitClientAPI.uploadSignature(11, encodedString);

            responseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    Log.d(TAG, "onResponse: success = "+response.isSuccessful());
                    Log.d(TAG, "onResponse: raw "+response.raw().toString());

                    if(response.body()!=null) {
                        Log.d(TAG, "onResponse: message "+response.message());
                        try {
                            Log.d(TAG, "onResponse: json = "+response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(response.isSuccessful())
                        Toast.makeText(RetrofitLoginRegistrationActivity.this, "image uploaded", Toast.LENGTH_SHORT).show();

                    else
                        Toast.makeText(RetrofitLoginRegistrationActivity.this, response.raw().message(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    Toast.makeText(RetrofitLoginRegistrationActivity.this,"Error: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

//            File file = new File(imgPath);
//            final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
//
//            MultipartBody.Part body =  MultipartBody.Part.createFormData("file",file.getName(), requestBody
//            );
//
//            Call<Image> imageCall = retrofitClientAPI.uploadImage(body);
//            imageCall.enqueue(new Callback<Image>() {
//                @Override
//                public void onResponse(Call<Image> call, Response<Image> response) {
//
//                    Log.d(TAG, "onResponse: "+response.body());
//                    Log.d(TAG, "onResponse: is success = "+response.isSuccessful());
//                    Log.d(TAG, "onResponse: error = "+response.message());
//                    if(response.isSuccessful()){
//                        Image image = response.body();
//                        Log.d(TAG, "onResponse: "+response.body().toString());
//                        Log.d(TAG, "onResponse: "+image.getSuccess());
//                        Toast.makeText(RetrofitLoginRegistrationActivity.this, "Image Uploaded....", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Image> call, Throwable t) {
//                    Log.d(TAG, "onFailure: "+t.getMessage());
//                }
//            });



        }
    }

    private String getRealPath(Uri uri){
        String[] col = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri, col, null, null, null);
        Cursor cursor = getContentResolver().query(uri, col, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToNext();
        String path = cursor.getString(index);
        cursor.close();
        return path;
    }
}
