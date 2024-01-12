package com.example.firebasedatabasedemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Activity activity;
    private Context context;
    ArrayList<MainModel> studentList;

    CustomAdapter(Activity activity, Context context, ArrayList<MainModel> studentList) {
        this.activity = activity;
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        String id = studentList.get(position).id;
        String name = studentList.get(position).name;
        String email = studentList.get(position).email;
        String course = studentList.get(position).course;

        holder.name_txt.setText(name);
        holder.email_txt.setText(email);
        holder.course_txt.setText(course);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("course", course);
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_txt, email_txt, course_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            name_txt = itemView.findViewById(R.id.name_text);
            email_txt = itemView.findViewById(R.id.email_text);
            course_txt = itemView.findViewById(R.id.course_text);
        }
    }
}
