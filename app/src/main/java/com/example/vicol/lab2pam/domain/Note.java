package com.example.vicol.lab2pam.domain;

public class Note implements DescriptionAndNoteInterface {
    private int note_id;
    private int date_id;
    private String time;
    private String date;
    private String title;
    private String description;
    private String iconPath;

    public Note(int note_id, int date_id, String time, String date, String title, String description, String iconPath) {
        this.note_id = note_id;
        this.date_id = date_id;
        this.time = time;
        this.date = date;
        this.title = title;
        this.description = description;
        this.iconPath = iconPath;
    }

    public Note() {

    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public int getDate_id() {
        return date_id;
    }

    public void setDate_id(int date_id) {
        this.date_id = date_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    @Override
    public String toString() {
        return "Note{" +
                "note_id=" + note_id +
                ", date_id=" + date_id +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", iconPath='" + iconPath + '\'' +
                '}';
    }
}
