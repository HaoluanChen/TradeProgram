package frontend.userGUI.listeners;

import frontend.userGUI.presenters.UserPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import use_cases.MessageBuilder;
import use_cases.UserManager;

import java.net.URL;
import java.util.ResourceBundle;

public class PrivateMessageMenu implements Initializable {

    @FXML private TextField usernameInput;
    @FXML private TextArea messageInput;
    @FXML private Label usernamePrompt;
    @FXML private Label messagePrompt;
    @FXML private Label usernameError;
    @FXML private Button send;
    @FXML private Button cancel;
    @FXML private Label confirmation;

    private UserPresenter userPresenter;
    private MessageBuilder messageBuilder;
    private UserManager userManager;
    private String currUser;

    /**
     * Constructs a new PrivateMessageMenu listener
     * @param userManager the UserManager object
     * @param currUser the currently logged in user
     */
    public PrivateMessageMenu(UserManager userManager, String currUser) {
        this.userManager = userManager;
        this.userPresenter = new UserPresenter();
        this.messageBuilder = new MessageBuilder();
        this.currUser = currUser;
    }

    /**
     * Set up button functionality/text
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set text
        this.usernamePrompt.setText(this.userPresenter.userMessagePrompt());
        this.messagePrompt.setText(this.userPresenter.userMessagePromptSecundus());
        this.send.setText(this.userPresenter.userLoanPromptConfirm());
        this.cancel.setText(this.userPresenter.userLoanPromptCancel());

        // set button functionality
        this.send.setOnAction(e -> sendMessage());
        this.cancel.setOnAction(this::returnToMenu);
    }

    /**
     * Sends a private message
     */
    private void sendMessage() {
        String user = this.usernameInput.getText();
        // if either username/description not filled out
        if(this.usernameInput.getLength() == 0 || this.messageInput.getLength() == 0) {
            this.usernameError.setVisible(true);
            this.confirmation.setVisible(false);
            this.usernameError.setText(this.userPresenter.invalidMessageInput());
        }
        // if the username is not in the system and if the user is sending a message to themselves
        else if(!this.userManager.isValidUser(user) && !user.equals(this.currUser)) {
            this.usernameError.setVisible(true);
            this.confirmation.setVisible(false);
            this.usernameError.setText(this.userPresenter.invalidUsername());
        }
        // otherwise
        else {
            this.userManager.addUserMessage(user,
                    this.messageBuilder.getPrivateMessage(this.messageInput.getText(), this.currUser));
            this.usernameError.setVisible(false);
            this.confirmation.setVisible(true);
            this.confirmation.setText(this.userPresenter.userPrivateMessageConfirmation());
        }
    }

    /**
     * Return to main menu
     * @param actionEvent the ActionEvent object
     */
    private void returnToMenu(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
