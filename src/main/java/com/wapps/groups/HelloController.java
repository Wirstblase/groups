package com.wapps.groups;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;

import java.util.List;

public class HelloController {

    @FXML
    private Label welcomeText, serverConnectText;

    @FXML
    private Button loginBtn, registerBtn, serverConnectBtn;

    @FXML
    private TextArea textArea;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField serverIpField, usernameField, emailField;

    @FXML
    protected void onServerConnectClick() {
        boolean success = false;

        DB db = DatabaseHandler.getInstance().getDatabase();

        if(serverIpField.getText().length() < 1)
            DatabaseHandler.getInstance().setServer("localhost");
        else
            DatabaseHandler.getInstance().setServer(serverIpField.getText());

        if (DatabaseHandler.getInstance().isConnected())
            success = true;

        if (success) {
            serverConnectText.setText("successfully connected to server");

            loginBtn.setDisable(false);
            registerBtn.setDisable(false);
            emailField.setDisable(false);
            passwordField.setDisable(false);
            usernameField.setDisable(false);

            emailField.setPromptText("only for new users");
            usernameField.setPromptText("required");
            passwordField.setPromptText("required");

            serverIpField.setDisable(true);
            serverConnectBtn.setDisable(true);

            //serverConnectText.setTextFill(Color.color(0,255,0));
        } else {
            serverConnectText.setText("error connecting to server");
            //serverConnectText.setTextFill(Color.color(255,0,0));
        }
    }

    @FXML
    protected void onLoginClick() {

        String email, username, password;
        Integer credentialsCheck = 0;

        String name = "";

        email = emailField.getText();
        username = usernameField.getText();
        password = passwordField.getText();

        UserHandler.getInstance().getUser().setEmail(email);
        UserHandler.getInstance().getUser().setUsername(username);
        UserHandler.getInstance().getUser().setEmail(password);

        if (username.equals("")) {
            System.out.println("username can t be empty");
            serverConnectText.setText("username can't be empty");
            serverConnectText.setStyle("-fx-text-fill: #ff5959");
            usernameField.setStyle("-fx-background-color: #ff5959");
        } else if (password.equals("")) {
            System.out.println("password can t be empty");
            serverConnectText.setText("password can't be empty");
            serverConnectText.setStyle("-fx-text-fill: #ff5959");
            passwordField.setStyle("-fx-background-color: #ff5959");
        } else if (password.length() < 8 && password.length() > 0) {
            System.out.println("password minimum 8 characters");
            serverConnectText.setText("password minimum 8 characters");
            serverConnectText.setStyle("-fx-text-fill: #ff5959");
            passwordField.setStyle("-fx-background-color: #ff5959");
        } else {

            usernameField.setStyle("-fx-background-color: #FFFFFF");
            passwordField.setStyle("-fx-background-color: #FFFFFF");
            emailField.setStyle("-fx-background-color: #FFFFFF");

            DB db = DatabaseHandler.getInstance().getDatabase();
            DBCollection users = db.getCollection("Users");
            List<DBObject> result = users.find().toArray();

            for (DBObject object : result) { //this is hella broken pls fix
                System.out.println(object);
                //if(object.get("Username") != null)
                name = (String) object.get("Username");
                String pass = (String) object.get("Password");
                String mail = (String) object.get("Email");

                if (name.equals(username)) {
                    credentialsCheck++;
                    System.out.println(object);
                    if (pass.equals(password))
                        credentialsCheck += 2;
                }

                //System.out.println(name);
            }
        }

        System.out.println(credentialsCheck);

        if (credentialsCheck == 3) {
            System.out.println("successfully logged in!");
            DataRefreshHandler.getInstance().saveUserData();
            HelloApplication.loadMainScene();
        } else if (credentialsCheck == 1) {
            System.out.println("invalid password");
            serverConnectText.setText("invalid password");
            passwordField.setStyle("-fx-background-color: #ff5959");
            serverConnectText.setStyle("-fx-text-fill: #ff5959");
        } else if(credentialsCheck == 2){
            System.out.println("invalid username");
            serverConnectText.setText("invalid username");
            usernameField.setStyle("-fx-background-color: #ff5959");
            serverConnectText.setStyle("-fx-text-fill: #ff5959");
        }

    }

    @FXML
    protected void onRegisterClick() {

        String email, username, password;
        boolean usernameExists = false,registerSuccess = false;

        email = emailField.getText();
        username = usernameField.getText();
        password = passwordField.getText();

        UserHandler.getInstance().getUser().setEmail(email);
        UserHandler.getInstance().getUser().setUsername(username);
        UserHandler.getInstance().getUser().setEmail(password);

        if (username.equals("")) {
            System.out.println("username can t be empty");
        } else if (password.equals("")) {
            System.out.println("password can t be empty");
        } else if (password.length() < 8) {
            System.out.println("password minimum 8 characters");
        } else if (email.equals("")) {
            System.out.println("email can t be empty");
        } else {

            System.out.print("welcome,");
            System.out.println(UserHandler.getInstance().getUser().getUsername());

            DB db = DatabaseHandler.getInstance().getDatabase();
            DBCollection users = db.getCollection("Users");
            List<DBObject> result = users.find().toArray();
            for (DBObject object : result) {

                String name = (String) object.get("Username");
                if (name.equals(username))
                    usernameExists = true;
                //System.out.println(name);
            }


            if (usernameExists == true){
                registerSuccess = true;
            } else {

                DataRefreshHandler.getInstance().addNewUser(username,password,email);
                registerSuccess = true;

            }

            if(registerSuccess == true){
                emailField.setDisable(true);
                passwordField.setDisable(true);
                usernameField.setDisable(true);
                registerBtn.setDisable(true);

                String welct = "welcome, " + username;
                welcomeText.setText(welct);

                serverConnectText.setText("press login to proceed");

            } else {
                serverConnectText.setText("register error!");
            }

        }

    }

    @FXML
    protected void onHelloButtonClick() {
        //textArea.set
        textArea.setText("dyaftghjdacfvhfdxcgvhcfxdcgvfdxcgvfasfafsafafsafafsafafah");
    }

    @FXML
    public void initialize() {
        serverConnectBtn.setTranslateX(5);
        registerBtn.setTranslateX(2.5);
        loginBtn.setTranslateX(-2.5);

        serverIpField.setPromptText("default: localhost");
        serverIpField.setText("localhost");

        emailField.setDisable(true);
        passwordField.setDisable(true);
        usernameField.setDisable(true);
        loginBtn.setDisable(true);
        registerBtn.setDisable(true);

        serverConnectBtn.setText("connect");

        loginBtn.setText("login");
        registerBtn.setText("register");

        welcomeText.setText("Welcome to groups!");
        serverConnectText.setText("input server ip address:");
    }

}