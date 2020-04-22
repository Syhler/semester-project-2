package org.example.presentation.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.DomainFacade;
import org.example.domain.User;
import org.example.presentation.multipleLanguages.LanguageHandler;
import org.example.presentation.utilities.CurrentUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthenticationController implements Initializable {

    @FXML
    private Button btnLogin;
    @FXML
    private Button btnCancel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Label statusText;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;

    private DomainFacade domainHandler = new DomainFacade();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(LanguageHandler.getText("usernameLabel"));
        passwordLabel.setText(LanguageHandler.getText("passwordLabel"));
        btnCancel.setText(LanguageHandler.getText("cancel"));
        btnLogin.setText(LanguageHandler.getText("login"));
    }

    /**
     * Opens the login page and waits until the user have logged in or cancel
     * @return currentUser
     */
    public User openLoginStage(ActionEvent event)
    {
        var loginStage = new Stage();

        try {
            loginStage.setScene(new Scene(App.getLoader("authentication").load()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        loginStage.getIcons().add(new Image(App.class.getResourceAsStream("loginImages/tv2trans.png")));
        loginStage.setTitle(LanguageHandler.getText("Authentication"));
        loginStage.initModality(Modality.WINDOW_MODAL);
        loginStage.initOwner(((Node)event.getTarget()).getScene().getWindow());
        loginStage.setResizable(false);
        loginStage.showAndWait();
        return CurrentUser.getInstance().getUser();
    }


    @FXML
    public void login(ActionEvent actionEvent) throws IOException
    {
        if (usernameField.getText().isEmpty())
        {
            setStatusText(LanguageHandler.getText("usernameEmpty"));
            return;
        }
        else if (passwordField.getText().isEmpty())
        {
            setStatusText(LanguageHandler.getText("passwordEmpty"));
            return;
        }

        var user = domainHandler.getAuthentication().login(usernameField.getText(), passwordField.getText());

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

            setStatusText(LanguageHandler.getText("usernameOrPassword"));
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
