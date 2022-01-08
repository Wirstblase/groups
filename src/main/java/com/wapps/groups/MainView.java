package com.wapps.groups;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class MainView implements Initializable, Runnable {

    private static MainView instance;
    public String text = "";
    private boolean poggers;
    private DataRefreshHandler refreshHandlerObj;

    @FXML
    Button settingsButton, sendButton;

    @FXML
    Text onlineUsersTxt;

    @FXML
    TextArea textArea, onlineUsersArea;

    @FXML
    TextField inputField;

    public void refreshMessages(){
        textArea.setText(refreshHandlerObj.getNewMessages());
        textArea.setScrollTop(Double.MAX_VALUE);
    }

    public void refreshUsers(){
        onlineUsersArea.setText(refreshHandlerObj.getNewUsers());
    }


    public void settingsButtonPress(){

        HelloApplication.loadSettingsScene();

    }


    public void sendButtonPress(){
        text = inputField.getText();

        if(text.length() < 1){
            inputField.setPromptText("message can't be empty!");
        } else {

            inputField.setPromptText("write your message here!");
            inputField.setText("");

            refreshHandlerObj.sendMessage(text);
            refreshMessages();
            //System.out.println(inputField.getText());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("com/wapps/groups/settingsicon.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        //Background background = new Background(backgroundImage);
        //settingsButton.setBackground(background);

        settingsButton.setStyle("-fx-background-color: #0f0f1b; -fx-background-image: url('http://s00flea.go.ro/settings2525inverted.png');");
        settingsButton.setBackground(Background.EMPTY);
        onlineUsersTxt.setStyle("-fx-text-fill: #FFFFFF; -fx-fill: #FFFFFF");

        //sendButton.setStyle("-fx-border-insets: 5px; -fx-padding: 5px; -fx-background-insets: 5px; -fx-background-color: #2B59C3;");

        textArea.setWrapText(true);
        onlineUsersArea.setWrapText(true);

        inputField.setPromptText("write your message here!");

        refreshHandlerObj = DataRefreshHandler.getInstance();
//        Thread thread = new Thread(refreshHandlerObj);
//        thread.start();

        Thread thread1 = new Thread(this);
        thread1.start();

        poggers = true;
        refreshMessages();
        refreshUsers();

        //inputField.setMaxHeight(20);
    }

    @Override
    public void run() {

        while (poggers) {
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            refreshMessages();
            //System.out.println("refreshed messages");
        }
    }
}