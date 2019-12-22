package com.example.comicreader.Model;

public class Chapter {
    private String id;
    private int number;

    public Chapter(String id, int number){
        this.id = id;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
