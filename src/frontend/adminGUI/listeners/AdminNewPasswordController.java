package frontend.adminGUI.listeners;

import entities.Admin;
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


public class AdminNewPasswordController implements Initializable {
    @FXML
    private Label resultOfPasswordChangeLabel;
    @FXML private Button addNewPasswordButton;
    @FXML private TextField newPasswordTextField;
    @FXML private TextField confirmNewPasswordField;
    @FXML private Button exitButton;
    private Admin admin;

    private AdminGUIPresenter adminGUIPresenter;

    private UserManager userManager;

    private AdminManager adminManager;
    /**
     * Class constructor.
     * Create a new AdminSystem that allows admins to change their password.
     * @param admin the admin of the currently logged in.
     * @param adminManager the AdminManager will be used to change account information
     * @param userManager the UserManager will be used to change user account information
     */
    AdminNewPasswordController(Admin admin, AdminManager adminManager,
                           UserManager userManager){
        this.admin = admin;
        adminGUIPresenter = new AdminGUIPresenter();
        this.adminManager = adminManager;
        this.userManager = userManager;



    }
    private void close(ActionEvent actionEvent){
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
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
        addNewPasswordButton.setText(adminGUIPresenter.saveChangeButtonText());
        addNewPasswordButton.setOnAction(this::addNewPasswordButtonPushed);


    }
    private void addNewPasswordButtonPushed(ActionEvent actionEvent){

        String password1 = newPasswordTextField.getText();

        String password2 = confirmNewPasswordField.getText();

        if(password1.equals("")){
            resultOfPasswordChangeLabel.setText(adminGUIPresenter.passwordCannotBeEmpty());
        }
        else if (password2.equals("")){
            resultOfPasswordChangeLabel.setText(adminGUIPresenter.newPasswordNotSaved());
        }
        else if (adminManager.addNewPassWord(password1,password2, admin)){
            resultOfPasswordChangeLabel.setText(adminGUIPresenter.newPasswordCreated(admin));
        }
        else {
            resultOfPasswordChangeLabel.setText(adminGUIPresenter.newPasswordNotSaved());
        }
}}
