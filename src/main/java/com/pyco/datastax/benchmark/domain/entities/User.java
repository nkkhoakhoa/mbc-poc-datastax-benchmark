package com.pyco.datastax.benchmark.domain.entities;

import java.io.Serializable;

/**
 * Created by KhoaNguyenKieu on 11/30/16.
 */
public class User extends BaseEntity implements Serializable{

    private String username;
    private String email;
    private String gender;

    public User() {
    }

    public User(String username, String email, String gender) {
        this.username = username;
        this.email = email;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String getInsertCommand() {
        return String.format("graph.addVertex(label, 'user', 'username', '%s', 'email','%s'," + " 'gender','%s')", getUsername(), getEmail(), getGender());
    }
}
