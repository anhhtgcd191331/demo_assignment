package com.example.demo_assignment.trips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.demo_assignment.MyDatabaseHelper;
import com.example.demo_assignment.R;

import java.util.Calendar;

public class AddTrips extends AppCompatActivity {
    private EditText name_trip, destination_trip, description_trip;
    private RadioGroup radioGroup;
    private RadioButton radio_yes;
    private RadioButton radio_no;
    private DatePickerDialog datePickerDialog;
    private Button dateButton, add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trips);

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String dateStr = dateToString(day,month,year);
                dateButton.setText(dateStr);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);



        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getDate());

        Intent intent = getIntent();
        String account = (String) intent.getSerializableExtra("username");
        //add trips
        name_trip = findViewById(R.id.input_name);
        destination_trip = findViewById(R.id.input_destination);
        radioGroup = findViewById(R.id.radioGroup);
        radio_yes = findViewById(R.id.radio_yes);
        radio_no = findViewById(R.id.radio_no);
        description_trip = findViewById(R.id.input_des);
        add_button = findViewById(R.id.add_button);
        Intent intentPut = new Intent(AddTrips.this, TripActivity.class);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int requireGroup = radioGroup.getCheckedRadioButtonId();
                RadioButton radioGroup = findViewById(requireGroup);
                String name = name_trip.getText().toString().trim();
                String destination = destination_trip.getText().toString().trim();
                String des = description_trip.getText().toString().trim();
                String strRequire = radioGroup.getText().toString().trim();
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddTrips.this);
                String date = dateButton.getText().toString().trim();
                myDB.addTrip(name,destination,date,strRequire,des,account);
                intentPut.putExtra("account",account);
                startActivity(intentPut);
            }
        });
    }
    private String dateToString(int day, int month, int year){
        if(day<10){
            return month + "/0" + day + "/"+ year;
        }else if(month <10){
            return "0" + month +"/"+ day + "/"+ year;
        }else if(day<10 && month <10){
            return "0" + month +"/0" + day + "/"+ year;
        }
        return month + "/" + day + "/"+ year;
    }
//    Date picker
    private String getDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return dateToString(day,month,year);
    }
    public void openDatePiker(View view) {
        datePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}