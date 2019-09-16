package com.example.shanu.tutorialdemoapp.ViewModelExample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataModel extends ViewModel {
    String TAG = DataModel.class.getName();
    private MutableLiveData<List<String>> userMutableLiveData;


    public LiveData<List<String>> getLiveData(){

        if(userMutableLiveData == null){
            userMutableLiveData = new MutableLiveData<>();
            loadData();
        }

        return userMutableLiveData;

    }



    private void loadData(){


        FirebaseDatabase.getInstance().getReference()
                .child("notification")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     List<String> list = new ArrayList<>();

                        Log.d(TAG, "onDataChange: ");

                        for(DataSnapshot post:dataSnapshot.getChildren()){
                            list.add(post.getValue().toString());
                        }

                        userMutableLiveData.setValue(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    @Override
    protected void onCleared() {
        Log.d(DataModel.class.getName(), "onCleared: ");
        super.onCleared();
    }
}

