package com.e.costingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Project_Home extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper1 myDB;
    ArrayList<String> pj_id, pj_name, pj_start, pj_end, pj_location, pj_cost;

    New_CustomAdapter new_customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project__home);

        myDB = new MyDatabaseHelper1(Project_Home.this);
        pj_id = new ArrayList<>();
        pj_name = new ArrayList<>();
        pj_start = new ArrayList<>();
        pj_end = new ArrayList<>();
        pj_location = new ArrayList<>();
        pj_cost = new ArrayList<>();

        storeProjectDataInArray();

        add_button= findViewById(R.id.add_btn);
        recyclerView= findViewById(R.id.rv_projects);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("projects"));
        tabLayout.addTab(tabLayout.newTab().setText("analysis"));


        new_customAdapter = new New_CustomAdapter( this, pj_id, pj_name, pj_start, pj_end, pj_location, pj_cost);
        recyclerView.setAdapter(new_customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Project_Home.this));

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Project_Home.this, Create_Project_Activity.class);
                startActivity(intent);
            }
        });

    }
    void storeProjectDataInArray(){
        Cursor cursor = myDB.readPJData();
        if(cursor!=null && cursor.getCount() > 0){

            if(cursor.getCount() == 0){
                Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            }
            else{
                while (cursor.moveToNext()){
                    pj_id.add(cursor.getString(0));
                    pj_name.add(cursor.getString(1));
                    pj_start.add(cursor.getString(2));
                    pj_end.add(cursor.getString(3));
                    pj_location.add(cursor.getString(4));
                    pj_cost.add(cursor.getString(5));
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        else if(item.getItemId() == R.id.refresh_activity){
            recreate();
        }
        else if(item.getItemId() == R.id.logout){

            Intent intent =new Intent(Project_Home.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper1 myDB = new MyDatabaseHelper1(Project_Home.this);
                myDB.deleteAllData();
                //Refresh Activity
                Intent intent = new Intent(Project_Home.this, MainActivity.class);
                startActivity(intent);
                finish();
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
















