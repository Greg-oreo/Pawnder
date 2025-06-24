package edu.utsa.cs3443.pawnder;

import android.net.Uri;
import java.util.List;

public class UserPetProfile {

    // User info
    public String userName;
    public String userAge;
    public String userLocation;
    public String userBio;
    public Uri userImageUri;

    // Pet info
    public String petName;
    public String petAge;
    public String petBreed;
    public String petLocation;
    public String petTemperament;
    public String petDescription;
    public String petGender;
    private List<Integer> petPhotos; // Assuming drawable resource IDs

    // Constructor
    public UserPetProfile(String userName, String userAge, String userLocation, String userBio, Uri userImageUri,
                          String petName, String petAge, String petBreed, String petLocation, String petTemperament,
                          String petDescription, String petGender, List<Integer> petPhotos) {

        this.userName = userName;
        this.userAge = userAge;
        this.userLocation = userLocation;
        this.userBio = userBio;
        this.userImageUri = userImageUri;

        this.petName = petName;
        this.petAge = petAge;
        this.petBreed = petBreed;
        this.petLocation = petLocation;
        this.petTemperament = petTemperament;
        this.petDescription = petDescription;
        this.petGender = petGender;
        this.petPhotos = petPhotos;
    }

    public List<Integer> getPhotos() {
      return petPhotos;
    }
}
