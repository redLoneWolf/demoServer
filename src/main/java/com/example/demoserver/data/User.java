package com.example.demoserver.data;

public class User {
    private int id;
    private String email;


    public User(int id, String email) {
        this.id = id;
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
