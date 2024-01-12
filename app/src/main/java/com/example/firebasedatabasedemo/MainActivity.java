package com.example.firebasedatabasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floating_add_button;
    FirebaseDatabase database;
    ArrayList<MainModel> studentList;
    DatabaseReference databaseReference;
    CustomAdapter customAdapter;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floating_add_button = (FloatingActionButton) findViewById(R.id.floating_add_button);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        getData();

        floating_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getData() {
//        Query query = FirebaseDatabase.getInstance().getReference().child("students");
        databaseReference = FirebaseDatabase.getInstance().getReference("students");
        studentList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customAdapter = new CustomAdapter(this, MainActivity.this, studentList);
        recyclerView.setAdapter(customAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MainModel student = dataSnapshot.getValue(MainModel.class);
                    studentList.add(student);
                }
                for (int i = 0; i < studentList.size(); i++) {
                    Log.d(TAG, "onDataChange: "+studentList);
                }
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}