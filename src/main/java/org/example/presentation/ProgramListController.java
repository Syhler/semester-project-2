package org.example.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;
import org.example.domain.DomainHandler;
import org.example.entity.ProgramEntity;
import org.example.entity.UserEntity;

public class ProgramListController implements Initializable {
    private DomainHandler domainHandler;



    public Node openView() {
        FXMLLoader loader = null;
        try {
            loader = App.getLoader("programList");
            Parent node = loader.load();
            return node;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TEST ikke sikkert dette virker som det skal...
    public Node showProgramList()
    {
        FXMLLoader loader = null;
        try {
            loader = App.getLoader("program");
            Parent node = loader.load();
            return node;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}