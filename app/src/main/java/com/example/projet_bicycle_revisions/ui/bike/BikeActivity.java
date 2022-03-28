package com.example.projet_bicycle_revisions.ui.bike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projet_bicycle_revisions.R;
import com.example.projet_bicycle_revisions.database.async.bike.CreateBike;
import com.example.projet_bicycle_revisions.database.dao.BikeDao;
import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.example.projet_bicycle_revisions.ui.MainActivity;
import com.example.projet_bicycle_revisions.ui.mechanic.MechanicActivity;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BikeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener{

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
        navigationView.setOnItemSelectedListener(this);
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
                etFirstNameBike.getText().toString(),
                etBike.getText().toString(),
                etLastNameBike.getText().toString(),
                etEmailBike.getText().toString(),
                etTelephoneBike.getText().toString(),
                etAddressBike.getText().toString(),
                etDescriptionBike.getText().toString()
        ));
    }

    private void saveNewBike(String firstNameBike,String typeBike, String lastNameBike, String emailBike, String telephoneBike, String addressBike, String descriptionBike){
        if(typeBike.equals("")){
            etBike.setError(getString(R.string.error_non_null));
            etBike.requestFocus();
            return;
        }
        if(descriptionBike.equals("")) {
            etDescriptionBike.setError(getString(R.string.error_non_null));
            etDescriptionBike.requestFocus();
            return;
        }

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        String user = settings.getString(MainActivity.PREFS_USER,null);
        BikesEntity newBike = new BikesEntity(user,typeBike,firstNameBike,lastNameBike,emailBike,telephoneBike,addressBike,descriptionBike,false);

        new CreateBike(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false);
            }
        }).execute(newBike);



        startActivity( new Intent(this, MainActivity.class));
    }
    private void setResponse(Boolean response) {
        if (response) {
            toast = Toast.makeText(this, getString(R.string.bikecreated), Toast.LENGTH_LONG);
            toast.show();
           ;
        } else {
            toast = Toast.makeText(this, "Error", Toast.LENGTH_LONG);
            toast.show();

        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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
        return false;

    }
}