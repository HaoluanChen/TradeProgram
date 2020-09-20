package frontend.adminGUI.listeners;

import entities.Admin;
import frontend.adminGUI.presenters.AdminGUIPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import use_cases.AdminManager;
import use_cases.UserManager;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class AdminAccountController implements Initializable {
    @FXML private Button changePassWordButton;
    @FXML private Button adminCreationButton;
    @FXML private Button exitButton;

    private String NewPasswordFXML = "/frontend/adminGUI/fxml_files/AdminChangePassword.fxml";
    private String NewAdminFXML = "/frontend/adminGUI/fxml_files/AdminCreation.fxml";




    private Admin admin;

    private AdminGUIPresenter adminGUIPresenter;

    private UserManager userManager;

    private AdminManager adminManager;


    /**
     * Class constructor.
     * Create a new AdminAccountSystem that controls and allows the admin to manage Admin Accounts
     * @param admin the admin of the currently logged in.
     * @param adminManager the AdminManager will be used to change account information

     * @param userManager the UserManager used to check account information
     */


    AdminAccountController(Admin admin, AdminManager adminManager,
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


    private void ChangePassWordButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinHeight(400);
        window.setTitle(adminGUIPresenter.adminPasswordChangeWindow());
        window.setMinWidth(600);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(NewPasswordFXML));
        loader.setController(new AdminNewPasswordController(admin, adminManager, userManager));
        window.initStyle(StageStyle.UNDECORATED);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        window.setScene(scene);

        window.show();



    }
    private void AdminCreationButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(adminGUIPresenter.adminAccountCreationWindow());
        window.setMinWidth(600);
        window.setMinHeight(400);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(NewAdminFXML));
        window.initStyle(StageStyle.UNDECORATED);

        loader.setController(new AdminNewAdminController(admin, adminManager, userManager));

        Parent parent = loader.load();
        Scene scene = new Scene(parent);



        window.setScene(scene);


        window.show();


    }
    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changePassWordButton.setText(adminGUIPresenter.adminChangePasswordButton());
        adminCreationButton.setText(adminGUIPresenter.adminCreationButtonText());
        exitButton.setText(adminGUIPresenter.exitButton());
        exitButton.setOnAction(this::close);
        changePassWordButton.setOnAction(e -> {
            try {
                ChangePassWordButtonPushed(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        adminCreationButton.setOnAction(e -> {
            try {
                AdminCreationButtonPushed(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });



    }
}
