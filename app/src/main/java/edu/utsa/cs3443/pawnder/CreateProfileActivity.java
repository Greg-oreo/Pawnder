package edu.utsa.cs3443.pawnder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class CreateProfileActivity extends AppCompatActivity {

    private static final int PICK_USER_IMAGE_REQUEST = 101;

    EditText userNameField, userAgeField, userLocationField, userBioField, userEmailField;
    ImageView userImagePreview;
    Button continueBtn, uploadUserPhotoBtn;

    Uri userPhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile);

        // Initialize UI
        userNameField = findViewById(R.id.userNameField);
        userAgeField = findViewById(R.id.userAgeField);
        userLocationField = findViewById(R.id.userLocationField);
        userBioField = findViewById(R.id.userBioField);
        userEmailField = findViewById(R.id.userEmailField);
        userImagePreview = findViewById(R.id.userImagePreview);
        continueBtn = findViewById(R.id.continueBtn);
        uploadUserPhotoBtn = findViewById(R.id.uploadUserPhotoBtn);

        // Upload profile photo
        uploadUserPhotoBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_USER_IMAGE_REQUEST);
        });

        // Continue to pet profile
        continueBtn.setOnClickListener(v -> {
            String name = userNameField.getText().toString().trim();
            String age = userAgeField.getText().toString().trim();
            String location = userLocationField.getText().toString().trim();
            String bio = userBioField.getText().toString().trim();
            String email = userEmailField.getText().toString().trim();

            if (name.isEmpty() || age.isEmpty() || location.isEmpty() || bio.isEmpty() || email.isEmpty() || userPhotoUri == null) {
                Toast.makeText(this, "Please fill out all fields and upload an image", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pass data to next activity
            Intent intent = new Intent(CreateProfileActivity.this, CreatePetProfile.class);
            intent.putExtra("userName", name);
            intent.putExtra("userAge", age);
            intent.putExtra("userLocation", location);
            intent.putExtra("userBio", bio);
            intent.putExtra("userEmail", email);
            intent.putExtra("userPhotoUri", userPhotoUri.toString());
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_USER_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            userPhotoUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), userPhotoUri);
                userImagePreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
