package com.example.projet_bicycle_revisions.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.projet_bicycle_revisions.R;
import com.example.projet_bicycle_revisions.ui.bike.BikeActivity;
import com.example.projet_bicycle_revisions.ui.mechanic.MechanicActivity;
import com.example.projet_bicycle_revisions.ui.mgmt.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemReselectedListener {

    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";
    protected BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = new BottomNavigationView(this);
        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnItemReselectedListener(this);
        navigationView.setSelectedItemId(R.id.home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.person:
                startActivity( new Intent(this, MechanicActivity.class));
                break;
            case R.id.add:
                startActivity( new Intent(this, BikeActivity.class));
                break;
            case R.id.home:
                //do nothing
            default:
                break;
        }
    }
}