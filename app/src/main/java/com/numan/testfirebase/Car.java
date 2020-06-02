package com.numan.testfirebase;

public class Car {
    private String Name;
    private String Email;
    private String imageUri;

    public Car() {
    }

    public Car(String name, String email, String imageUri) {
        Name = name;
        Email = email;
        this.imageUri = imageUri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
