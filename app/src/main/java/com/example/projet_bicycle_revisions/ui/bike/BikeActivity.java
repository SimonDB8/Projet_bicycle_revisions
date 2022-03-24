package com.example.projet_bicycle_revisions.ui.bike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projet_bicycle_revisions.R;
import com.example.projet_bicycle_revisions.database.async.bike.CreateBike;
import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.example.projet_bicycle_revisions.ui.MainActivity;
import com.example.projet_bicycle_revisions.ui.mechanic.MechanicActivity;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BikeActivity extends AppCompatActivity implements NavigationBarView.OnItemReselectedListener{

    protected BottomNavigationView navigationView;

    private Toast toast;

    private EditText etFirstNameBike;
    private EditText etLastNameBike;
    private EditText etEmailBike;
    private EditText etTelephoneBike;
    private EditText etAddressBike;
    private EditText etDescrptionBike;



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

      initializeForm();
        toast = Toast.makeText(this, "Bike created",Toast.LENGTH_LONG);
    }


    private void initializeForm(){
        etFirstNameBike = findViewById(R.id.firstnameBike);
        etLastNameBike  = findViewById(R.id.lastnameBike);
        etEmailBike = findViewById(R.id.emailBike);
        etTelephoneBike = findViewById(R.id.telephoneBike);
        etAddressBike= findViewById(R.id.addressBike);
        etDescrptionBike = findViewById(R.id.descriptionProblem);
        Button createBike = findViewById(R.id.registerRepair);
        createBike.setOnClickListener(view -> saveNewBike(
                etFirstNameBike.getText().toString(),
                etLastNameBike.getText().toString(),
                etEmailBike.getText().toString(),
                etTelephoneBike.getText().toString(),
                etAddressBike.getText().toString(),
                etDescrptionBike.getText().toString()
        ));
    }

    private void saveNewBike(String firstNameBike, String lastNameBike, String emailBike, String telephoneBike, String addressBike, String descriptionBike){
        BikesEntity newBike = new BikesEntity(MainActivity.PREFS_NAME,firstNameBike,lastNameBike,emailBike,telephoneBike,addressBike,descriptionBike);

        new CreateBike(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        }).execute(newBike);

        startActivity( new Intent(this, MainActivity.class));
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