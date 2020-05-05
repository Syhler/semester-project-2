package org.example.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import org.example.App;
import org.example.domain.applicationFacade.DomainFacade;
import org.example.domain.buisnessComponents.Program;
import org.example.domain.buisnessComponents.Role;
import org.example.domain.buisnessComponents.User;
import org.example.presentation.login.AuthenticationController;
import org.example.presentation.multipleLanguages.Language;
import org.example.presentation.multipleLanguages.LanguageHandler;
import org.example.presentation.multipleLanguages.LanguageModel;
import org.example.presentation.multipleLanguages.LanguageSelector;
import org.example.presentation.program.CreateProgramController;
import org.example.presentation.program.ProgramListController;
import org.example.presentation.usermangement.UpdateUserController;
import org.example.presentation.utilities.ControllerUtility;
import org.example.presentation.utilities.CurrentUser;

public class DefaultController implements Initializable
{
    @FXML
    public ComboBox<LanguageModel> selectLanguage;
    @FXML
    private ToggleButton login;
    @FXML
    private ToggleButton createProgram;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField searchBar;
    @FXML
    private ToolBar navigation;
    @FXML
    private ToggleButton importBtn;
    @FXML
    private ToggleButton usermanagementBtn;
    @FXML
    private ToggleButton profileNavigation;
    @FXML
    private ToggleButton searchNavigation;

    private int sprog;

    private DomainFacade domainHandler = new DomainFacade();
    private ProgramListController programListController;




    /**
     * Opens AuthenticationController for login, closes if login was succesfull
     * @param event
     */
    @FXML
    private void goToLogin(ActionEvent event) {
        AuthenticationController authenticationController = new AuthenticationController();
        var userEntity = authenticationController.openLoginStage(event);
        login.setSelected(false);
        if (userEntity != null) {
            login.setText(LanguageHandler.getText("logoff"));
            login.setOnAction(this::logout);
            profileNavigation.setVisible(true);


            if (userEntity.getRole() != Role.Actor) {
                usermanagementBtn.setVisible(true);
                createProgram.setVisible(true);
            }

            if (userEntity.getRole() == Role.Admin)
            {
                importBtn.setVisible(true);
            }
        }
    }

    /**
     * Logs out the current user sets CurrentUser to null
     * @param event
     * @throws IOException
     */
    private void logout(ActionEvent event) {
        if (CurrentUser.getInstance().getUser() != null) {
            CurrentUser.getInstance().init(null); //Logs off
            login.setText(LanguageHandler.getText("login"));
            login.setOnAction(this::goToLogin);
            login.setSelected(false);


            usermanagementBtn.setVisible(false);
            profileNavigation.setVisible(false);
            createProgram.setVisible(false);
            importBtn.setVisible(false);
        }
    }

    /**
     * On the click of a button, opens the scene to create a program
     * @param event
     * @throws IOException
     */
    @FXML
    private void goToCreateProgram(ActionEvent event) {

        CreateProgramController createProgramController = new CreateProgramController();
        createProgram.setSelected(false);
        Program programEntity = createProgramController.openView(event);
        if (programEntity != null)
        {
            programListController.listOfPrograms.add(programEntity);
            programListController.updateProgramList();
        }
    }

    public void importOnAction(ActionEvent event) throws Exception {

        ControllerUtility.importProgram(event);

        importBtn.setSelected(false);

    }


    @FXML
    public void searchOnKeyPressed(KeyEvent keyEvent) throws Exception {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
        {
            var programs = domainHandler.search(searchBar.getText());
            setImages(programs);
            ProgramListController.getInstance().listOfPrograms = programs;
            ProgramListController.getInstance().updateProgramList();
        }
    }

    /**
     * Loads the "programList.fxml" scene in the center of the borderpane
     */
    public void loadProgramList()
    {
        FXMLLoader loader = null;
        try {
            loader = App.getLoader("programList");
            Parent node = loader.load();
            programListController = loader.getController();
            borderPane.setCenter(node);


            var allPrograms = domainHandler.getAllPrograms();
            setImages(allPrograms);

            programListController.listOfPrograms.addAll(allPrograms);
            programListController.updateProgramList();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setImages(List<Program> programs)
    {
        var listOfImages = new ArrayList<Image>();
        listOfImages.add(new Image(App.class.getResourceAsStream("loginImages/kim.jpg")));
        listOfImages.add(new Image(App.class.getResourceAsStream("loginImages/mænd.jpg")));
        listOfImages.add(new Image(App.class.getResourceAsStream("loginImages/nah.jpg")));
        listOfImages.add(new Image(App.class.getResourceAsStream("loginImages/dal.jpg")));
        listOfImages.add(new Image(App.class.getResourceAsStream("loginImages/3.jpg")));

        for (var program : programs) {
            program.setImage(listOfImages.get(new Random().nextInt(listOfImages.size())));
        }
    }

    @FXML
    private void goToUserManagement() throws IOException {
        App.setRoot("usermanagement");
    }

    /**
     * Opens updateuser so user can update his/her own interformation only
     * @param event
     */
    @FXML
    private void openUpdateUser(ActionEvent event) {
        User userToUpdate = CurrentUser.getInstance().getUser();
        UpdateUserController updateUserController = new UpdateUserController();
        User user = updateUserController.openUpdateUser(event, userToUpdate);
        profileNavigation.setSelected(false);
        if (user != null) {
            CurrentUser.getInstance().init(user);

        }
    }

    /**
     * Adds pictures and text to the ComboBox, so the user knows which languages they can choose
     */
    public void chooseLanguage()
    {
        LanguageModel danish = new LanguageModel(new Image(App.class.getResourceAsStream("loginImages/danishFlag.png")), Language.Danish);
        LanguageModel english = new LanguageModel(new Image(App.class.getResourceAsStream("loginImages/englishFlag.png")), Language.English);
        LanguageModel swedish = new LanguageModel(new Image(App.class.getResourceAsStream("loginImages/swedishFlag.png")), Language.Swedish);
        LanguageModel norway = new LanguageModel(new Image(App.class.getResourceAsStream("loginImages/norwayFlag.png")), Language.Norwegian);
        LanguageModel russian = new LanguageModel(new Image(App.class.getResourceAsStream("loginImages/russianFlag.jpg")), Language.Russian);

        ObservableList<LanguageModel> options = FXCollections.observableArrayList();
        options.addAll(danish, english, swedish, norway, russian);
        selectLanguage.setItems(options);
        selectLanguage.setCellFactory(c -> new LanguageSelector());
        selectLanguage.setButtonCell(new LanguageSelector());
    }

    /**
     * Changes language of the different objects in the system.
     */
    public void setLanguage()
    {
        selectLanguage.valueProperty().addListener(new ChangeListener<LanguageModel>() {
            @Override
            public void changed(ObservableValue<? extends LanguageModel> observableValue, LanguageModel languageModel, LanguageModel t1) {
                if (t1 != null && t1.getLanguage() != null) {
                    LanguageHandler.initLanguage(t1.getLanguage());
                    setButtonLanguage();
                }
            }
        });
    }

    /**
     * Sets language of the buttons
     */
    private void setButtonLanguage()
    {
        login.setText(LanguageHandler.getText("login"));
        profileNavigation.setText(LanguageHandler.getText("profile"));
        usermanagementBtn.setText(LanguageHandler.getText("usermanagementBtn"));
        createProgram.setText(LanguageHandler.getText("createProgram"));
        searchBar.setPromptText(LanguageHandler.getText("searchBarPrompt"));
        searchNavigation.setText(LanguageHandler.getText("searchNavigation"));
        importBtn.setText(LanguageHandler.getText("import"));
    }

    /**
     * Sets the selectLanguage ComboBox to the current language
     */
    private void currentLanguage()
    {
        for (int i = 0; i < selectLanguage.getItems().size(); i++) {

            if (selectLanguage.getItems().get(i).getLanguage() == LanguageHandler.getLanguage())
            {
                selectLanguage.getSelectionModel().select(i);
                break;
            }
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setLanguage();
        chooseLanguage();
        currentLanguage();

        /**
         * Language
         */
        setButtonLanguage();

        navigation.prefWidthProperty().bind(borderPane.widthProperty());
        searchNavigation.setSelected(true);

        if (CurrentUser.getInstance().getUser() != null) {
            login.setText(LanguageHandler.getText("logoff"));
            login.setOnAction(this::logout);
            profileNavigation.setVisible(true);

            if (CurrentUser.getInstance().getUser().getRole() != Role.Actor) {
                usermanagementBtn.setVisible(true);
                createProgram.setVisible(true);
            }

            if (CurrentUser.getInstance().getUser().getRole() == Role.Admin)
            {
                importBtn.setVisible(true);
            }
        }

        //Platform.runLater(this::loadProgramList);
    }



}
