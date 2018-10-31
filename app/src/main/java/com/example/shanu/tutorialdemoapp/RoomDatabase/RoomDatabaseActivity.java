package com.example.shanu.tutorialdemoapp.RoomDatabase;

import android.arch.persistence.room.Room;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shanu.tutorialdemoapp.R;

import java.util.List;

public class RoomDatabaseActivity extends AppCompatActivity {

    RecyclerView recyclerView ;

    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_database);


        appDatabase = Room.databaseBuilder(this, AppDatabase.class,"user_db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        Log.d("recy", "onCreate: "+appDatabase.roomInterfaceDao().getAllUsers().size());

        recyclerView = findViewById(R.id.recyclerView);

        setRecyclerViewAdapter();

    }



    private void setRecyclerViewAdapter(){

        final List<User> users = appDatabase.roomInterfaceDao().getAllUsers();
        Log.d("recy", "setRecyclerViewAdapter: "+users.get(0).getName());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(RoomDatabaseActivity.this)
                        .inflate(android.R.layout.simple_list_item_1, parent,  false);

                TextView textView = new TextView(getApplicationContext());
                view.setPadding(40,40,40,40);

                textView.setTextSize( TypedValue.COMPLEX_UNIT_SP, 16);

                return new RecyclerView.ViewHolder(view) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                TextView textView = (TextView) holder.itemView;
                textView.setTextColor(Color.BLACK);
                textView.setText(users.get(position).getName() + "\n" + users.get(position).getMob());
            }

            @Override
            public int getItemCount() {
                return users.size();
            }


        });
    }

    public void addUser(View view) {



        EditText nameText = findViewById(R.id.editText);
             String name = nameText   .getText().toString();

        EditText mobText = findViewById(R.id.editText2);
              String mob =mobText.getText().toString();

              if(name.isEmpty() || mob.isEmpty()){
                  Toast.makeText(this, "Emtpy", Toast.LENGTH_SHORT).show();
                  return;
              }

        User user = new User();
        user.setName(name);
        user.setMob(mob);

        appDatabase.roomInterfaceDao().addUser(user);

        nameText.setText("");
        mobText.setText("");
        setRecyclerViewAdapter();

    }
}
