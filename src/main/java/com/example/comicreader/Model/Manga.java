package com.example.comicreader.Model;

//thông tin cơ bản về truyện

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;


public class Manga {
    private String id;
    private String title;
    private String alias;
    private String category;
    private String status;
    private String image;
    private String rating;
    private String summary;
    private String author;
    private String chapter_length;
    private ArrayList<Chapter> chapter;

    public Manga(String id, String title, String alias, String category, String status, String rating, String image) {
        this.id = id;
        this.title = title;
        this.alias = alias;
        this.category = category;
        this.status = status;
        this.rating = rating;
        this.image = image;
        this.summary = "";
        this.author = "";
        this.chapter_length = "";
        this.chapter = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getAlias() {
        return alias;
    }

    public String getCategory() {
        if(this.category.equals("[]")){
            return "-";
        }
        else{
            String category="";
            for (int i = 0 ; i < this.category.length() ; i++){
                if(this.category.charAt(i)=='['||this.category.charAt(i)==']'||this.category.charAt(i)=='"'){
                    continue;
                }
                else if(this.category.charAt(i)==','){
                    category+=this.category.charAt(i)+" ";
                }
                else{
                    category+=this.category.charAt(i);
                }
            }
            return category;
        }
    }

    public String getStatus() {
        if(this.status.equals("0")){
            return "Suspended";
        }
        else if(this.status.equals("1")) {
            return "Ongoing";
        }
        else{
            return "Complete";
        }
    }

    public String getRating() {
        return rating;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public String getSummary(){
        if(summary.equals("")){
            return "-";
        }
        return summary;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public String getAuthor(){
        if(this.author.equals("")){
            return "-";
        }
        return this.author;
    }

    public void setChapter_length(String chapter_length) {
        this.chapter_length = chapter_length;
    }

    public String getChapter_length() {
        if(this.chapter_length.equals("")){
            return "0";
        }
        else{
            return chapter_length;
        }
    }

    public void setChapter(ArrayList<Chapter> chapter){
        this.chapter = chapter;
    }
}


