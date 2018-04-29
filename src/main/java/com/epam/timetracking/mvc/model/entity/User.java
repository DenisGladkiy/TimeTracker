package com.epam.timetracking.mvc.model.entity;

/**
 * Created by Denis on 26.04.2018.
 */
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public User(){}

    public User(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
