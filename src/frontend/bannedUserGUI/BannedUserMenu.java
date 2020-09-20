package frontend.bannedUserGUI;

import entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import use_cases.AdminManager;
import use_cases.MessageBuilder;

public class BannedUserMenu implements Initializable {

    private String currUser;
    private AdminManager adminManager;
    private BannedUserPresenter bannedUserPresenter;
    private MessageBuilder messageBuilder;

    @FXML private Button confirm;
    @FXML private Button cancel;
    @FXML private Label bannedMenuPrompt;
    @FXML private Label confirmationMessage;

    /**
     * Sets up a BannedUserMenu listener.
     * @param currUser the String current user
     * @param adminManager the AdminManager object
     */
    public BannedUserMenu(String currUser, AdminManager adminManager) {
        this.currUser = currUser;
        this.adminManager = adminManager;
        this.bannedUserPresenter = new BannedUserPresenter();
        this.messageBuilder = new MessageBuilder();
    }

    /**
     * Sets up button functionality/labels
     * @param location ¯\_(ツ)_/¯
     * @param resources ¯\_(ツ)_/¯
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set text
        this.confirm.setText(this.bannedUserPresenter.unbanConfirm());
        this.cancel.setText(this.bannedUserPresenter.unbanCancel());
        this.bannedMenuPrompt.setText(this.bannedUserPresenter.unbanPrompt());

        // set functionality
        this.confirm.setOnAction(e -> confirm());
        this.cancel.setOnAction(this::returnToMainMenu);
    }

    /**
     * Send the unban request
     */
    private void confirm() {
        List<Message> adminMessages = this.adminManager.getAdminMessages();
        adminMessages.add(this.messageBuilder.getUnbanRequest("Please unban user: " +
                this.currUser + ". They are very sorry :(", this.currUser));
        this.adminManager.setAdminMessages(adminMessages);
        this.confirmationMessage.setText(this.bannedUserPresenter.unbanRequestSent());
    }

    /**
     * Return to main menu (closes this banned user listener)
     * @param actionEvent the ActionEvent object
     */
    private void returnToMainMenu(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
