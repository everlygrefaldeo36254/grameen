package com.example.technicalassignment.object;

public class PublicHolidays {
    String date,localName,name,countryCode;

    public PublicHolidays(String date, String localName, String name, String countryCode) {
        this.date = date;
        this.localName = localName;
        this.name = name;
        this.countryCode = countryCode;
    }

    public PublicHolidays() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
