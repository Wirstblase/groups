package com.wapps.groups;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DataRefreshHandler {

    private static DataRefreshHandler instance;

    public DataRefreshHandler() {

    }

    public static DataRefreshHandler getInstance() {
        if (instance == null) {
            instance = new DataRefreshHandler();
        }
        return instance;
    }

    public void addNewUser(String userName, String password, String email) {

        DB db = DatabaseHandler.getInstance().getDatabase();
        DBCollection users = db.getCollection("Users");

        BasicDBObject newUser = new BasicDBObject();

        newUser.append("Username", userName);
        newUser.append("Email", email);
        newUser.append("Password", password);
        newUser.append("Bio", "Hi! I'm new here'");

        users.insert(newUser);

    }

    public void sendMessage(String message) {

        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        System.out.println(timeStamp);
        String username = "";
        username = UserHandler.getInstance().getUser().getUsername();

        DB db = DatabaseHandler.getInstance().getDatabase();
        DBCollection chat = db.getCollection("Chat");

        //List<DBObject> messageObj = null;

        BasicDBObject newMessage = new BasicDBObject();

        newMessage.append("Sender", username);
        newMessage.append("Content", message);
        newMessage.append("ContentType", "1");
        newMessage.append("TimeStamp", timeStamp);

        chat.insert(newMessage);

    }

    public String getBio() {
        String bio = "", username = UserHandler.getInstance().getUser().getUsername();

        DB db = DatabaseHandler.getInstance().getDatabase();
        DBCollection users = db.getCollection("Users");
        List<DBObject> result = users.find().toArray();
        for (DBObject object : result) {
            //System.out.println(object);
            String name = (String) object.get("Username");
            if (name.equals(username))
                bio = (String) object.get("Bio");
            //System.out.println(name);
        }

        return bio;
    }

    public void setBio(String newBio) {
        //yes
        String bio = "", username = UserHandler.getInstance().getUser().getUsername();
        BasicDBObject existingUser = new BasicDBObject();
        BasicDBObject queryObj = new BasicDBObject();
        String id = "";

        DB db = DatabaseHandler.getInstance().getDatabase();
        DBCollection users = db.getCollection("Users");
        List<DBObject> result = users.find().toArray();
        for (DBObject object : result) {
            //System.out.println(object);
            String name = (String) object.get("Username");
            if (name.equals(username)) {

                existingUser.put("Email", object.get("Email"));
                existingUser.put("Password", UserHandler.getInstance().getUser().getPassword());
                existingUser.put("Bio", newBio); //new bio
                existingUser.put("Username", username);

                System.out.println(object.get("_id").getClass());
                ObjectId idObj = (ObjectId) object.get("_id");
                id = idObj.toString();
                queryObj.put("_id", idObj);

            }

        }

        System.out.println("should update bio");
        System.out.println(queryObj);
        System.out.println(existingUser);
        users.update(queryObj, existingUser);
    }


    public void setPassword(String newPassword) {
        //yes
        String bio = "", username = UserHandler.getInstance().getUser().getUsername();
        BasicDBObject existingUser = new BasicDBObject();
        BasicDBObject queryObj = new BasicDBObject();
        String id = "";

        DB db = DatabaseHandler.getInstance().getDatabase();
        DBCollection users = db.getCollection("Users");
        List<DBObject> result = users.find().toArray();
        for (DBObject object : result) {
            //System.out.println(object);
            String name = (String) object.get("Username");
            if (name.equals(username)) {

                existingUser.put("Email", object.get("Email"));
                existingUser.put("Password", newPassword);
                existingUser.put("Bio", object.get("Bio"));
                existingUser.put("Username", username);

                System.out.println(object.get("_id").getClass());
                ObjectId idObj = (ObjectId) object.get("_id");
                id = idObj.toString();
                queryObj.put("_id", idObj);

            }

        }

        System.out.println("should update bio");
        System.out.println(queryObj);
        System.out.println(existingUser);
        users.update(queryObj, existingUser);
    }



    public String getNewUsers() {
        String text = "",bio = "";

        DB db = DatabaseHandler.getInstance().getDatabase();
        DBCollection users = db.getCollection("Users");
        List<DBObject> result = users.find().toArray();
        for (DBObject object : result) {
            //System.out.println(object);
            String name = (String) object.get("Username");
            bio = (String) object.get("Bio");
            //System.out.println(name);
            text = text + name + ":" + bio + " \n\n";
        }

        return text;
    }

    public String getEmailForUser(String username) {
        String text = "",email="error",name="";

        DB db = DatabaseHandler.getInstance().getDatabase();
        DBCollection users = db.getCollection("Users");
        List<DBObject> result = users.find().toArray();
        for (DBObject object : result) {
            //System.out.println(object);
            name = (String) object.get("Username");
            if(username.equals(name))
                email = (String) object.get("Email");
            //System.out.println(name);
        }

        return email;
    }

    public void saveUserDataInverted() {

        String name = UserHandler.getInstance().getUser().getUsername();
        String pass = UserHandler.getInstance().getUser().getPassword();
        String mail = UserHandler.getInstance().getUser().getEmail();

        HashMap<String, String> jsonMap = new HashMap<>();
        jsonMap.put("user", name);
        jsonMap.put("password", pass);
        jsonMap.put("mail", mail); //I have no idea why those two are inverted this makes absolutely no sense
        jsonMap.put("thing","new1"); //another attempt at fixing the bug??


        JSONObject saveObject = new JSONObject(jsonMap);

        try {
            File file = new File("config.json");
            if (!file.exists())
                file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(saveObject.toJSONString());

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveUserData() {

        String name = UserHandler.getInstance().getUser().getUsername();
        String pass = UserHandler.getInstance().getUser().getPassword();
        String mail = UserHandler.getInstance().getUser().getEmail();

        HashMap<String, String> jsonMap = new HashMap<>();
        jsonMap.put("user", name);
        jsonMap.put("password", mail);
        jsonMap.put("mail", pass); //I have no idea why those two are inverted this makes absolutely no sense
        jsonMap.put("thing","new1"); //another attempt at fixing the bug??


        JSONObject saveObject = new JSONObject(jsonMap);

        try {
            File file = new File("config.json");
            if (!file.exists())
                file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(saveObject.toJSONString());

            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadUserData() {

        try {
            JSONObject loadObject = (JSONObject) new JSONParser().parse(new FileReader("config.json"));

            String user = (String) loadObject.get("user");
            String pass = (String) loadObject.get("password");
            String mail = (String) loadObject.get("mail");

            UserHandler.getInstance().getUser().setUsername(user);
            UserHandler.getInstance().getUser().setEmail(mail);
            UserHandler.getInstance().getUser().setPassword(pass);

            System.out.println("loaded user: " + user + " mail:" + mail + " pass:" + pass);
            System.out.println(UserHandler.getInstance().getUser().getPassword());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public String getNewMessages() {
        String text = "";

        DB db = DatabaseHandler.getInstance().getDatabase();
        DBCollection chat = db.getCollection("Chat");
        List<DBObject> result = chat.find().toArray();

        //result.sort(result.get("TimeStamp"));

        for (DBObject object : result) {
            String timeStamp = (String) object.get("TimeStamp");
            String sender = (String) object.get("Sender");
            String content = (String) object.get("Content");

            text = text + timeStamp + " " + sender + ": " + content + "\n";
        }

        return text;
    }


}
