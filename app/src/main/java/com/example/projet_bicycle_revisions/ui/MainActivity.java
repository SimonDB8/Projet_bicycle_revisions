package com.example.projet_bicycle_revisions.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.example.projet_bicycle_revisions.R;
import com.example.projet_bicycle_revisions.adapter.RecyclerAdapter;
import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.example.projet_bicycle_revisions.ui.bike.BikeActivity;
import com.example.projet_bicycle_revisions.ui.bike.BikeArchivedActivity;
import com.example.projet_bicycle_revisions.ui.bike.BikeDetailActivity;
import com.example.projet_bicycle_revisions.ui.mechanic.MechanicActivity;
import com.example.projet_bicycle_revisions.ui.mgmt.SettingsActivity;
import com.example.projet_bicycle_revisions.util.RecyclerViewItemClickListener;
import com.example.projet_bicycle_revisions.viewmodel.ListBikeArchivedViewModel;
import com.example.projet_bicycle_revisions.viewmodel.ListBikeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";
    protected BottomNavigationView navigationView;
    private static final String TAG = "AccountsActivity";


    private List<BikesEntity> bikes;
    private RecyclerAdapter<BikesEntity> adapter;
    private ListBikeViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = new BottomNavigationView(this);
        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnItemSelectedListener(this);
        navigationView.setSelectedItemId(R.id.home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        setTitle("");

        RecyclerView recyclerView = findViewById(R.id.bikesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String email = settings.getString(PREFS_USER, null);

        bikes = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + bikes.get(position).getId());

                Intent intent = new Intent(MainActivity.this, BikeDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("bikeId", bikes.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + bikes.get(position).getId());

            }
        });
        ListBikeViewModel.Factory factory = new ListBikeViewModel.Factory(getApplication(),email);
        viewModel = new ViewModelProvider(this,factory).get(ListBikeViewModel.class);
        viewModel.getAllBikesOfMechanic().observe(this, bikesEntities -> {
            if(bikesEntities !=null){
                bikes = bikesEntities;
                adapter.setData(bikes);
            }
        });

        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.archives){
            startActivity(new Intent(this, BikeArchivedActivity.class));
        }
        if(item.getItemId()== R.id.settings){
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}