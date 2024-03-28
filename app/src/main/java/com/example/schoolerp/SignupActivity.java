package com.example.schoolerp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.regex.Pattern;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;


public class SignupActivity extends AppCompatActivity {


    EditText signup_name, signup_dob, signup_password, signup_confirm_password;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        signup_name = findViewById(R.id.signup_name);
        signup_dob = findViewById(R.id.signup_dob);
        signup_password = findViewById(R.id.signup_password);
        signup_confirm_password = findViewById(R.id.signup_confirm_password);


        Button signup_button = findViewById(R.id.signup_button);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signup_name.getText().toString().trim();
                String dob = signup_dob.getText().toString().trim();
                String password = signup_password.getText().toString().trim();
                String confirmPassword = signup_confirm_password.getText().toString().trim();

                validateInfo(name, dob, password, confirmPassword);
            }
        });
    }

    private void validateInfo(String name, String dob, String password, String confirmPassword) {
        if (name.isEmpty()) {
            signup_name.setError("Name cannot be empty");
            signup_name.requestFocus();
            return;
        }

        if (dob.isEmpty()) {
            signup_dob.setError("Date of birth cannot be empty");
            signup_dob.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            signup_password.setError("Password cannot be empty");
            signup_password.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            signup_confirm_password.setError("Confirm Password cannot be empty");
            signup_confirm_password.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            signup_confirm_password.setError("Passwords do not match");
            signup_confirm_password.requestFocus();
            return;
        }

        // If all validations pass, you can proceed with your signup process
        // For example, you can start the LoginActivity
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void sign_dob(View view) {
        // Get current date to set as default in the date picker dialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        showDatePickerDialog(year, month, dayOfMonth);
    }

    private void showDatePickerDialog(int year, int month, int dayOfMonth) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update the EditText with the selected date
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        EditText signup_dob = findViewById(R.id.signup_dob); // Replace with your EditText id
                        signup_dob.setText(selectedDate);
                    }
                },
                year, month, dayOfMonth);

        // Show the date picker dialog
        datePickerDialog.show();
    }
}


