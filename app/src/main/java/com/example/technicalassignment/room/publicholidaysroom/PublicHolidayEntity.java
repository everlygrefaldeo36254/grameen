package com.example.technicalassignment.room.publicholidaysroom;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "public_holidays")
public class PublicHolidayEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private String localName;
    private String name;
    private String countryCode;

    public PublicHolidayEntity(int id, String date, String localName, String name, String countryCode) {
        this.id = id;
        this.date = date;
        this.localName = localName;
        this.name = name;
        this.countryCode = countryCode;
    }

    public PublicHolidayEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
