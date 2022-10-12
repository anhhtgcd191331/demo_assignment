package com.example.demo_assignment.trips;

public class Trip {
    private int id;
    private String name;
    private String destination;
    private String date;
    private String require;
    private String description;
    private String username;

    public Trip() {
    }

    public Trip(int id, String name, String destination, String date, String require, String description,String username) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.date = date;
        this.require = require;
        this.description = description;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
