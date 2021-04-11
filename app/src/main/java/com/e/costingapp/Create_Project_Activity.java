package com.e.costingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DigitalClock;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Create_Project_Activity extends AppCompatActivity {

    TextView disclaimer;
    LinearLayout back_icon;
    TextInputEditText pj_name, s_date, e_date, pj_location, pj_cost;
    Button delete, add, view_items;
    TextView date_today;
    DigitalClock time;

    private static final String TAG ="AddActivity";
    private static final String TAG1 ="AddActivity1";
    private DatePickerDialog.OnDateSetListener mDateSetListener, mDateSetListener1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__project_);

//        finding views of items.
        disclaimer = findViewById(R.id.editing_panel);

        back_icon = findViewById(R.id.go_back);

        pj_name =findViewById(R.id.ed_name);
        s_date = findViewById(R.id.ed_start_date);
        e_date =findViewById(R.id.ed_end_date);
        pj_location = findViewById(R.id.ed_location);
        pj_cost = findViewById(R.id.ed_esimate_cost);

        add =findViewById(R.id.update_pj);
        view_items =findViewById(R.id.view_items);

        date_today = findViewById(R.id.ai_date);
        time = findViewById(R.id.ai_time);



        s_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Create_Project_Activity.this,
                        android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yy: " + dayOfMonth + "/" + month + "/" + year);

                String date = dayOfMonth + "/" + month + "/" + year;
                s_date.setText(date);
            }
        };


        e_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Create_Project_Activity.this,
                        android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,
                        mDateSetListener1,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG1, "onDateSet: dd/mm/yy: " + dayOfMonth + "/" + month + "/" + year);

                String date = dayOfMonth + "/" + month + "/" + year;
                e_date.setText(date);
            }
        };


        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = new MainActivity();
                mainActivity.finish();
                Intent intent = new Intent(Create_Project_Activity.this, Project_Home.class);
                startActivity(intent);
                finish();
            }
        });

//        getting time and date
        String today_date = new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date());
        String time_now = new SimpleDateFormat("hh:mm:ss z", Locale.getDefault()).format(new Date());
//        set date
        date_today.setText(today_date);
        time.setText(time_now);



//        add field's content to the database
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper1 myDB = new MyDatabaseHelper1(Create_Project_Activity.this);
                myDB.addProject(pj_name.getText().toString().trim(),
                        s_date.getText().toString().trim(),
                        e_date.getText().toString().trim(),
                        pj_location.getText().toString().trim(),
                        Integer.parseInt(pj_cost.getText().toString().trim()));

            }
        });





    }



    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Discard Data!");
        builder.setMessage("Are you sure you want to discard this data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pj_name.getText().clear();
                pj_cost.getText().clear();
                s_date.getText().clear();
                e_date.getText().clear();
                pj_location.getText().clear();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }




}