package com.example.vicol.lab2pam.domain;

public class Description implements DescriptionAndNoteInterface {
    private String date;

    public Description(String date) {
        this.date = date;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
