package org.example.model;

public abstract class User {
    private static int idCounter = 0;
    private final int id;

    public User(){
        id = ++idCounter;
    }

    public int getId() {
        return id;
    }

}
