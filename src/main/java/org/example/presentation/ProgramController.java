package org.example.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.domain.Authentication;
import org.example.domain.Credit;
import org.example.domain.DomainHandler;
import org.example.entity.CompanyEntity;
import org.example.entity.CreditEntity;
import org.example.entity.ProgramEntity;
import org.example.entity.UserEntity;
import org.example.presentation.multipleLanguages.LanguageHandler;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ProgramController implements Initializable {
    public Button showMore;
    public Button updateProgram;
    public Button deleteProgram;
    public Label title;
    public VBox program;
    public Label description;
    public Label titleHeader;
    public Label descriptionHeader;
    public VBox buttonHolder;
    private DomainHandler domainHandler;
    public ProgramEntity programEntity;






    @FXML
    private void goToCredit() throws IOException {
    }

    @FXML
    private void createProgram() throws IOException {
    }



    @FXML
    private void deleteProgram() throws IOException {
    }

    @FXML
    private void updateProgram() throws IOException {
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showMore.setText(LanguageHandler.getText("showMore"));
        updateProgram.setText(LanguageHandler.getText("updateProgram"));
        deleteProgram.setText(LanguageHandler.getText("deleteProgram"));
        titleHeader.setText(LanguageHandler.getText("titleHeader"));
        descriptionHeader.setText(LanguageHandler.getText("descriptionHeader"));
        /*createProgTitle.setText(LanguageHandler.getText("titleHeader"));
        createProgDescription.setText(LanguageHandler.getText("descriptionHeader"));
        createProgCompany.setText(LanguageHandler.getText("createProgCompany"));
        createProgProducer.setText(LanguageHandler.getText("createProgProducer"));*/
    }
}
