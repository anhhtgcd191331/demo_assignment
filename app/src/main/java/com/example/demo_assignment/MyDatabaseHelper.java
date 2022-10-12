package com.example.demo_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "coursework.db";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_TRIPS = "trips";
    private static final String TABLE_EXPENSES = "expenses";
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String account_table = "CREATE TABLE "+TABLE_USERS +"(username TEXT primary key, email TEXT, password TEXT);";
        String trips_table = "CREATE TABLE "+TABLE_TRIPS+ "(trip_id INTEGER primary key autoincrement, name TEXT, destination TEXT, date TEXT, require TEXT, des TEXT, username TEXT, foreign key(username) references users(username));";
        String expenses_table = "CREATE TABLE "+ TABLE_EXPENSES+ "(expenses_id INTEGER primary key autoincrement, trip_id INTEGER, type TEXT, amount DOUBLE, time TEXT, foreign key(trip_id) references trips(trip_id) );";
        db.execSQL(account_table);
        db.execSQL(trips_table);
        db.execSQL(expenses_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TRIPS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        onCreate(db);
    }

    public void addUser(String username,String email,String password){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);
        long result = db.insert(TABLE_USERS,null,cv);
        if(result == -1){
            Toast.makeText(context, "Failed to Add", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully to Add", Toast.LENGTH_SHORT).show();
        }
    }

    public void addTrip(String name, String destination,String date, String require, String des, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("destination", destination);
        cv.put("date", date);
        cv.put("require", require);
        cv.put("des", des);
        cv.put("username", username);
        long result = db.insert(TABLE_TRIPS,null,cv);
        if(result == -1){
            Toast.makeText(context, "Failed to Add", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully to Add", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean checkUsername(String username){
        String query = "select * from users where username=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, new String[] {username});
        if (cursor.getCount() == 0){
            return true;
        }else {
            return false;
        }
    }
    public boolean checkLogin(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_USERS+" WHERE username =? AND password =?",new String[]{username,password});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
    public Cursor readAllTrip(String username){
        String query ="SELECT trip_id,name,destination, date, require, des FROM "+ TABLE_TRIPS +" where username = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, new String[]{username});
        }
        return cursor;
    }
    public void updateData(String row_id, String name,String destinaton, String date, String require, String des,String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("destination", destinaton);
        cv.put("date", date);
        cv.put("require", require);
        cv.put("des", des);
        long result = db.update(TABLE_TRIPS, cv, "username=? and trip_id=?",new String[]{username,row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully to Update", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_TRIPS, "trip_id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully to Delete", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_TRIPS);
    }

    public Cursor readAllExpenses(int tripID){
        String query ="SELECT expenses_id, type, amount, time FROM "+ TABLE_EXPENSES +" where trip_id = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, new String[]{String.valueOf(tripID)});
        }
        return cursor;
    }

    public void addExpenses(String type, double amount,String time, int tripID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("trip_id", tripID);
        cv.put("type", type);
        cv.put("amount", amount);
        cv.put("time", time);
        long result = db.insert(TABLE_EXPENSES,null,cv);
        if(result == -1){
            Toast.makeText(context, "Failed to Add", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully to Add", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateData(String expenses_id, String type, String amount, String time, int tripID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("type", type);
        cv.put("amount", amount);
        cv.put("time", time);
        long result = db.update(TABLE_EXPENSES, cv, "expenses_id=? and trip_id=?",new String[]{expenses_id, String.valueOf(tripID)});
        if(result == -1){
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully to Update", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor findAllTrip(String username, String name){
        String query ="SELECT trip_id,destination, date, require, des FROM "+ TABLE_TRIPS +" where username = ? and name=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, new String[]{username,name});
        }
        return cursor;
    }
}
