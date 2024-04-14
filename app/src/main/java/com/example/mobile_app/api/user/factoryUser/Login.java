package com.example.mobile_app.api.user.factoryUser;

import com.example.mobile_app.api.user.userObject.userInterface;
import com.example.mobile_app.api.user.userObject.adminUser;
import com.example.mobile_app.api.user.userObject.doctorUser;
import com.example.mobile_app.api.user.userObject.patientUser;
public class Login extends User{
    public userInterface createUser(String userType, String username, String password) {
        if(userType.equals("Admin")) {
            return new adminUser(username, password);
        }
        else if (userType.equals("Doctor")) {
            return new doctorUser(username, password);
        }
        else if (userType.equals("Patient")) {
            return new patientUser(username, password);
        }
        else {
            System.out.println("Invalid user type");
        }
        return null;
    }
}
