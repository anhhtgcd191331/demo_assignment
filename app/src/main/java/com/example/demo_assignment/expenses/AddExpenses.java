package com.example.demo_assignment.expenses;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo_assignment.MyDatabaseHelper;
import com.example.demo_assignment.R;

import java.util.Calendar;

public class AddExpenses extends AppCompatActivity {
    private EditText expenses_type, expenses_amount, expenses_time;
    private DatePickerDialog datePickerDialog;
    private Button dateButton, add_button;
    private Spinner spinner_type;
    private ImageView back_arrow, menu_group;
    private TextView title_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

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


        spinner_type = findViewById(R.id.spinner_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_expenses, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter);


        Intent intent = getIntent();
        int trip_id = Integer.parseInt(intent.getStringExtra("trip_id"));
        expenses_amount = findViewById(R.id.amount_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPut = new Intent(AddExpenses.this, ExpensesActivity.class);
                String type = spinner_type.getSelectedItem().toString().trim();
                double amount = Double.parseDouble(expenses_amount.getText().toString().trim());
                String time = dateButton.getText().toString().trim();
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddExpenses.this);
                myDB.addExpenses(type,amount,time, trip_id);
                intentPut.putExtra("tripID", String.valueOf(trip_id));
                startActivity(intentPut);
            }
        });

        back_arrow = findViewById(R.id.back_arrow);
        menu_group = findViewById(R.id.menu_group);
        title_toolbar = findViewById(R.id.toolbar_title);
        title_toolbar.setText("Add new Expenses");
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddExpenses.this, ExpensesActivity.class);
                intent.putExtra("tripID", String.valueOf(trip_id));
                startActivity(intent);
            }
        });
    }

    private String dateToString(int day, int month, int year){
        if(day<10){
            return month + "/0" + day + "/"+ year;
        }else if(month <10){
            return "0" + month+ "/" + day + "/"+ year;
        }else if(day<10 && month <10){
            return "0" + month +"/0" + day + "/"+ year;
        }
        return month + "/" + day + "/"+ year;
    }
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

}