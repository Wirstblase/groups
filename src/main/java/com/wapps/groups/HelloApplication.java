package com.wapps.groups;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public int sceneIndex = 0, appRunning = 1, sceneLoading = 1;
    public static Stage stageVar;


    @Override
    public void start(Stage stage) throws IOException {

        //stageVar = stage;
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320 * 2, 240 * 2);
//        //scene.getStylesheets().add(getClass().getResource(;).toExternalForm());
//        //scene.getStylesheets().add(getClass().getResource("com/wapps/groups/helloviewstyle.css").toExternalForm());
//        stage.setTitle("login to groups");
//        stage.setScene(scene);
//        stage.setResizable(false);
//
//        //stage.getIcons().add(new Image("com/wapps/groups/groupslogo.png"));
        Image icon = new Image(getClass().getResourceAsStream("groupslogo1.png"));
        stage.getIcons().add(icon);
        stageVar = stage;
//
//        stage.show();
        DataRefreshHandler.getInstance().loadUserData();
        if(!MyUserHandler.getInstance().getUser().getUsername().equals("new")){
            System.out.println("should redirect to main scene");
            HelloApplication.loadMainScene();
        } else {
            loadHelloScene();
        }
    }

    public static void loadHelloScene(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320 * 2, 240 * 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //scene.getStylesheets().add(getClass().getResource(;).toExternalForm());
        //scene.getStylesheets().add(getClass().getResource("com/wapps/groups/helloviewstyle.css").toExternalForm());
        stageVar.setTitle("login to groups");
        stageVar.setScene(scene);
        stageVar.setResizable(false);

//        stage.getIcons().add(new Image("com/wapps/groups/groupslogo.png"));
//        stageVar.getIcons().add(icon);

        stageVar.show();
    }

    public static void loadMainScene(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320*2, 240*2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sceneTitle = "groups - logged in as " + MyUserHandler.getInstance().getUser().getUsername();
        stageVar.setTitle(sceneTitle);
        stageVar.setScene(scene);
        stageVar.show();

    }

    public static void loadChangePasswordScene(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("changepassword-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320*2, 240*2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stageVar.setTitle("groups");
        stageVar.setScene(scene);
        stageVar.show();

    }

    public static void loadSettingsScene(){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("settings-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320*2, 240*2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stageVar.setTitle("groups");
        stageVar.setScene(scene);
        stageVar.show();

    }


    public static void main(String[] args) {

        DatabaseHandler.getInstance().init();
        MyUserHandler.getInstance().init();

        launch();
    }
}