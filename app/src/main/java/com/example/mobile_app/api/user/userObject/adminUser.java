package com.example.mobile_app.api.user.userObject;

import java.io.Serializable;

public class adminUser implements userInterface, Serializable {
    private String username;
    private String password;
    private String typeuser;

    public String getTypeuser() {
        return typeuser;
    }

    public void setTypeuser(String typeuser) {
        this.typeuser = typeuser;
    }

    public adminUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.typeuser = "Admin";
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
