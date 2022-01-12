package com.wapps.groups;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SettingsController {

    @FXML
    Button changeBioBtn, changePasswordBtn;

    @FXML
    Label loggedInLabel, emailLabel, titleLabel;

    @FXML
    TextArea bioArea;

    public void backButtonPress(){

        HelloApplication.loadMainScene();

    }

    public void logOutButtonPress(){

        MyUserHandler.getInstance().getUser().setUsername("new");
        MyUserHandler.getInstance().getUser().setPassword("new");
        MyUserHandler.getInstance().getUser().setEmail("new");
        HelloApplication.loadHelloScene();

        DataRefreshHandler.getInstance().saveUserData();

    }

    public void changePasswordButtonPress(){

        HelloApplication.loadChangePasswordScene();

    }

    public void changeBioButtonPress(){

        String newBio = bioArea.getText();
        DataRefreshHandler.getInstance().setBio(newBio);

    }

    @FXML
    public void initialize(){

        String bio = "";
        bio = DataRefreshHandler.getInstance().getBio();
        bioArea.setText(bio);
        bioArea.setWrapText(true);

        changeBioBtn.setLayoutX(52);
        changePasswordBtn.setLayoutY(260);
        changePasswordBtn.setLayoutX(30);

        loggedInLabel.setTranslateX(-8);
        emailLabel.setTranslateX(-8);

        //String email = "";
        //String user = MyUserHandler.getInstance().getUser().getUsername();
        //email = DataRefreshHandler.getInstance().getEmailForUser(user);
        //System.out.println(email);
        String email = DataRefreshHandler.getInstance().getEmailForUser(MyUserHandler.getInstance().getUser().getUsername());
        emailLabel.setText(email);

        loggedInLabel.setText("logged in as: "+ MyUserHandler.getInstance().getUser().getUsername());
        //String emailLabelText="";
        //emailLabel.setText(emailLabelText);

        //titleLabel.setStyle("-fx-text-fill: #FFFFFF");

    }

}
