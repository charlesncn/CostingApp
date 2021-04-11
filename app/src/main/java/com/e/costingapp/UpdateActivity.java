package com.e.costingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;

public class UpdateActivity extends AppCompatActivity {

    TextView disclaimer;
    TextInputEditText item_name2, item_cost2, item_supplier2, item_qtt2, item_description2, total;
    Button btn_update2, btn_delete2;
    String id,name, cost, quantity, description, my_date, supplier;
    LinearLayout l_l_back;
    TextView date_today, lastUpdate;
    DigitalClock time;
    Spinner get_pj_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        item_name2 = findViewById(R.id.ed_name2);
        item_cost2 = findViewById(R.id.ed_cost2);
        item_description2 = findViewById(R.id.ed_description2);
        item_supplier2 = findViewById(R.id.ed_supplier2);
        item_qtt2 = findViewById(R.id.ed_quantity2);

        total = findViewById(R.id.ed_duration);

        disclaimer = findViewById(R.id.disclaimer_text);

        date_today = findViewById(R.id.edp_date);
        lastUpdate = findViewById(R.id.edp_update_date);
        time = findViewById(R.id.edp_time);

        get_pj_status = findViewById(R.id.edp_spinner);

        btn_update2 = findViewById(R.id.update_btn2);
        btn_delete2 = findViewById(R.id.delete_btn2);
        l_l_back = findViewById(R.id.go_back);

//        call method to get total
        getTotal();

//      call method to put data to this activity
        getAndSetIntentData();

//      getting time and date
        String today_date = new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date());
        String time_now = new SimpleDateFormat("hh:mm:ss z", Locale.getDefault()).format(new Date());
//        set date
        date_today.setText(today_date);
        time.setText(time_now);

//        show text when total is clicked
        disclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateActivity.this, "total will be updated automatically", Toast.LENGTH_SHORT).show();
            }
        });

//        go back
        l_l_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                MainActivity mainActivity = new MainActivity();
                mainActivity.finish();
            }
        });
        btn_update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper1 myDb = new MyDatabaseHelper1(UpdateActivity.this);
                name = item_name2.getText().toString().trim();
                cost = item_cost2.getText().toString().trim();
                quantity = item_qtt2.getText().toString().trim();
                cost = item_cost2.getText().toString().trim();
                description = item_description2.getText().toString().trim();
                my_date = date_today.getText().toString().trim();
                supplier = item_supplier2.getText().toString().trim();
                myDb.updateData(id, name, cost, quantity, description, my_date, supplier);
            }
        });

        btn_delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("cost")
                && getIntent().hasExtra("quantity") && getIntent().hasExtra("description")
                && getIntent().hasExtra("my_date") && getIntent().hasExtra("supplier")){

//          getting data from intent
            id= getIntent().getStringExtra("id");
            name= getIntent().getStringExtra("name");
            cost = getIntent().getStringExtra("cost");
            quantity = getIntent().getStringExtra("quantity");
            description= getIntent().getStringExtra("description");
            my_date= getIntent().getStringExtra("my_date");
            supplier= getIntent().getStringExtra("supplier");

            //setting data to the intent
            item_name2.setText(name);
            item_cost2.setText((cost));
            item_qtt2.setText(quantity);
            item_description2.setText(description);
            lastUpdate.setText("Last update " + my_date);
            item_supplier2.setText(supplier);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+name+"?");
        builder.setMessage("Are you sure you want to delete '"+name+"' permanently?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper1 myDb =new MyDatabaseHelper1(UpdateActivity.this);
                myDb.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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

                if(!item_cost2.getText().toString().trim().equals("") && !item_qtt2.getText().toString().trim().equals("")){
                    int temp_cost = Integer.parseInt(item_cost2.getText().toString().trim());
                    int temp_quantity = Integer.parseInt(item_qtt2.getText().toString().trim());
                    total.setText(String.valueOf(temp_cost * temp_quantity));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        item_cost2.addTextChangedListener(textWatcher);
        item_qtt2.addTextChangedListener(textWatcher);

    }
}