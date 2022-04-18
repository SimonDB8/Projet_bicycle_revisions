package com.example.projet_bicycle_revisions.ui.mgmt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projet_bicycle_revisions.BaseApp;
import com.example.projet_bicycle_revisions.R;

import com.example.projet_bicycle_revisions.database.entity.MechanicEntity;
import com.example.projet_bicycle_revisions.database.repository.MechanicRepository;
import com.example.projet_bicycle_revisions.ui.MainActivity;
import com.example.projet_bicycle_revisions.util.OnAsyncEventListener;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private MechanicRepository repository;

    private Toast toast;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etAddress;
    private EditText etTelephone;
    private EditText etPwd1;
    private EditText etPwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_register);
        initializeForm();
        toast = Toast.makeText(this, getString(R.string.mechanic_created), Toast.LENGTH_LONG);
        repository = ((BaseApp) getApplication()).getMechanicRepository();

    }

    /**
     * Initialise the edit texts
     */
    private void initializeForm() {
        etFirstName = findViewById(R.id.firstname);
        etLastName = findViewById(R.id.lastname);
        etEmail = findViewById(R.id.email);
        etAddress = findViewById(R.id.address);
        etTelephone = findViewById(R.id.telephone);
        etPwd1 = findViewById(R.id.password);
        etPwd2 = findViewById(R.id.passwordRep);
        Button saveBtn = findViewById(R.id.registerButton2);
        saveBtn.setOnClickListener(view -> saveChanges(
                etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etAddress.getText().toString(),
                etTelephone.getText().toString(),
                etEmail.getText().toString(),
                etPwd1.getText().toString(),
                etPwd2.getText().toString()
        ));
    }

    /**
     * Creates an account with double password confirmation
     * If the passwords are not the same, doesn't continue
     * @param firstName
     * @param lastName
     * @param address
     * @param telephone
     * @param email
     * @param pwd
     * @param pwd2
     */
    private void saveChanges(String firstName, String lastName,String address,String telephone, String email, String pwd, String pwd2) {
        if (!pwd.equals(pwd2) || pwd.length() < 6) {
            etPwd1.setError(getString(R.string.error_invalid_password));
            etPwd1.requestFocus();
            etPwd1.setText("");
            etPwd2.setText("");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            return;
        }
        MechanicEntity newMechanic = new MechanicEntity(email,pwd,firstName,lastName,telephone,address);

        repository.register(newMechanic, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createUserWithEmail: success");
                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createUserWithEmail: failure", e);
                setResponse(false);
            }
        });
    }

    /**
     * Set the new user and logs in
     * With email is already used, throws error and focus on email
     * @param response
     */
    private void setResponse(Boolean response) {
        if (response) {
            toast.show();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            etEmail.setError(getString(R.string.error_used_email));
            etEmail.requestFocus();
        }
    }
}
