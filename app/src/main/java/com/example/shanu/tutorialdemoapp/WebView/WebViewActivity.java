package com.example.shanu.tutorialdemoapp.WebView;

import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.example.shanu.tutorialdemoapp.R;
import com.example.shanu.tutorialdemoapp.ViewModelExample.DataModel;

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
