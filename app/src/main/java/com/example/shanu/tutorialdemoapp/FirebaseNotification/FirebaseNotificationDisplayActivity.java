package com.example.shanu.tutorialdemoapp.FirebaseNotification;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.shanu.tutorialdemoapp.R;

public class FirebaseNotificationDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_notification_display);

        TextView textView = findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("payload"));
    }
}
