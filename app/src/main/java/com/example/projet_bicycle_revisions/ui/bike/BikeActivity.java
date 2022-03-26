package com.example.projet_bicycle_revisions.ui.bike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

    private EditText etBike;
    private EditText etFirstNameBike;
    private EditText etLastNameBike;
    private EditText etEmailBike;
    private EditText etTelephoneBike;
    private EditText etAddressBike;
    private EditText etDescriptionBike;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);
        navigationView = new BottomNavigationView(this);
        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnItemReselectedListener(this);
        navigationView.setSelectedItemId(R.id.add);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        setTitle("");


      initializeForm();
      toast = Toast.makeText(this, "Bike created",Toast.LENGTH_LONG);
    }


    private void initializeForm(){
        etBike = findViewById(R.id.bikeBike);
        etFirstNameBike = findViewById(R.id.firstnameBike);
        etLastNameBike  = findViewById(R.id.lastnameBike);
        etEmailBike = findViewById(R.id.emailBike);
        etTelephoneBike = findViewById(R.id.telephoneBike);
        etAddressBike= findViewById(R.id.addressBike);
        etDescriptionBike = findViewById(R.id.descriptionProblem);
        Button createBike = findViewById(R.id.registerRepair);
        createBike.setOnClickListener(view -> saveNewBike(
                etBike.getText().toString(),
                etFirstNameBike.getText().toString(),
                etLastNameBike.getText().toString(),
                etEmailBike.getText().toString(),
                etTelephoneBike.getText().toString(),
                etAddressBike.getText().toString(),
                etDescriptionBike.getText().toString()
        ));
    }

    private void saveNewBike(String firstNameBike,String typeBike, String lastNameBike, String emailBike, String telephoneBike, String addressBike, String descriptionBike){
        String status="ongoing";
        BikesEntity newBike = new BikesEntity(MainActivity.PREFS_NAME,typeBike,firstNameBike,lastNameBike,emailBike,telephoneBike,addressBike,descriptionBike,false);

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
                //do nothing
                break;
            case R.id.home:
                startActivity( new Intent(this, MainActivity.class));
                break;
            default:
                break;
        }

    }
}