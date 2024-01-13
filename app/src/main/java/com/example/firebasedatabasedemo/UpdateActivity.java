package com.example.firebasedatabasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    ImageView back_arrow_button;
    TextInputEditText update_name, update_email, update_course;
    Button update_button, delete_button;
    String id, name, email, course;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String TAG = "UpdateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        back_arrow_button = (ImageView) findViewById(R.id.back_arrow_button);
        update_name = (TextInputEditText) findViewById(R.id.update_name);
        update_email = (TextInputEditText) findViewById(R.id.update_email);
        update_course = (TextInputEditText) findViewById(R.id.update_course);
        update_button = (Button) findViewById(R.id.update_button);
        delete_button = (Button) findViewById(R.id.delete_button);

        getAndSetIntentData();

        back_arrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(update_name.getText().toString())) {
                    update_name.setError("Please enter name");
                } else if (TextUtils.isEmpty(update_email.getText().toString())) {
                    update_email.setError("Please enter email");
                } else if (!update_email.getText().toString().trim().matches(emailPattern)) {
                    update_email.setError("Please enter valid email");
                }else if (TextUtils.isEmpty(update_course.getText().toString())) {
                    update_course.setError("Please enter course");
                } else {
                    String uName = update_name.getText().toString().trim();
                    String uEmail = update_email.getText().toString().trim();
                    String uCourse = update_course.getText().toString().trim();
                    updateStudentData(id, uName, uEmail, uCourse);
                }
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudentData(id);
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("name") &&
                getIntent().hasExtra("email") && getIntent().hasExtra("course")) {
            //  Getting data from Intent
            id = getIntent().getStringExtra("id");
            Log.d(TAG, "getAndSetIntentData: id=====> " + id);
            name = getIntent().getStringExtra("name");
            email = getIntent().getStringExtra("email");
            course = getIntent().getStringExtra("course");

            //  Setting Intent Data
            update_name.setText(name);
            update_email.setText(email);
            update_course.setText(course);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void updateStudentData(String id, String name, String email, String course) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("students").child(id);
        Map<String, Object> map = new HashMap<>();
        map.put(String.valueOf(R.string.name_intent), name);
        map.put(String.valueOf(R.string.email_intent), email);
        map.put(String.valueOf(R.string.course_intent), course);

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UpdateActivity.this, "Updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(UpdateActivity.this, "Failed to update.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void  deleteStudentData(String id) {
        confirmDialog();
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.delete) + name + " ?");
        builder.setMessage(getString(R.string.are_you_sure_you_want_to_delete) + name + " ?");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("students").child(id);
                reference.removeValue();
                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        builder.create().show();
    }
}