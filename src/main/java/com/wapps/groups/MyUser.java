package com.wapps.groups;

public class MyUser implements User{
    private String username;
    private String password;
    private String email;

    MyUser(String username, String email, String password) {
        this.password = password;
        this.username = username;
        this.email = email;
    }

    public void setUsername(String username1){
        username = username1;
    }

    public void setPassword(String password1){
        password = password1;
    }

    public void setEmail(String email1){
        email = email1;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }

}
