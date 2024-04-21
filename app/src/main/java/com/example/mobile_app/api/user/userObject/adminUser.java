package com.example.mobile_app.api.user.userObject;

import java.io.Serializable;

public class adminUser implements userInterface, Serializable {
    private String username;
    private String password;
    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeuser() {
        return "Admin";
    }


    public adminUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.name=username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setUserPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}
