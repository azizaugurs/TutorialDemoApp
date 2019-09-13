package com.example.shanu.tutorialdemoapp.RestApi.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shanu.tutorialdemoapp.R;
import com.example.shanu.tutorialdemoapp.RestApi.Model.Joke;

import java.util.ArrayList;

public class FragmentRecyclerView extends Fragment {
    private String TAG = FragmentRecyclerView.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recyclerview, container, false);


        if(getArguments()== null)
            return view;


        final ArrayList<Joke> jokes = (ArrayList<Joke>) getArguments().getSerializable("list");
        Log.d(TAG, "onCreateView: list is : "+jokes);



        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));


        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                TextView textView = new TextView(getContext());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(textView) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };

                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                TextView textView = (TextView) holder.itemView;
                textView.setText(jokes.get(position).getSetup()+"\n" + jokes.get(position).getPunchline() +"\n\n");
            }

            @Override
            public int getItemCount() {
                return jokes.size();
            }
        });

        return view;
    }
}
