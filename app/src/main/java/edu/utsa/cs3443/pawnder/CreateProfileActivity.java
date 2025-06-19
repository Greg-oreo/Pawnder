package edu.utsa.cs3443.pawnder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateProfileActivity extends AppCompatActivity {

    EditText userNameField, userAgeField, userLocationField, userBioField;
    Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile);

        userNameField = findViewById(R.id.userNameField);
        userAgeField = findViewById(R.id.userAgeField);
        userLocationField = findViewById(R.id.userLocationField);
        userBioField = findViewById(R.id.userBioField);
        continueBtn = findViewById(R.id.continueBtn);

        continueBtn.setOnClickListener(v -> {
            String name = userNameField.getText().toString().trim();
            String age = userAgeField.getText().toString().trim();
            String location = userLocationField.getText().toString().trim();
            String bio = userBioField.getText().toString().trim();

            if (name.isEmpty() || age.isEmpty() || location.isEmpty() || bio.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pass data to the next activity
            Intent intent = new Intent(CreateProfileActivity.this, CreatePetProfile.class);
            intent.putExtra("userName", name);
            intent.putExtra("userAge", age);
            intent.putExtra("userLocation", location);
            intent.putExtra("userBio", bio);
            startActivity(intent);
        });
    }
}
