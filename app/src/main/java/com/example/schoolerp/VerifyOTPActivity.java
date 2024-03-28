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
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
public class VerifyOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_otpactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button = findViewById(R.id.buttonVerify);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inputCode1 = findViewById(R.id.inputCode1);
                EditText inputCode2 = findViewById(R.id.inputCode2);
                EditText inputCode3 = findViewById(R.id.inputCode3);
                EditText inputCode4 = findViewById(R.id.inputCode4);

                String code1 = inputCode1.getText().toString().trim();
                String code2 = inputCode2.getText().toString().trim();
                String code3 = inputCode3.getText().toString().trim();
                String code4 = inputCode4.getText().toString().trim();

                if (!code1.isEmpty() && !code2.isEmpty() && !code3.isEmpty() && !code4.isEmpty()) {
                    // All input fields are filled
                    // Set up TextWatcher for each OTP box
                    inputCode1.addTextChangedListener(new OTPTextWatcher(inputCode1, inputCode2));
                    inputCode2.addTextChangedListener(new OTPTextWatcher(inputCode2, inputCode3));
                    inputCode3.addTextChangedListener(new OTPTextWatcher(inputCode3, inputCode4));

                    // Navigate to the next activity (SignupActivity)
                    Intent intent = new Intent(VerifyOTPActivity.this, SignupActivity.class);
                    startActivity(intent);
                } else {
                    // Display a message indicating all fields must be filled
                    Toast.makeText(VerifyOTPActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // TextWatcher implementation
    class OTPTextWatcher implements TextWatcher {
        private EditText currentBox;
        private EditText nextBox;

        public OTPTextWatcher(EditText currentBox, EditText nextBox) {
            this.currentBox = currentBox;
            this.nextBox = nextBox;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 1) {
                // Move focus to the next EditText box
                nextBox.requestFocus();
            }
        }
    }
}
