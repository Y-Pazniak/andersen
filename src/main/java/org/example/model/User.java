package org.example.model;

public abstract class User {
    private static int idCounter = 0;
    private final int id;
    private final String name;

    public User(final String name){
        id = ++idCounter;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
