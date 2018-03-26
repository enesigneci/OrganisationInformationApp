package com.enesigneci.dernek.model;

/**
 * Created by rdcmac on 26.03.2018.
 */

public class User {
    String Id;
    String Name;
    String Surname;
    int BloodType;
    String PhoneNumber;
    String Address;
    String Photo;

    public User() {
    }

    public User(String name, String surname, int bloodType, String phoneNumber, String address, String photo) {
        Name = name;
        Surname = surname;
        BloodType = bloodType;
        PhoneNumber = phoneNumber;
        Address = address;
        Photo = photo;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public int getBloodType() {
        return BloodType;
    }

    public void setBloodType(int bloodType) {
        BloodType = bloodType;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}