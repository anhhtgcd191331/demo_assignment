package com.example.demo_assignment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.demo_assignment.trips.TripActivity;


public class MainActivity extends AppCompatActivity {
    private Button start_trip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String account = (String) intent.getSerializableExtra("username");
        TextView welcom = findViewById(R.id.welcome);
        welcom.setText(account);
        start_trip = findViewById(R.id.start_trip);
        start_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TripActivity.class);
                intent.putExtra("username", account);
                startActivity(intent);
            }
        });
    }

}