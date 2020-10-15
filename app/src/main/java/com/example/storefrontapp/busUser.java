package com.example.storefrontapp;

//This is a class to help us insert data from Business Profile Setup page into our Firebase DB.
//Getters and setters for field references.

public class busUser {
    private String fName, lName, street, city, state, country;
    private Integer zipCode;

    public String getfName() {
        return fName;
    }

    public String setfName(String fName) {
        this.fName = fName;
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }


}
