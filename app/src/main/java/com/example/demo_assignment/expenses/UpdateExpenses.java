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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo_assignment.MyDatabaseHelper;
import com.example.demo_assignment.R;

import java.text.ParseException;
import java.util.Calendar;

public class UpdateExpenses extends AppCompatActivity {
    private EditText amount_expenses;
    private Spinner spinner_type;
    private DatePickerDialog datePickerDialog;
    private Button dateButton, update_button;
    private ImageView back_arrow, toolbar_delete;
    private TextView title_toolbar;

    private String expenses_id,type,amount,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expenses);
        Intent intent = getIntent();
        int tripID = Integer.parseInt(intent.getStringExtra("tripID"));
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

        amount_expenses = findViewById(R.id.amount_input_txt);
        spinner_type = findViewById(R.id.spinner_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_expenses, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter);
        try {
            getAndSetIntentData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        update_button = findViewById(R.id.update_button);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateExpenses.this);
                Intent intent = new Intent(UpdateExpenses.this, ExpensesActivity.class);
                intent.putExtra("tripID",String.valueOf(tripID));
                type = spinner_type.getSelectedItem().toString().trim();
                amount = amount_expenses.getText().toString().trim();
                time = dateButton.getText().toString().trim();
                myDB.updateData(expenses_id,type,amount,time, tripID);
                startActivity(intent);
            }
        });

        back_arrow = findViewById(R.id.back_arrow);
        toolbar_delete = findViewById(R.id.menu_group);
//        toolbar_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        title_toolbar = findViewById(R.id.toolbar_title);
        title_toolbar.setText("Update Expenses");
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateExpenses.this, ExpensesActivity.class);
                intent.putExtra("tripID",String.valueOf(tripID));
                startActivity(intent);
            }
        });


    }

    public void getAndSetIntentData() throws ParseException {
        if(getIntent().hasExtra("expenses_id")&&getIntent().hasExtra("type")&&getIntent().hasExtra("time")){
            expenses_id = getIntent().getStringExtra("expenses_id");
            type = getIntent().getStringExtra("type");
            amount = getIntent().getStringExtra("amount");
            time = getIntent().getStringExtra("time");

            amount_expenses.setText(amount);
            dateButton.setText(time);
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
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
    public void openDatePiker(View view) {
        datePickerDialog.show();
    }
}