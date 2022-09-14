package com.example.demo.model;

public class SalesRep {

    private int id;

    private String name;

    private static int initialID = 0;

    public SalesRep(String name) {
        setUniqueId();
        this.name = name;
    }

    public void setUniqueId() {
        this.id = initialID++;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
