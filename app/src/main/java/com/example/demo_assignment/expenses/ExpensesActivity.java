package com.example.demo_assignment.expenses;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo_assignment.MyDatabaseHelper;
import com.example.demo_assignment.R;
import com.example.demo_assignment.trips.UpdateTrip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExpensesActivity extends AppCompatActivity {
    private FloatingActionButton add_button;
    private RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> expenses_id, expenses_type,expenses_amount, expenses_time;
    ExpensesAdapter expensesAdapter;

    private ImageView back_arrow, menu_group;
    private TextView title_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expences);

        Intent intent = getIntent();
        int tripID = Integer.parseInt(intent.getStringExtra("tripID"));
        String username = intent.getStringExtra("username");
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPut = new Intent(ExpensesActivity.this, AddExpenses.class);
                intentPut.putExtra("trip_id", String.valueOf(tripID));
                startActivity(intentPut);
            }
        });
        myDB = new MyDatabaseHelper(ExpensesActivity.this);
        expenses_id = new ArrayList<>();
        expenses_type = new ArrayList<>();
        expenses_amount = new ArrayList<>();
        expenses_time = new ArrayList<>();
        displayExpenses(tripID);

        expensesAdapter = new ExpensesAdapter(ExpensesActivity.this,this,expenses_id, expenses_type,expenses_amount,expenses_time,tripID);
        recyclerView.setAdapter(expensesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExpensesActivity.this));

        back_arrow = findViewById(R.id.back_arrow);
        menu_group = findViewById(R.id.menu_group);
        title_toolbar = findViewById(R.id.toolbar_title);
        title_toolbar.setText("Expenses of Trip");
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpensesActivity.this, UpdateTrip.class);
                intent.putExtra("trip_id", String.valueOf(tripID));
                intent.putExtra("username", String.valueOf(username));
                startActivity(intent);
            }
        });

    }
    public void displayExpenses(int tripID){
        Cursor cursor = myDB.readAllExpenses(tripID);
        if(cursor.getCount() == 0){
            Toast.makeText(ExpensesActivity.this, "No data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                expenses_id.add(cursor.getString(0));
                expenses_type.add(cursor.getString(1));
                expenses_amount.add(cursor.getString(2));
                expenses_time.add(cursor.getString(3));
            }
        }
    }
}