package com.example.projet_bicycle_revisions.ui.mechanic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_bicycle_revisions.R;
import com.example.projet_bicycle_revisions.ui.MainActivity;
import com.example.projet_bicycle_revisions.ui.bike.BikeActivity;
import com.example.projet_bicycle_revisions.ui.mgmt.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MechanicActivity extends AppCompatActivity implements NavigationBarView.OnItemReselectedListener{

    protected BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic);
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



    public void logout() {
        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_NAME, 0).edit();
        editor.remove(MainActivity.PREFS_USER);
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
