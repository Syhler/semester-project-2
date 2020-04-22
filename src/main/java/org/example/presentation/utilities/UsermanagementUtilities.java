package org.example.presentation.utilities;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.example.domain.buisnessComponents.Company;
import org.example.domain.buisnessComponents.Role;
import org.example.presentation.multipleLanguages.LanguageHandler;

public class UsermanagementUtilities {

    // Cell factory for createUser, usermanagementTable, updateUser

    /**
     * Call to UsermangementUtilities to get cellfactory
     *
     * @return Callback for cellfactory
     */
    public static Callback<ListView<Company>, ListCell<Company>> cellFactoryUserManagement()
    {
        return new Callback<>() {

            @Override
            public ListCell<Company> call(ListView<Company> l) {
                return new ListCell<Company>() {

                    @Override
                    protected void updateItem(Company item, boolean empty) {
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

    public static String formValidation(String firstName, String lastName, String email, Company company, String title, String password){
        String msgToReturn = null;

        msgToReturn = formValidation(firstName, lastName, email, company, title);

        if (password.isEmpty()) {
            msgToReturn = (LanguageHandler.getText("passwordEmpty"));
        }

        return msgToReturn;
    }

    public static String formValidation(String firstName, String lastName, String email, Company company, String title){
        String msgToReturn = null;

        if (firstName.isEmpty()) {
            msgToReturn = (LanguageHandler.getText("firstnameEmpty"));
        } else if (lastName.isEmpty()) {
            msgToReturn = (LanguageHandler.getText("lastnameEmpty"));
        } else if (email.isEmpty()) {
            msgToReturn = (LanguageHandler.getText("emailEmpty"));
        } else if (company == null) {
            msgToReturn = (LanguageHandler.getText("companyEmpty"));
        } else if (title.isEmpty()) {
            msgToReturn = (LanguageHandler.getText("titleEmpty"));
        }

        return msgToReturn;
    }

}
