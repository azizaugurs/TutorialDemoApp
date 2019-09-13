package com.example.shanu.tutorialdemoapp.ElementTransition;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.shanu.tutorialdemoapp.R;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

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
