package com.wapps.groups;

public class MyUserHandler implements UserHandler {

    private MyUser myUser;
    private static MyUserHandler instance;

    public static MyUserHandler getInstance(){
        if(instance == null)
            instance = new MyUserHandler();
        return instance;
    }

    public MyUser getUser(){
        return myUser;
    }

    public void init(){
        myUser = new MyUser("new", "new", "new");
    }

}
