package frontend.messageReplyGUI.listeners;

import frontend.messageReplyGUI.presenters.MessageReplyPresenter;
import frontend.popUp.PopUp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import javafx.stage.StageStyle;
import entities.Message;
import use_cases.AdminManager;

public class PrivateMessageResponse implements MessageResponse {

    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private Message message;
    private List<Message> messageList;
    private AdminManager adminManager;
    private String accountName;

    private final String reportCreationFilepath = "/frontend/messageReplyGUI/fxml_files/MakeReport.fxml";

    /**
     * Class constructor.
     * Create a new PrivateMessageResponse that responses to the user's action for a private message
     * @param message the message
     * @param adminManager the admin manager of the system
     * @param messageList the copyed message list from the source of the new item request
     * @param accountName the username of the current user using the system
     */
    PrivateMessageResponse(Message message, AdminManager adminManager, List<Message> messageList, String accountName){
        this.message = message;
        this.adminManager = adminManager;
        this.messageList = messageList;
        this.accountName = accountName;
    }

    /**
     * Method to get all the possible actions an user can do to a private message
     * @return list of all possible actions in string
     */
    @Override
    public String[] getActions() {
        return messageReplyPresenter.privateMessageAction();
    }

    /**
     * Method that takes in an actions, if it's from the list of possible actions, the method will do the action
     * @param action the action passed in
     */
    @Override
    public void doAction(String action){
        String[]validActions = getActions();
        //Action: Deletion
        if(action.equals(validActions[0])){
            messageList.remove(message);
        }
        //Action: Reporting
        else if(action.equals(validActions[1])){
            //Create the reporting UI
            try {
                Stage window = new Stage();
                FXMLLoader reportLoader = new FXMLLoader(getClass().getResource(reportCreationFilepath));
                reportLoader.setController(new MakeReportGUI(message, adminManager, accountName));
                Parent root = reportLoader.load();

                window.initModality(Modality.APPLICATION_MODAL);
                window.setScene(new Scene(root));
                window.setTitle(messageReplyPresenter.reportTitle());
                window.initStyle(StageStyle.UNDECORATED);
                window.show();
            }catch(IOException e){
                new PopUp(messageReplyPresenter.error());
            }
        }
    }

}
