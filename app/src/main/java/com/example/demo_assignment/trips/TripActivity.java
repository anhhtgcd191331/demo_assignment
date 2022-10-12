package com.example.demo_assignment.trips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.demo_assignment.MainActivity;
import com.example.demo_assignment.MyDatabaseHelper;
import com.example.demo_assignment.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TripActivity extends AppCompatActivity {
    private FloatingActionButton add_button;
    private RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    List<Trip> tripList = new ArrayList<>();
    TripsAdapter tripsAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        String account = getUsername();
        Intent intentPut = new Intent(TripActivity.this, UpdateTrip.class);
        intentPut.putExtra("username",account);
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripActivity.this, AddTrips.class);
                intent.putExtra("username", account);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(TripActivity.this);
        displayTrip(account);

        tripsAdapter = new TripsAdapter(TripActivity.this,this,tripList);
        recyclerView.setAdapter(tripsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TripActivity.this));

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.trip_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.trip_home:
                        return true;
                    case R.id.upload_trip:
                        Intent intent = new Intent(getApplicationContext(),UploadTripActivity.class);
                        intent.putExtra("username", account);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
         }
    public String getUsername(){
        Intent intent = getIntent();
        String account = (String) intent.getSerializableExtra("username");
        String username = (String) intent.getSerializableExtra("account");
        String name = (String) intent.getSerializableExtra("name");
        if(account == null && name == null){
            return username;
        }else if(account ==null&& username== null){
            return name;
        }else if(username == null && name == null){
            return account;
        }
        return null;
    }
    public void displayTrip(String username){
        Cursor cursor = myDB.readAllTrip(username);
        if(cursor.getCount() == 0){
            Toast.makeText(TripActivity.this, "No data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                tripList.add(new Trip(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),username));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_action).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tripsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tripsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }


}