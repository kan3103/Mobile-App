package com.example.mobile_app.api.user.userObject;

public class doctorUser implements userInterface{
    private String username;
    private String password;
    public doctorUser(String username, String password) {
        this.username = username;
        this.password = password;
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
