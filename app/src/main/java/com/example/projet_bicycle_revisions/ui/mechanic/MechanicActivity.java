package com.example.projet_bicycle_revisions.ui.mechanic;

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
import com.example.projet_bicycle_revisions.database.entity.MechanicEntity;
import com.example.projet_bicycle_revisions.ui.MainActivity;
import com.example.projet_bicycle_revisions.ui.bike.BikeActivity;
import com.example.projet_bicycle_revisions.ui.mgmt.LoginActivity;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;
import com.example.projet_bicycle_revisions.viewmodel.MechanicViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MechanicActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    protected BottomNavigationView navigationView;
    EditText firstname;
    EditText lastname;
    EditText email;
    EditText phone;
    EditText address;

    Button editButton;
    Button deleteButton;
    Button logoutButton;
    Button cancelButton;
    Button saveButton;

    boolean editMode;

    MechanicViewModel viewModel;

    MechanicEntity mechanic;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic);

        //Bottom navigation menu
        navigationView = new BottomNavigationView(this);
        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnItemSelectedListener(this);
        navigationView.setSelectedItemId(R.id.person);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        setTitle("");

        //Edit text initialisation
        firstname = findViewById(R.id.firstnameMechanicProfile);
        lastname = findViewById(R.id.lastnameMechanicProfile);
        email = findViewById(R.id.emailMechanicProfile);
        phone = findViewById(R.id.telephoneMechanicProfile);
        address = findViewById(R.id.addressMechanicProfile);
        editButton = findViewById(R.id.editProfile);
        deleteButton = findViewById(R.id.deleteProfile);
        logoutButton = findViewById(R.id.logoutProfile);
        cancelButton = findViewById(R.id.cancelProfile);
        saveButton = findViewById(R.id.saveProfile);
        switchMode();
        dataInitialiser();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMode = true;
                switchMode();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMode = false;
                switchMode();
                dataInitialiser();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    /**
     * Get the mechanic regarding the shared parameter PREFS_NAME
     */
    public void dataInitialiser() {
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
        String user = settings.getString(MainActivity.PREFS_USER, null);
        MechanicViewModel.Factory factory = new MechanicViewModel.Factory(getApplication(), user);
        viewModel = new ViewModelProvider(this, factory).get(MechanicViewModel.class);
        viewModel.getMechanic().observe(this, mechanicEntity -> {
            if (mechanicEntity != null) {
                mechanic = mechanicEntity;
                updateContent();
            }
        });
    }

    /**
     * Switches between read and edit mode regarding of the boolean editMode
     */
    public void switchMode() {
        if (editMode == true) {

            //Edit mode
            firstname.setFocusable(true);
            lastname.setFocusable(true);
            email.setFocusable(true);
            phone.setFocusable(true);
            address.setFocusable(true);

            firstname.setFocusableInTouchMode(true);
            lastname.setFocusableInTouchMode(true);
            email.setFocusableInTouchMode(true);
            phone.setFocusableInTouchMode(true);
            address.setFocusableInTouchMode(true);

            firstname.setEnabled(true);
            lastname.setEnabled(true);
            email.setEnabled(true);
            phone.setEnabled(true);
            address.setEnabled(true);

            editButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.GONE);
            cancelButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);


        } else {
            //Normal mode
            firstname.setFocusable(false);
            lastname.setFocusable(false);
            email.setFocusable(false);
            phone.setFocusable(false);
            address.setFocusable(false);

            firstname.setFocusableInTouchMode(false);
            lastname.setFocusableInTouchMode(false);
            email.setFocusableInTouchMode(false);
            phone.setFocusableInTouchMode(false);
            address.setFocusableInTouchMode(false);

            firstname.setEnabled(false);
            lastname.setEnabled(false);
            email.setEnabled(false);
            phone.setEnabled(false);
            address.setEnabled(false);

            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);

        }
    }

    /**
     * Set the mechanic informations in the EditTexts
     */
    private void updateContent() {
        if (mechanic != null) {
            firstname.setText(mechanic.getName());
            lastname.setText(mechanic.getSurname());
            email.setText(mechanic.getEmail());
            address.setText(mechanic.getAddress());
            phone.setText(mechanic.getTelephone());
        }
    }

    /**
     * Saves the changes made in edit mode
     */
    public void saveChanges() {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            email.setError(getString(R.string.error_invalid_email));
            email.requestFocus();
            return;
        }
        mechanic.setName(firstname.getText().toString());
        mechanic.setSurname(lastname.getText().toString());
        mechanic.setEmail(email.getText().toString());
        mechanic.setTelephone(phone.getText().toString());
        mechanic.setAddress(address.getText().toString());

        viewModel.updateMechanic(mechanic, new OnAsyncEventListener() {
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

    /**
     * Gives user an output when he has modified the infos
     * @param response
     */
    private void setResponse(Boolean response) {
        if (response) {
            updateContent();
            toast = Toast.makeText(this, getString(R.string.mechanic_edited), Toast.LENGTH_LONG);
            toast.show();
            editMode = false;
            switchMode();
        } else {
            email.setError(getString(R.string.error_used_email));
            email.requestFocus();
        }
    }

    /**
     * Delete methode with alertDialog for confirmation
     */
    public void deleteAccount() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.action_delete));
        alertDialog.setCancelable(false);
        alertDialog.setMessage(getString(R.string.delete_msg));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_delete), (dialog, which) -> {
            viewModel.deleteMechanic(mechanic, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    logout();
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
     * Resets shared preferences and sends user back to login activity
     */
    public void logout() {
        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_NAME, 0).edit();
        editor.remove(MainActivity.PREFS_USER);
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.person:
                //do nothing
                break;
            case R.id.add:
                startActivity(new Intent(this, BikeActivity.class));
                break;
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                break;
            default:
                break;
        }

        return false;
    }


}
