package com.example.mobile_app.api.user.factoryUser;

import com.example.mobile_app.api.user.userObject.userInterface;
public abstract class User {
    public abstract userInterface createUser(String userType, String username, String password);
}
