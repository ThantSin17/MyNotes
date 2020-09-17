package com.stone.mynotes.dto;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date_time")
    private String dateTime;

    @ColumnInfo(name = "note_text")
    private String noteText;

    @ColumnInfo(name = "image_path")
    private String imagePath;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "web_link")
    private String webLink;

    public Note(int id, String title, String dateTime, String noteText, String imagePath, String color, String webLink) {
        this.id = id;
        this.title = title;
        this.dateTime = dateTime;
        this.noteText = noteText;
        this.imagePath = imagePath;
        this.color = color;
        this.webLink = webLink;
    }

    public Note() {
    }

    public Note(Builder builder) {
        this.id=builder.id;
        this.title = builder.title;
        this.dateTime = builder.dateTime;
        this.noteText = builder.noteText;
        this.imagePath = builder.imagePath;
        this.color = builder.color;
        this.webLink = builder.webLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public static class Builder {
        int id;
        String title;
        String dateTime;
        String noteText;
        String imagePath;
        String color;
        String webLink;
        public Builder id(int id) {
            this.id = id;
            return this;
        }
       public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder dateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder noteText(String noteText) {
            this.noteText = noteText;
            return this;
        }
        public Builder imagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }
        public Builder colour(String color) {
            this.color = color;
            return this;
        }
        public Builder webLink(String webLink) {
            this.webLink = webLink;
            return this;
        }
        public Note build(){
            return new Note(this);
        }


    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", noteText='" + noteText + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", color='" + color + '\'' +
                ", webLink='" + webLink + '\'' +
                '}';
    }
}
