package com.example.schoolerp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SendOTPActivity extends AppCompatActivity {

    EditText inputMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sent_o_t_p);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputMobile = findViewById(R.id.inputMobile);
        Button button = findViewById(R.id.buttonGetotp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateMobileNumber()) {
                    // Proceed to the next activity if mobile number is valid
                    Intent intent = new Intent(SendOTPActivity.this, VerifyOTPActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateMobileNumber() {

        String mobileNumber = inputMobile.getText().toString().trim();
        // Check if the mobile number consists of only digits and has a length of exactly 10 characters

        if (mobileNumber.isEmpty() ) {
            inputMobile.setError("FIELD CANNOT BE EMPTY");
            inputMobile.requestFocus();
            return false;
        }   else if (mobileNumber.length() > 10) {
            inputMobile.requestFocus();
            inputMobile.setError("INVALID");
            return false;
        }   else if (mobileNumber.length() < 10) {
            inputMobile.requestFocus();
            inputMobile.setError("INVALID");
            return false;
        } else if (mobileNumber.matches("[0-9]+")) {
            inputMobile.requestFocus();

        }
        return true;
    }

    private boolean isValidMobileNumber(String mobileNumber) {
        // You can define your own validation logic here
        // For example, checking if it consists of only digits and has a specific length
        return mobileNumber.matches("[0-9]+") && mobileNumber.length() == 10;
    }
}
