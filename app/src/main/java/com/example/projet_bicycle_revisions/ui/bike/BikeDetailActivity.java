package com.example.projet_bicycle_revisions.ui.bike;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.projet_bicycle_revisions.R;
import com.example.projet_bicycle_revisions.database.entity.BikesEntity;
import com.example.projet_bicycle_revisions.ui.MainActivity;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;
import com.example.projet_bicycle_revisions.viewmodel.BikeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Locale;

public class BikeDetailActivity extends AppCompatActivity {
    BottomNavigationView navigationView;

    EditText etTypeBike;
    EditText etProblemBike;
    EditText etFirstnameBike;
    EditText etLastnameBike;
    EditText etAddressBike;
    EditText etPhoneBike;

    Button editBike;
    Button deleteBike;
    Button cancelBike;
    Button saveBike;
    Button finishBike;

    boolean editMode;

    BikeViewModel bikeViewModel;

    BikesEntity bike;

    Toast toast;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikedetail);

        setTitle("");

        etTypeBike = findViewById(R.id.typeBikeDetails);
        etProblemBike = findViewById(R.id.problemBikeDetails);
        etFirstnameBike = findViewById(R.id.firstnameBikeDetails);
        etLastnameBike = findViewById(R.id.lastnameBikeDetails);
        etAddressBike = findViewById(R.id.addressBikeDetails);
        etPhoneBike = findViewById(R.id.phoneBikeDetails);

        editBike = findViewById(R.id.editBike);
        deleteBike = findViewById(R.id.deleteBike);
        cancelBike = findViewById(R.id.cancelBike);
        saveBike = findViewById(R.id.saveBike);
        finishBike = findViewById(R.id.finishBike);

        long bikeId = getIntent().getExtras().getLong("bikeId");
        dataInitialiser(bikeId);
        switchMode();

        editBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMode=true;
                switchMode();
            }
        });
        deleteBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBike();
            }
        });
        cancelBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMode=false;
                switchMode();
            }
        });
        saveBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMode=false;
                saveBike();
            }
        });
        finishBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bike.setFinished(true);
                saveBike();
                goBack();
            }
        });
    }

    public void dataInitialiser(long id) {

        BikeViewModel.Factory factory = new BikeViewModel.Factory(getApplication(),id);
        bikeViewModel = new ViewModelProvider(this, factory).get(BikeViewModel.class);
        bikeViewModel.getBike().observe(this, bikesEntity -> {
            if (bikesEntity != null) {
                bike = bikesEntity;
                updateContent();
            }
        });
    }

    /**
     * Switches between read and edit mode regarding of the boolean editMode
     */
    public void switchMode(){
        if(editMode == true){

            etTypeBike.setFocusable(true);
            etProblemBike.setFocusable(true);
            etFirstnameBike.setFocusable(true);
            etLastnameBike.setFocusable(true);
            etAddressBike.setFocusable(true);
            etPhoneBike.setFocusable(true);

            etTypeBike.setFocusableInTouchMode(true);
            etProblemBike.setFocusableInTouchMode(true);
            etFirstnameBike.setFocusableInTouchMode(true);
            etLastnameBike.setFocusableInTouchMode(true);
            etAddressBike.setFocusableInTouchMode(true);
            etPhoneBike.setFocusableInTouchMode(true);

            etTypeBike.setEnabled(true);
            etProblemBike.setEnabled(true);
            etFirstnameBike.setEnabled(true);
            etLastnameBike.setEnabled(true);
            etAddressBike.setEnabled(true);
            etPhoneBike.setEnabled(true);

            editBike.setVisibility(View.GONE);
            deleteBike.setVisibility(View.GONE);
            cancelBike.setVisibility(View.VISIBLE);
            saveBike.setVisibility(View.VISIBLE);
            finishBike.setVisibility(View.GONE);


        }
        else{
            etTypeBike.setFocusable(false);
            etProblemBike.setFocusable(false);
            etFirstnameBike.setFocusable(false);
            etLastnameBike.setFocusable(false);
            etAddressBike.setFocusable(false);
            etPhoneBike.setFocusable(false);

            etTypeBike.setFocusableInTouchMode(false);
            etProblemBike.setFocusableInTouchMode(false);
            etFirstnameBike.setFocusableInTouchMode(false);
            etLastnameBike.setFocusableInTouchMode(false);
            etAddressBike.setFocusableInTouchMode(false);
            etPhoneBike.setFocusableInTouchMode(false);

            etTypeBike.setEnabled(false);
            etProblemBike.setEnabled(false);
            etFirstnameBike.setEnabled(false);
            etLastnameBike.setEnabled(false);
            etAddressBike.setEnabled(false);
            etPhoneBike.setEnabled(false);

            editBike.setVisibility(View.VISIBLE);
            deleteBike.setVisibility(View.VISIBLE);
            cancelBike.setVisibility(View.GONE);
            saveBike.setVisibility(View.GONE);
            finishBike.setVisibility(View.VISIBLE);

        }
    }

    /**
     * Updates the EditTextes with new data
     */
    private void updateContent() {
        if(bike!=null){
            etTypeBike.setText(bike.getTypeBike());
            etProblemBike.setText(bike.getDescriptionBike());
            etFirstnameBike.setText(bike.getFirstNameBike());
            etLastnameBike.setText(bike.getLastNameBike());
            etAddressBike.setText(bike.getAddressBike());
            etPhoneBike.setText(bike.getTelephoneBike());
        }
    }

    /**
     * Method to delete bike with alertDialog for confirmation
     */
    public void deleteBike(){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.action_delete));
        alertDialog.setCancelable(false);
        alertDialog.setMessage(getString(R.string.delete_msg_bike));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
            bikeViewModel.deleteBike(bike, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    goBack();
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.show();
    }

    /**
     * Method to return to the previous activity after, for example, deleting the bike
     */
    public void goBack(){
        startActivity(new Intent(this,MainActivity.class));
    }

    /**
     * Saves the changes made on bike's information
     */
    public void saveBike(){
        if(etTypeBike.getText().equals(null)){
            etTypeBike.setError(getString(R.string.error_non_null));
            etTypeBike.requestFocus();
            return;
        }
        if(etProblemBike.getText().toString().equals(null)){
            etProblemBike.setError(getString(R.string.error_non_null));
            etProblemBike.requestFocus();
            return;
        }
        bike.setTypeBike(etTypeBike.getText().toString());
        bike.setDescriptionBike(etProblemBike.getText().toString());
        bike.setFirstNameBike(etFirstnameBike.getText().toString());
        bike.setLastNameBike(etLastnameBike.getText().toString());
        bike.setAddressBike(etAddressBike.getText().toString());
        bike.setTelephoneBike(etPhoneBike.getText().toString());

        bikeViewModel.updateBike(bike, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {
        if (response) {
            updateContent();
            toast = Toast.makeText(this, getString(R.string.bike_edited), Toast.LENGTH_LONG);
            toast.show();
            editMode=false;
            switchMode();
        } else {
            toast = Toast.makeText(this, getString(R.string.bike_not_edited), Toast.LENGTH_LONG);
        }
    }

}
