package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.App;
import org.example.entity.UserEntity;
import org.example.presentation.multipleLanguages.Language;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteController  implements Initializable {
    public Label deleteWarningText;
    public Button yesBtn;
    public Button noBtn;

    //Isn't currently used

    public void openView() {
        FXMLLoader loader = null;
        try {
            loader = App.getLoader("delete");
            Parent node = loader.load();

            Scene scene = new Scene(node);
            Stage stage = new Stage();
            stage.setTitle(LanguageHandler.getText("deleteProgramStageTitle"));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteProgramYes(ActionEvent event) throws IOException
    {
        //programInformationController.deleteProgram(event);
        ControllerUtility.closeProgram(event);
    }

    @FXML
    public void deleteProgramNo(ActionEvent event) throws IOException
    {
        ControllerUtility.closeProgram(event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deleteWarningText.setText(LanguageHandler.getText("deleteWarningText"));
        yesBtn.setText(LanguageHandler.getText("yes"));
        noBtn.setText(LanguageHandler.getText("no"));
    }
}
