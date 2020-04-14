package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.entity.UserEntity;
import java.io.IOException;

public class AuthenticationController {

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Label statusText;

    private DomainHandler domainHandler = new DomainHandler();

    /**
     * Opens the login page and waits until the user have logged in or cancel
     * @return currentUser
     */
    public UserEntity openLoginStage(ActionEvent event)
    {
        var loginStage = new Stage();

        try {
            loginStage.setScene(new Scene(App.getLoader("authentication").load()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        loginStage.getIcons().add(new Image(App.class.getResourceAsStream("loginImages/lockIcon.jpg")));
        loginStage.setTitle("Authentication");
        loginStage.initModality(Modality.WINDOW_MODAL);
        loginStage.initOwner(((Node)event.getTarget()).getScene().getWindow());
        loginStage.setResizable(false);
        loginStage.showAndWait();
        return CurrentUser.getInstance().getUserEntity();
    }


    @FXML
    public void login(ActionEvent actionEvent) throws IOException
    {
        if (usernameField.getText().isEmpty())
        {
            setStatusText("Username is empty");
            return;
        }
        else if (passwordField.getText().isEmpty())
        {
            setStatusText("Password is empty");
            return;
        }

        var user = domainHandler.authentication().login(usernameField.getText(), passwordField.getText());

        if (user != null)
        {
            //sets currentUser by init the user that was given from the login method
            CurrentUser.getInstance().init(user);
            closeDialog(actionEvent);
        }
        else
        {
            //adds style to the textfield to show the user that the input was wrong
            passwordField.getStyleClass().add("wrong-credentials");
            usernameField.getStyleClass().add("wrong-credentials");

            setStatusText("Username or password was not correct Please try again");
        }
    }

    @FXML
    public void cancel(ActionEvent event)
    {
        closeDialog(event);
    }

    private void setStatusText(String text)
    {
        statusText.setText(text);
        statusText.setVisible(true);
    }

    /**
     * closes the last opened stage
     */
    private void closeDialog(ActionEvent event)
    {
        //gets the node of the given event
        Node source = (Node)  event.getSource();
        //gets that nodes stage
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
