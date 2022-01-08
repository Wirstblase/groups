package com.wapps.groups;

public class User {
    private String username;
    private String password;
    private String email;

    User(String username,String email, String password) {
        this.password = password;
        this.username = username;
        this.email = email;
    }

    void setUsername(String username1){
        username = username1;
    }

    void setPassword(String password1){
        password = password1;
    }

    void setEmail(String email1){
        email = email1;
    }

    String getUsername(){
        return username;
    }

    String getPassword(){
        return password;
    }

    String getEmail(){
        return email;
    }

}
