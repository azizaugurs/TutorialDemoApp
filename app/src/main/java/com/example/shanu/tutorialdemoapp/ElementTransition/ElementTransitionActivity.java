package com.example.shanu.tutorialdemoapp.ElementTransition;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.shanu.tutorialdemoapp.R;

import java.util.Arrays;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class ElementTransitionActivity extends AppCompatActivity {

    @BindView(R.id.listView)ListView listView ;
    String TAG = ElementTransitionActivity.class.getName();

    @BindArray(R.array.list_items) String[] items;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_transition);

        ButterKnife.bind(this);


        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                items));

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Log.d(TAG, "onCreate: position = "+position);
        });

    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }
}
