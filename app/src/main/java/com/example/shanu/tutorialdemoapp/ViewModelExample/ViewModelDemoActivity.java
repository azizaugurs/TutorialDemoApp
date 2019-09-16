
package com.example.shanu.tutorialdemoapp.ViewModelExample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.shanu.tutorialdemoapp.R;
import com.example.shanu.tutorialdemoapp.WebView.WebViewActivity;

public class ViewModelDemoActivity extends AppCompatActivity {
    String TAG = "ViewModel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model_demo);


        DataModel dataModel = ViewModelProviders.of(this)
                .get(DataModel.class);
        ListView listView = findViewById(R.id.listView);

        dataModel.getLiveData()
                .observe(this, strings -> {

                    Log.d(TAG, "onChanged: "+strings);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,
                            strings);

                    listView.setAdapter(adapter);
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(ViewModelDemoActivity.this,
                        WebViewActivity.class));

            }
        });


    }




}
