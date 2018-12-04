package com.example.shanu.tutorialdemoapp.WebView;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.example.shanu.tutorialdemoapp.R;
import com.example.shanu.tutorialdemoapp.ViewModelExample.DataModel;

import java.util.List;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    String TAG = WebViewActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);

        DataModel dataModel = ViewModelProviders.of(this)
                .get(DataModel.class);

        dataModel.getLiveData()
                .observe(this, strings -> {
                    Log.d(TAG, "onCreate: strings -> "+strings);
                });

    }
}
