package com.example.firebasedatabasedemo;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompat implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
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

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        floating_add_button = (FloatingActionButton) findViewById(R.id.floating_add_button);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //  Toolbar
        setSupportActionBar(toolbar);

        //  Navigation Drawer Menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.setCheckedItem(R.id.english_lang);
        getData();

        floating_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    // On pressing the back button, when navigation drawer is open
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void getData() {
//        Query query = FirebaseDatabase.getInstance().getReference().child("students");
        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.students));
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        LanguageManager lang = new LanguageManager(this);

        if (item.getItemId() == R.id.english_lang) {
            lang.updateResource("en");
            reload();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (item.getItemId() == R.id.arabic_lang) {
            lang.updateResource("ar");
            reload();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (item.getItemId() == R.id.german_lang) {
            lang.updateResource("de");
            reload();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (item.getItemId() == R.id.spanish_lang) {
            lang.updateResource("es");
            reload();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (item.getItemId() == R.id.french_lang) {
            lang.updateResource("fr");
            reload();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (item.getItemId() == R.id.hindi_lang) {
            lang.updateResource("hi");
            reload();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if (item.getItemId() == R.id.chinese_lang) {
            lang.updateResource("zh");
            reload();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    public void reload() {
        Intent intent = new Intent(this, MainActivity.class);
        // set the new task and clear flags
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}