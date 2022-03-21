package com.example.projet_bicycle_revisions.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.projet_bicycle_revisions.R;
import com.example.projet_bicycle_revisions.ui.mechanic.MechanicActivity;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
    //    super.onCreateOptionsMenu(menu);
    //    MenuInflater inflater = getMenuInflater();
    //    inflater.inflate(R.menu.bottom_navigation_menu,menu);
    //    return true;
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
    public void showProfile(View v){
        //startActivity(new Intent(this,MechanicActivity.class));
    }

}