package com.e.costingapp;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper1 extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Items_fragment.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ITEMS = "tb_items";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "item_name";
    private static final String COLUMN_PRICE = "item_price";
    private static final String COLUMN_QTT = "item_qtty";
    private static final String COLUMN_DESCP = "item_descp";
    private static final String COLUMN_DATE = "date_column";
    private static final String COLUMN_SUPPL = "item_suppl";
    private static final String COLUMN_FOREIGN = "foreign_key";

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_U_ID = "_id_user";
    private static final String COLUMN_U_NAME = "username";
    private static final String COLUMN_U_PASS = "pwd";
    private static final String COLUMN_U_DEPTMT = "deptmt";



    private static final String TABLE_PROJECTS = "tb_project";
    private static final String COLUMN_P_ID = "_id_pj";
    private static final String COLUMN_P_NAME = "pj_name";
    private static final String COLUMN_P_START = "pj_st_date";
    private static final String COLUMN_P_END = "pj_end_date";
    private static final String COLUMN_P_LOCATION = "pj_location";
    private static final String COLUMN_P_COST = "pj_cost";


    MyDatabaseHelper1(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ITEMS +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_QTT + " INTEGER, " +
                COLUMN_DESCP + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_SUPPL + " TEXT, " +
                COLUMN_FOREIGN + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_FOREIGN + ") REFERENCES " + TABLE_PROJECTS + "(" + COLUMN_P_ID + "));";
        db.execSQL(query);

//        create table users
        String query1 = "CREATE TABLE " + TABLE_USERS +
                " (" + COLUMN_U_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_U_NAME + " TEXT, " +
                COLUMN_U_PASS+ " TEXT, " +
                COLUMN_U_DEPTMT + " TEXT);";
        db.execSQL(query1);

//        create table project
        String query3 = "CREATE TABLE " + TABLE_PROJECTS +
                " (" + COLUMN_P_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_P_NAME + " TEXT, " +
                COLUMN_P_START + " TEXT, " +
                COLUMN_P_END + " TEXT, " +
                COLUMN_P_LOCATION + " TEXT, " +
                COLUMN_P_COST + " INTEGER);";
        db.execSQL(query3);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        onCreate(db);
    }

    void addItem(String name, int price, int quantity, String desc, String my_date, String supplier, int f_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_QTT, quantity);
        cv.put(COLUMN_DESCP, desc);
        cv.put(COLUMN_DATE, my_date);
        cv.put(COLUMN_SUPPL, supplier);
        cv.put(COLUMN_FOREIGN, f_id);

        long result = db.insert(TABLE_ITEMS,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed To Add", Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();

        }

    }
//    method to add data to table projects
    void addProject(String project_name, String start_date, String end_date, String location, int cost){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_P_NAME, project_name);
        cv.put(COLUMN_P_START, start_date);
        cv.put(COLUMN_P_END, end_date);
        cv.put(COLUMN_P_LOCATION, location);
        cv.put(COLUMN_P_COST, cost);

        long result = db.insert(TABLE_PROJECTS, null, cv);
        if(result==-1){
            Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Project Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    //    Read project's data
    Cursor readPJData(){
        String query = "SELECT * FROM " + TABLE_PROJECTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db != null){
            cursor =db.rawQuery(query, null);
        }
        return cursor;
    }

//    users table methods
    public  Boolean insertData(String username, String password, String department){
        SQLiteDatabase Mydb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_U_NAME, username);
        cv.put(COLUMN_U_PASS, password);
        cv.put(COLUMN_U_DEPTMT, department);

        long result2 = Mydb.insert(TABLE_USERS,null, cv);
        if(result2 == -1)
            return false;
        else
            return true;
    }

    public Boolean checkUser(String username){
        SQLiteDatabase Mydb = this.getWritableDatabase();
        Cursor hold_data = Mydb.rawQuery("select * from users where username = ?", new String[] {username});
        if(hold_data.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkUserPwd(String username, String pwd){
        SQLiteDatabase Mydb = this.getWritableDatabase();
        Cursor hold_data =Mydb.rawQuery("select * from users where username = ? and pwd = ?", new String[] {username, pwd});
        if(hold_data.getCount() > 0 )
            return true;
        else
            return false;
    }
//  Read all data from table items
    Cursor readAllData(String get_id){

        int s = Integer.parseInt(get_id);
        String query = "SELECT * FROM " + TABLE_ITEMS+" WHERE foreign_key = " + s;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db!=null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }



    void updateData(String row_id, String name_U, String cost_U, String quantity_U, String description_U,
                    String date_U, String supplier_U){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(COLUMN_NAME, name_U);
        cv.put(COLUMN_PRICE, cost_U);
        cv.put(COLUMN_QTT, quantity_U);
        cv.put(COLUMN_DESCP, description_U);
        cv.put(COLUMN_DATE, date_U);
        cv.put(COLUMN_SUPPL, supplier_U);

        long result = db.update(TABLE_ITEMS, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "failed to update", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteOneRow(String row_id){
        SQLiteDatabase db =this.getWritableDatabase();
        long result = db.delete(TABLE_ITEMS, "_id=?", new String[]{row_id});
        if( result == -1){
            Toast.makeText(context, "Error: Failed to Delete!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
        }
    }
    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ITEMS);
    }

}

