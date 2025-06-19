package edu.utsa.cs3443.pawnder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    private Button createProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        createProfileButton = findViewById(R.id.button_create_profile);

        createProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, CreateProfileActivity.class);
            startActivity(intent);
        });
    }
}

