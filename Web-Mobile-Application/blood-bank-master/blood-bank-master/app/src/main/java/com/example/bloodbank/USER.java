package com.example.bloodbank;

public class USER {
    private String Name;
    private String Phone_Number;
    private String LAST_DATE_OF_DONATION;
    private String Blood_Group;
    private String City;

    public  USER(){


    }

    public USER(String name, String phone_Number, String LAST_DATE_OF_DONATION, String blood_Group, String city) {
        Name = name;
        Phone_Number = phone_Number;
        this.LAST_DATE_OF_DONATION = LAST_DATE_OF_DONATION;
        Blood_Group = blood_Group;
        City = city;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getLAST_DATE_OF_DONATION() {
        return LAST_DATE_OF_DONATION;
    }

    public void setLAST_DATE_OF_DONATION(String LAST_DATE_OF_DONATION) {
        this.LAST_DATE_OF_DONATION = LAST_DATE_OF_DONATION;
    }

    public String getBlood_Group() {
        return Blood_Group;
    }

    public void setBlood_Group(String blood_Group) {
        Blood_Group = blood_Group;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
