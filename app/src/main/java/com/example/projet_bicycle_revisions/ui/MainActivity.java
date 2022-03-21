package com.example.projet_bicycle_revisions.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.projet_bicycle_revisions.R;
import com.example.projet_bicycle_revisions.ui.mechanic.MechanicActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

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
                break;
            case R.id.home:
               break;
            default:
                break;
        }

    }


    //@Override
  //public boolean onNavigationItemSelected(@NonNull MenuItem item) {
  //    switch (item.getItemId()) {
  //               case R.id.person:
  //                   Intent intent = new Intent(MainActivity.this, MechanicActivity.class);
  //                   startActivity(intent);
  //                   return true;
  //               case R.id.add:
  //                   return true;
  //               case R.id.home:
  //                   return true;
  //               default:
  //               return super.onOptionsItemSelected(item);
  //           }
  //}
//
    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
    //    switch (item.getItemId()) {
    //        case R.id.person:
    //            Intent intent = new Intent(MainActivity.this, MechanicActivity.class);
    //            startActivity(intent);
    //            return true;
    //        case R.id.add:
    //            return true;
    //        case R.id.home:
    //            return true;
    //        default:
    //        return super.onOptionsItemSelected(item);
    //    }
    //}




}