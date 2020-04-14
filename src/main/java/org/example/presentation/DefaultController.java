package org.example.presentation;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.entity.UserEntity;
import org.example.presentation.multipleLanguages.Language;
import org.example.presentation.multipleLanguages.LanguageHandler;

public class DefaultController {

    public Button login;

    @FXML
    public Button usermanagementBtn;
    @FXML
    private Button profileNavigation;

    @FXML
    public void initialize() {

        /**
         * Language
         */
        login.setText(LanguageHandler.getText("login"));
        profileNavigation.setText(LanguageHandler.getText("profile"));
        usermanagementBtn.setText(LanguageHandler.getText("usermanagementBtn"));

        if (CurrentUser.getInstance().getUserEntity() != null) {
            login.setText(LanguageHandler.getText("logoff"));
            login.setOnAction(this::logout);
            profileNavigation.setVisible(true);

            if (CurrentUser.getInstance().getUserEntity().getRole().getValue() < 4) {
                usermanagementBtn.setVisible(true);
            }
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        AuthenticationController authenticationController = new AuthenticationController();
        var userEntity = authenticationController.openLoginStage(event);

        if (userEntity != null) {
            login.setText(LanguageHandler.getText("logoff"));
            login.setOnAction(this::logout);
            profileNavigation.setVisible(true);

            if (userEntity.getRole().getValue() < 4) {
                usermanagementBtn.setVisible(true);
            }
        }
    }

    private void logout(ActionEvent event) {
        if (CurrentUser.getInstance().getUserEntity() != null) {
            CurrentUser.getInstance().init(null); //Logs off
            login.setText(LanguageHandler.getText("login"));
            login.setOnAction(this::goToLogin);

            usermanagementBtn.setVisible(false);
            profileNavigation.setVisible(false);
        }
    }

    @FXML
    private void goToSearch() throws IOException {

    }

    @FXML
    private void goToProgram() throws IOException {

    }

    @FXML
    private void goToUsermanagement() throws IOException {
        App.setRoot("usermanagement");
    }

    @FXML
    private void openUpdateUser(ActionEvent event) {
        UserEntity userToUpdate = CurrentUser.getInstance().getUserEntity();
        UpdateUserController updateUserController = new UpdateUserController();
        UserEntity user = updateUserController.openUpdateUser(event, userToUpdate, userToUpdate.getRole().getValue());

        if (user != null) {
            CurrentUser.getInstance().init(user);
        }

    }
}
