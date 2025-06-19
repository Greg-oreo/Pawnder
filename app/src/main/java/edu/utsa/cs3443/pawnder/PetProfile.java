package edu.utsa.cs3443.pawnder;

import java.util.List;

public class PetProfile {
    public String name;
    public int age;
    public String breed;
    public String location;
    public String temperament;
    public String description;

    public String gender;
    private List<Integer> photos;

    public PetProfile( String gender,String name, int age, String breed, String location, String temperament, String description, List<Integer> photos) {
        this.gender = gender;
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.location = location;
        this.temperament = temperament;
        this.description = description;
        this.photos = photos;
    }

    public List<Integer> getPhotos() {
        return photos;
    }
}
