package com.example.firebasedatabasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AddActivity extends AppCompatActivity {

    ImageView back_arrow_button;
    TextInputEditText add_name, add_email, add_course;
    Button add_button;
    MainModel model;
    DatabaseReference dbRef;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        back_arrow_button = (ImageView) findViewById(R.id.back_arrow_button);
        add_name = (TextInputEditText) findViewById(R.id.add_name);
        add_email = (TextInputEditText) findViewById(R.id.add_email);
        add_course = (TextInputEditText) findViewById(R.id.add_course);
        add_button = (Button) findViewById(R.id.add_button);

        model = new MainModel();

        back_arrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(add_name.getText().toString())) {
                    add_name.setError(getString(R.string.please_enter_name));
                } else if (TextUtils.isEmpty(add_email.getText().toString())) {
                    add_email.setError(getString(R.string.please_enter_email));
                } else if (!add_email.getText().toString().trim().matches(emailPattern)) {
                    add_email.setError(getString(R.string.please_enter_valid_email));
                }else if (TextUtils.isEmpty(add_course.getText().toString())) {
                    add_course.setError(getString(R.string.please_enter_course));
                } else {
                    dbRef = FirebaseDatabase.getInstance().getReference().child("students");
                    String key = dbRef.push().getKey();
                    String id = key;
                    model.setName(add_name.getText().toString().trim());
                    model.setEmail(add_email.getText().toString().trim());
                    model.setCourse(add_course.getText().toString().trim());
                    model.setId(id);

                    //  Insert into students table
                    dbRef.child(key).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddActivity.this, R.string.data_inserted_successfully, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }
}