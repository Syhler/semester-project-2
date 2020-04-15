package org.example.presentation.utilities;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.util.Callback;
import org.example.entity.CompanyEntity;
import org.example.entity.Role;
import org.example.presentation.multipleLanguages.LanguageHandler;

public class UsermanagementUtilities {

    // Cell factory for createUser, usermanagementTable, updateUser

    public static Callback<ListView<CompanyEntity>, ListCell<CompanyEntity>>  cellFactoryUsermanagemnt()
    {
        return new Callback<>() {

            @Override
            public ListCell<CompanyEntity> call(ListView<CompanyEntity> l) {
                return new ListCell<CompanyEntity>() {

                    @Override
                    protected void updateItem(CompanyEntity item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setDisable(false);
                            setGraphic(null);
                            setText("");
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        };
    }

    public static String setStageTitleCreateUpdateUser(Role role){

        String rolename = "";

        switch (role) {
            case Admin:
                rolename = LanguageHandler.getText("admin");
                break;
            case Manufacture:
                rolename = LanguageHandler.getText("manufacture");
                break;
            case Producer:
                rolename = LanguageHandler.getText("producer");
                break;
            case Actor:
                rolename = LanguageHandler.getText("actor");
                break;
        }
        return rolename;
    }

    public static String formValidation(String firstname, String lastname, String email, CompanyEntity company, String title, String password){
        String msgToReturn = "";

        if (firstname.isEmpty()) {
            msgToReturn = (LanguageHandler.getText("firstnameEmpty"));
        } else if (lastname.isEmpty()) {
            msgToReturn = (LanguageHandler.getText("lastnameEmpty"));
        } else if (email.isEmpty()) {
            msgToReturn = (LanguageHandler.getText("emailEmpty"));
        } else if (company == null) {
            msgToReturn = (LanguageHandler.getText("companyEmpty"));
        } else if (title.isEmpty()) {
            msgToReturn = (LanguageHandler.getText("titleEmpty"));
        } else if (password.isEmpty()) {
            msgToReturn = (LanguageHandler.getText("passwordEmpty"));
        }
        return msgToReturn;
    }
}
