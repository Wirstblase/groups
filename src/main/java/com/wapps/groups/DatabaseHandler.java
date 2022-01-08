package com.wapps.groups;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class DatabaseHandler {

    private String server;
    private static DatabaseHandler instance;
    private DB database;
    private MongoClient mongoClient;

    public DatabaseHandler() {
    }

    public static DatabaseHandler getInstance() {
        if (instance == null)
            instance = new DatabaseHandler();
        return instance;
    }

    public void setDatabase(DB database) {
        this.database = database;
    }

    public void setServer(String newServer){
        server = newServer;
    }

    public DB getDatabase() {
        return database;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public boolean isConnected(){
        try {
            CommandResult result = database.command("ping");
            return result.ok();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        }

    public void init() {
        try {
            mongoClient = new MongoClient(server, 27017);
        } catch (UnknownHostException ignored) {

        }
        database = mongoClient.getDB("Groups");
    }

}
