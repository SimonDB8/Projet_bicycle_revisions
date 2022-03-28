package com.example.projet_bicycle_revisions.ui.bike;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_bicycle_revisions.R;
import com.example.projet_bicycle_revisions.adapter.RecyclerAdapter;
import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.example.projet_bicycle_revisions.ui.MainActivity;
import com.example.projet_bicycle_revisions.util.RecyclerViewItemClickListener;
import com.example.projet_bicycle_revisions.viewmodel.ListBikeArchivedViewModel;

import java.util.ArrayList;
import java.util.List;

public class BikeArchivedActivity extends AppCompatActivity {

    private static final String TAG1= "AccountsActivity";


    private List<BikesEntity> bikes;
    private RecyclerAdapter<BikesEntity> adapter;
    private ListBikeArchivedViewModel viewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archived_bike);
        setTitle("Archived Bikes");


        RecyclerView recyclerView = findViewById(R.id.bikesArchivedRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_USER, 0);
        String email = settings.getString(MainActivity.PREFS_USER, null);

        bikes = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG1, "clicked position:" + position);
                Log.d(TAG1, "clicked on: " + bikes.get(position).getId());

                Intent intent = new Intent(BikeArchivedActivity.this, BikeDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("bikeId", bikes.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG1, "longClicked position:" + position);
                Log.d(TAG1, "longClicked on: " + bikes.get(position).getId());

            }
        });
        ListBikeArchivedViewModel.Factory factory =
                new ListBikeArchivedViewModel.Factory(getApplication(),email);
        viewModel = new ViewModelProvider(this,factory).get(ListBikeArchivedViewModel.class);
        viewModel.getAllBikesOfMechanic().observe(this, bikesEntities -> {
            if(bikesEntities !=null){
                bikes = bikesEntities;
                adapter.setData(bikes);
            }
        });

        recyclerView.setAdapter(adapter);


    }
}
