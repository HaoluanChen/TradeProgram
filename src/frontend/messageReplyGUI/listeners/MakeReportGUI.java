package frontend.messageReplyGUI.listeners;

import entities.Message;
import frontend.messageReplyGUI.presenters.MessageReplyPresenter;
import frontend.popUp.PopUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import use_cases.AdminManager;
import use_cases.MessageBuilder;

import java.net.URL;
import java.util.ResourceBundle;

public class MakeReportGUI implements Initializable {
    @FXML private Label title;
    @FXML private Label messageContent;
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private TextArea userInput;

    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private Message message;
    private AdminManager adminManager;
    private String accountName;

    /**
     * Class constructor.
     * Create a new MessageReplyPresenter that allows the user to make a report
     * @param message the message
     * @param adminManager the admin manager of the system
     * @param accountName the username of the current user using the system
     */
    MakeReportGUI(Message message, AdminManager adminManager, String accountName){
        this.message = message;
        this.adminManager = adminManager;
        this.accountName = accountName;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(messageReplyPresenter.reportPrompt());
        messageContent.setText(messageReplyPresenter.messageContent(message));
        button1.setText(messageReplyPresenter.exit());
        button1.setOnAction(this::exit);
        button2.setText(messageReplyPresenter.report());
        button2.setOnAction(this::makeReport);
    }

    private void exit(ActionEvent e){
        Stage s = (Stage)(((Node) e.getSource()).getScene()).getWindow();
        s.close();
    }

    private void makeReport(ActionEvent e){
        MessageBuilder messageBuilder = new MessageBuilder();
        String reason = userInput.getText();

        //checking if the reason the user enter is valid
        if(reason.length() == 0){
            title.setText(messageReplyPresenter.reportReasonEmpty());
            return;
        }

        //creating the report and exiting the reporting ui
        Message m = messageBuilder.getReportRequest(reason, accountName, message.getContent(), message.getSender());
        adminManager.addMessage(m);

        title.setText(messageReplyPresenter.success());
        new PopUp(messageReplyPresenter.reportSuccess());

        exit(e);

    }
}
