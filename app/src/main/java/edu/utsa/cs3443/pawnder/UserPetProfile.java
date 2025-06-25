package edu.utsa.cs3443.pawnder;

import android.net.Uri;
import java.util.List;

public class UserPetProfile {

    // User info
    public String userName;
    public String userAge;
    public String userLocation;
    public String userBio;
    public List<Uri> userImageUris;  // multiple user images

    // Pet info
    public String petName;
    public String petAge;
    public String petBreed;
    public String petLocation;
    public String petTemperament;
    public String petDescription;
    public String petGender;
    private List<Integer> petPhotos; // drawable resource IDs

    // Constructor
    public UserPetProfile(String userName, String userAge, String userLocation, String userBio, List<Uri> userImageUris,
                          String petName, String petAge, String petBreed, String petLocation, String petTemperament,
                          String petDescription, String petGender, List<Integer> petPhotos) {

        this.userName = userName;
        this.userAge = userAge;
        this.userLocation = userLocation;
        this.userBio = userBio;
        this.userImageUris = userImageUris;

        this.petName = petName;
        this.petAge = petAge;
        this.petBreed = petBreed;
        this.petLocation = petLocation;
        this.petTemperament = petTemperament;
        this.petDescription = petDescription;
        this.petGender = petGender;
        this.petPhotos = petPhotos;
    }

    // Getter for pet photos
    public List<Integer> getPhotos() {
        return petPhotos;
    }

    // Getter for user image URIs
    public List<Uri> userImageUris() {
        return userImageUris;
    }
}
