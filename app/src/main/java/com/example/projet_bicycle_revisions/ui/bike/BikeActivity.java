package com.example.projet_bicycle_revisions.ui.bike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.projet_bicycle_revisions.R;
import com.example.projet_bicycle_revisions.ui.MainActivity;
import com.example.projet_bicycle_revisions.ui.mechanic.MechanicActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BikeActivity extends AppCompatActivity implements NavigationBarView.OnItemReselectedListener{

    protected BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);

        navigationView = new BottomNavigationView(this);
        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnItemReselectedListener(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        setTitle("");
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
                startActivity( new Intent(this, MainActivity.class));
                break;
            default:
                break;
        }

    }
}