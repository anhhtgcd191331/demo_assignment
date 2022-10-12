package com.example.demo_assignment.trips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.demo_assignment.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class UploadTripActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_trip);
        String account = getIntent().getStringExtra("username");

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.upload_trip);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                            case R.id.trip_home:
                                Intent intent = new Intent(getApplicationContext(),TripActivity.class);
                                intent.putExtra("username", account);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.upload_trip:
                        return true;
                }
                return false;
            }
        });

    }
}