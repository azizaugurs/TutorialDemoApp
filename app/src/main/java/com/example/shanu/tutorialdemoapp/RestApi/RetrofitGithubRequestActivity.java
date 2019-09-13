package com.example.shanu.tutorialdemoapp.RestApi;

import android.app.ProgressDialog;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shanu.tutorialdemoapp.R;
import com.example.shanu.tutorialdemoapp.RestApi.Interfaces.RetrofitClientAPI;
import com.example.shanu.tutorialdemoapp.RestApi.Model.Github;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitGithubRequestActivity extends AppCompatActivity {

    RetrofitClientAPI retrofitClientAPI;
    String TAG = RetrofitClientAPI.class.getSimpleName();
    String BASE_URL_GITHUB = "https://api.github.com/";

    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_github_request);

         retrofitClientAPI = new Retrofit
                .Builder()
                .baseUrl(BASE_URL_GITHUB)
                 .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitClientAPI.class);

         recyclerView = findViewById(R.id.recyclerView);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));
         recyclerView.setHasFixedSize(true);
         recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


    }

    public void onSearchUser(View view) {

        final ProgressDialog progressDialog = ProgressDialog.show(this, "","Loading...",true,false);

        EditText editText = findViewById(R.id.search_bar);

        Call<List<Github>> call = retrofitClientAPI.getRepos(editText.getText().toString());

        call.enqueue(new Callback<List<Github>>() {
            @Override
            public void onResponse(Call<List<Github>> call, Response<List<Github>> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+response.body());
                    Toast.makeText(RetrofitGithubRequestActivity.this, "Found "+response.body().size()+" repositories", Toast.LENGTH_SHORT).show();

                    final List<Github> data = response.body();

                    recyclerView.setAdapter(new RecyclerView.Adapter() {
                        @NonNull
                        @Override
                        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                            TextView textView = new TextView(RetrofitGithubRequestActivity.this);
                            textView.setPadding(30,30,30,30);
                            textView.setTextSize( TypedValue.COMPLEX_UNIT_SP, 16);
                            textView.setTextColor(Color.BLACK);

                            return new RecyclerView.ViewHolder(textView) {
                                @Override
                                public String toString() {
                                    return super.toString();
                                }
                            };
                        }

                        @Override
                        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                            TextView textView = (TextView) holder.itemView;
                            Github item = data.get(position);

                            String text = item.getName()+"\n" +
                                    item.getFullName()+"\n"+
                                    "Owner : "+item.getOwner().getLogin()+"\n"+
                                    item.getOwner().getType()+"\n"+
                                    "Private : "+item.getPrivate();
                            textView.setText( text  );
                            Log.d(TAG, "onBindViewHolder: "+text);


                        }

                        @Override
                        public int getItemCount() {
                            return data.size();
                        }
                    });
                }
                else {
                    Log.d(TAG, "onResponse: Unsuccessful " + response.message());
                    Toast.makeText(RetrofitGithubRequestActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Github>> call, Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });




    }
}
