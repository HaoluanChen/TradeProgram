package frontend.adminGUI.listeners;

import entities.Admin;
import exceptions.InvalidUsernameException;
import frontend.adminGUI.presenters.AdminGUIPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import use_cases.AdminManager;
import use_cases.UserManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminNewAdminController implements Initializable {
    @FXML private Label resultOfCreationLabel;
    @FXML private Button addNewAdminButton;
    @FXML private Button exitButton;
    @FXML private TextField newAdminUserNameTextField;
    @FXML private TextField newAdminPasswordTextField;

    private Admin admin;

    private AdminGUIPresenter adminGUIPresenter;

    private UserManager userManager;

    private AdminManager adminManager;
    /**
     * Class constructor.
     * Create a new AdminSystem that allows admins to create a new admin account.
     * @param admin the admin of the currently logged in.
     * @param adminManager the AdminManager will be used to change account information
     * @param userManager the UserManager will be used to change user account information
     */

    AdminNewAdminController(Admin admin, AdminManager adminManager, UserManager userManager){
        this.admin = admin;
        adminGUIPresenter = new AdminGUIPresenter();
        this.adminManager = adminManager;
        this.userManager = userManager;
    }
    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitButton.setText(adminGUIPresenter.exitButton());
        exitButton.setOnAction(this::close);
        addNewAdminButton.setText(adminGUIPresenter.newAdminButton());
        addNewAdminButton.setOnAction(this::addNewAdminButtonPushed);

    }
    private void close(ActionEvent actionEvent){
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
    private void addNewAdminButtonPushed(ActionEvent actionEvent){

        String newUsername = newAdminUserNameTextField.getText();

        String newPassword = newAdminPasswordTextField.getText();
        if (newUsername.equals("")){
            resultOfCreationLabel.setText(adminGUIPresenter.userNameCannotBeEmpty());

        }
        else if(newPassword.equals("")){
            resultOfCreationLabel.setText(adminGUIPresenter.passwordCannotBeEmpty());
        }
        else if(userManager.isValidUser(newUsername)){
            resultOfCreationLabel.setText(adminGUIPresenter.AdminCreationFailed());
        }
        else {
            try {
                adminManager.addAdmin(newUsername, newPassword);
                resultOfCreationLabel.setText(adminGUIPresenter.newAdminCreated());
            }
            catch (InvalidUsernameException e) {
                resultOfCreationLabel.setText(adminGUIPresenter.AdminCreationFailed());
            }
        }


    }
}
