package com.example.shanu.tutorialdemoapp.RestApi.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shanu.tutorialdemoapp.R;
import com.example.shanu.tutorialdemoapp.RestApi.Model.Joke;

public class FragmentSimpleTextView extends Fragment {
    private String TAG = FragmentSimpleTextView.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_simple_textview, container, false);


        if(getArguments()== null)
            return view;


        Joke joke = getArguments().getParcelable("text");

        String text = null;
        if (joke != null) {
            text = joke.getSetup();
        }
        TextView textView = view.findViewById(R.id.textView);

        textView.setText("\""+text+ "\"\n\n" + joke.getPunchline());

        Log.d(TAG, "onCreateView: value = "+textView.getText().toString());
        return view;
    }
}
