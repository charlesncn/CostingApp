package com.e.costingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    TextView disclaimer;
    LinearLayout back_icon;
    TextInputEditText project_id, item_name, cost, quantity, description, supplier, total;
    Button delete, add;
    TextView date_today, last_update;
    Spinner pj_status;
    DigitalClock time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        back_icon = findViewById(R.id.go_back);



        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity =new MainActivity();
                mainActivity.finish();
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        project_id = findViewById(R.id.ed_id);
        item_name = findViewById(R.id.ed_name);
        cost = findViewById(R.id.ed_start_date);
        quantity = findViewById(R.id.ed_end_date);
        description = findViewById(R.id.ed_location);
        supplier = findViewById(R.id.ed_esimate_cost);
        total =findViewById(R.id.ed_duration);

        date_today =findViewById(R.id.ai_date);
        last_update =findViewById(R.id.ai_update_date);
        time =findViewById(R.id.ai_time);

        pj_status =findViewById(R.id.ai_spinner);

        disclaimer = findViewById(R.id.disclaimer_text);

        delete = findViewById(R.id.delete_pj);
        add = findViewById(R.id.update_pj);

//        call method to find total
        getTotal();

        //        show text when total is clicked
        disclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddActivity.this, "total will be updated automatically", Toast.LENGTH_SHORT).show();
            }
        });

//        getting time and date
        String today_date = new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date());
        String time_now = new SimpleDateFormat("hh:mm:ss z", Locale.getDefault()).format(new Date());
//        set date
        date_today.setText(today_date);
        time.setText(time_now);

//      add button onclick listener

//        get id


        int passed_id_int;
        String passed_id;

        Intent intent = new Intent();
        passed_id = intent.getStringExtra("my_id");
//        passed_id_int = Integer.parseInt(passed_id);
        project_id.setText(passed_id);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDatabaseHelper1 myDB = new MyDatabaseHelper1(AddActivity.this);

                if(item_name.toString().trim().equals("") && cost.toString().trim().equals("") && quantity.toString().trim().equals("")){
                    Toast.makeText(AddActivity.this, "Item name, Cost and Quantity cannot be empty!", Toast.LENGTH_SHORT).show();
                }
                else {

                    myDB.addItem(item_name.getText().toString().trim(),
                            Integer.parseInt(cost.getText().toString().trim()),
                            Integer.parseInt(quantity.getText().toString().trim()),
                            description.getText().toString().trim(),
                            date_today.getText().toString().trim(),
                            supplier.getText().toString().trim(),
                            Integer.parseInt(project_id.getText().toString().trim()));
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.cancel){
            confirmDialog();
        }


        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Discard Data!");
        builder.setMessage("Are you sure you want to discard this data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                item_name.getText().clear();
                cost.getText().clear();
                description.getText().clear();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    void getTotal(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!cost.getText().toString().trim().equals("") && !quantity.getText().toString().trim().equals("")){
                    int temp_cost = Integer.parseInt(cost.getText().toString().trim());
                    int temp_quantity = Integer.parseInt(quantity.getText().toString().trim());
                    total.setText(String.valueOf(temp_cost * temp_quantity));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        cost.addTextChangedListener(textWatcher);
        quantity.addTextChangedListener(textWatcher);

    }
}