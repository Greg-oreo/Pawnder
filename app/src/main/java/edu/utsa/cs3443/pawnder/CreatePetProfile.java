package edu.utsa.cs3443.pawnder;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import java.io.IOException;



public class CreatePetProfile extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imagePreview;
    private EditText nameField, breedField, ageField, locationField, descriptionField;
    private Spinner temperamentSpinner;
    private Button uploadPhotoBtn, submitBtn, generateDescriptionBtn;

    private RadioGroup genderGroup;

    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_profile);


        imagePreview = findViewById(R.id.imagePreview);
        nameField = findViewById(R.id.nameField);
        breedField = findViewById(R.id.breedField);
        ageField = findViewById(R.id.ageField);
        locationField = findViewById(R.id.locationField);
        descriptionField = findViewById(R.id.descriptionField);
        temperamentSpinner = findViewById(R.id.temperamentSpinner);
        uploadPhotoBtn = findViewById(R.id.uploadPhotoBtn);
        submitBtn = findViewById(R.id.submitBtn);
        generateDescriptionBtn = findViewById(R.id.generateDescriptionBtn);
        genderGroup = findViewById(R.id.genderGroup);


        // Temperament options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.temperament_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperamentSpinner.setAdapter(adapter);

        uploadPhotoBtn.setOnClickListener(v -> selectImage());

        generateDescriptionBtn.setOnClickListener(v -> {
            // Simulate AI-generated description
            if (photoUri == null) {
                Toast.makeText(this, "Please upload a photo first", Toast.LENGTH_SHORT).show();
            } else {
                descriptionField.setText("An AI-generated description will appear here.");
            }
        });

        submitBtn.setOnClickListener(v -> {
            if (photoUri == null) {
                Toast.makeText(this, "Upload a photo to continue", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = nameField.getText().toString();

            // Do something with the data
            Toast.makeText(this, name + "'s profile created!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CreatePetProfile.this, SwipeActivity.class);
            startActivity(intent);
            // Return to homepage or go to browse page
            finish();

            int selectedGenderId = genderGroup.getCheckedRadioButtonId();
            String gender = "";

            if (selectedGenderId != -1) {
                RadioButton selectedGender = findViewById(selectedGenderId);
                gender = selectedGender.getText().toString();
            } else {
                Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            photoUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                imagePreview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
