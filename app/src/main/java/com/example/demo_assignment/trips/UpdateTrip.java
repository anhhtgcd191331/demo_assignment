package com.example.demo_assignment.trips;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo_assignment.MyDatabaseHelper;
import com.example.demo_assignment.R;
import com.example.demo_assignment.expenses.ExpensesActivity;

import java.text.ParseException;
import java.util.Calendar;

public class UpdateTrip extends AppCompatActivity {
    private EditText name_trip, destination_trip, description_trip;
    private RadioGroup radioGroup;
    private RadioButton radio_yes;
    private RadioButton radio_no;
    private DatePickerDialog datePickerDialog;
    private Button dateButton, update_button, see_expenses;
    private TextView title_toolbar;
    private ImageView back_arrow, menu_group;

    private String trip_id,name,destination,require, date, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip);

        initDatePiker();
        dateButton = findViewById(R.id.datePickerButton_trip);

        Intent intent = getIntent();
        String account = (String) intent.getSerializableExtra("username");
        int tripID = Integer.parseInt(intent.getStringExtra("trip_id"));

        back_arrow = findViewById(R.id.back_arrow);
        menu_group = findViewById(R.id.menu_group);
        title_toolbar = findViewById(R.id.toolbar_title);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateTrip.this, TripActivity.class);
                intent.putExtra("username", account);
                startActivity(intent);
            }
        });

        name_trip = findViewById(R.id.input_name_trip);
        destination_trip = findViewById(R.id.input_destination_trip);
        radioGroup = findViewById(R.id.radioGroup_trip);
        radio_yes = findViewById(R.id.radio_yes_trip);
        radio_no = findViewById(R.id.radio_no_trip);
        description_trip = findViewById(R.id.input_des_trip);
        update_button = findViewById(R.id.update_button);
        try {
            getAndSetIntentData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        see_expenses = findViewById(R.id.see_expenses);
        see_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentE = new Intent(UpdateTrip.this, ExpensesActivity.class);
                intentE.putExtra("tripID", String.valueOf(tripID));
                intentE.putExtra("username", String.valueOf(account));
                startActivity(intentE);
            }
        });
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateTrip.this);
                Intent intent = new Intent(UpdateTrip.this, TripActivity.class);
                intent.putExtra("name", account);
                int requireGroup = radioGroup.getCheckedRadioButtonId();
                RadioButton radioGroup = findViewById(requireGroup);
                name = name_trip.getText().toString().trim();
                date = dateButton.getText().toString().trim();
                destination = destination_trip.getText().toString().trim();
                description = description_trip.getText().toString().trim();
                String strRequire = radioGroup.getText().toString().trim();
                myDB.updateData(trip_id,name,destination,date,strRequire, description, account);
                startActivity(intent);
            }
        });

        ImageView delete_button = findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    public void getAndSetIntentData() throws ParseException {
        if(getIntent().hasExtra("trip_id")&&getIntent().hasExtra("destination")&&getIntent().hasExtra("date")&&getIntent().hasExtra("require")&&getIntent().hasExtra("des")){
            int requireGroup = radioGroup.getCheckedRadioButtonId();
            RadioButton radioGroup = findViewById(requireGroup);
            trip_id = getIntent().getStringExtra("trip_id");
            name = getIntent().getStringExtra("name");
            destination = getIntent().getStringExtra("destination");
            date = getIntent().getStringExtra("date");
            require = getIntent().getStringExtra("require");
            description = getIntent().getStringExtra("des");

            name_trip.setText(name);
            destination_trip.setText(destination);
            dateButton.setText(date);
//            requireGroup.
//            radioGroup.setText(require);
            description_trip.setText(description);
            if (require.equals("Yes"))
            {
                radio_yes.setChecked(true);
                radio_no.setChecked(false);
            }
            else {

                radio_yes.setChecked(false);
                radio_no.setChecked(true);
            }
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void initDatePiker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = dateToString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);

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

    void confirmDialog(){
        Intent intent = getIntent();
        String account = (String) intent.getSerializableExtra("username");
        androidx.appcompat.app.AlertDialog.Builder builder =new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Delete "+name_trip+" ?");
        builder.setMessage("Are you sure you want to delete item "+name_trip+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateTrip.this);
                myDB.deleteOneRow(trip_id);
                Intent intent = new Intent(UpdateTrip.this, TripActivity.class);
                intent.putExtra("username", account);
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