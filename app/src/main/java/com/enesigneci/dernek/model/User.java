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
    String UserCardId;
    String TCId;

    public User() {
    }

    public User(String name, String surname, int bloodType, String phoneNumber, String address, String photo,String tcId,String userCardId) {
        Name = name;
        Surname = surname;
        BloodType = bloodType;
        PhoneNumber = phoneNumber;
        Address = address;
        Photo = photo;
        TCId=tcId;
        UserCardId=userCardId;
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

    public String getTCId() {
        return TCId;
    }

    public void setTCId(String TCId) {
        this.TCId = TCId;
    }

    public String getUserCardId() {
        return UserCardId;
    }

    public void setUserCardId(String userCardId) {
        UserCardId = userCardId;
    }
}