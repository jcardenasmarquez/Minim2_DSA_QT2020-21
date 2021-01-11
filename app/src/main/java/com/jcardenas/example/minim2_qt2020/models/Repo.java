package com.jcardenas.example.minim2_qt2020.models;

import java.io.Serializable;

public class Repo implements Serializable {

    private static Repo repoinstance;

    public String name;
    public int id;
    public String language;

    public Repo(){}

    public static synchronized Repo getInstance(){
        if(repoinstance == null) {
            repoinstance = new Repo();
        }
        return repoinstance;
    }

    public Repo(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
