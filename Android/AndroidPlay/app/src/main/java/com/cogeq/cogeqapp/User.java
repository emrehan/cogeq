package com.cogeq.cogeqapp;

/**
 * Created by saygin on 3/6/2016.
 */
public class User {
    private String name;
    private String surname;
    private String username;
    private String passwordHash;
    private String fourSquareAccessToken;

    public User(String name, String surname, String username, String passwordHash) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
