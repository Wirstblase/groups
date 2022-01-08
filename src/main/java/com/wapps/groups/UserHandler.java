package com.wapps.groups;

public class UserHandler {

    private User user;
    private static UserHandler instance;

    public static UserHandler getInstance(){
        if(instance == null)
            instance = new UserHandler();
        return instance;
    }

    public User getUser(){
        return user;
    }

    public void init(){
        user = new User("new", "new", "new");
    }

}
