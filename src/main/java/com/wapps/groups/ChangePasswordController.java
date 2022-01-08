package com.wapps.groups;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ChangePasswordController {

    @FXML
    PasswordField oldPasswordField, newPasswordField;

    @FXML
    Label oldPasswordLbl, newPasswordLbl;

    @FXML
    void onBackPress(){
        HelloApplication.loadSettingsScene();
    }

    @FXML
    void onApplyPress(){

        if(newPasswordField.getText().length() > 7) {

            String userPass = UserHandler.getInstance().getUser().getPassword();
            if (oldPasswordField.getText().equals(userPass)) {
                DataRefreshHandler.getInstance().setPassword(newPasswordField.getText());
                UserHandler.getInstance().getUser().setPassword(newPasswordField.getText());
                DataRefreshHandler.getInstance().saveUserDataInverted();

                newPasswordField.setStyle("-fx-background-color: #7dff7d");
                oldPasswordField.setStyle("-fx-background-color: #7dff7d");
                newPasswordLbl.setStyle("-fx-background-color: #7dff7d");
                newPasswordLbl.setText("successfully changed password!");
                newPasswordLbl.setLayoutX(30);
            }
            else {
                oldPasswordLbl.setText("incorrect password");
                oldPasswordField.setStyle("-fx-background-color: #ff5959");
                oldPasswordLbl.setStyle("-fx-background-color: #ff5959");
            }
        } else {
            newPasswordField.setStyle("-fx-background-color: #ff5959");
            newPasswordLbl.setStyle("-fx-background-color: #ff5959");
            newPasswordLbl.setText("password too short");
        }
    }


}
