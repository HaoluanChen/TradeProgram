package frontend.messageReplyGUI.listeners;

import entities.ReportRequest;
import entities.Message;
import frontend.messageReplyGUI.presenters.MessageReplyPresenter;
import use_cases.UserManager;

import java.util.List;

public class ReportRequestResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private ReportRequest message;
    private List<Message> messageList;
    private UserManager userManager;

    /**
     * Class constructor.
     * Create a new ReportRequestResponse that responses to the user's action for a report request
     * @param message the message
     * @param userManager the user manager of the system
     * @param messageList the copyed message list from the source of the new item request
     */
    ReportRequestResponse(ReportRequest message, List<Message> messageList, UserManager userManager){
        this.message = message;
        this.messageList = messageList;
        this.userManager = userManager;
    }

    /**
     * Method to get all the possible actions an user can do to a report request
     * @return list of all possible actions in string
     */
    @Override
    public String[] getActions() {
        return messageReplyPresenter.requestAction(message);
    }

    /**
     * Method that takes in an actions, if it's from the list of possible actions, the method will do the action
     * @param action the action passed in
     */
    @Override
    public void doAction(String action) {
        String[]validActions = getActions();
        //Action: Ban reported person
        if(action.equals(validActions[0])){
            messageList.remove(message);
            userManager.banUserAccount(message.getReportedPerson());
        }
        //Action: Ignore
        else if(action.equals(validActions[1])){
            messageList.remove(message);
        }
        //Action: Ban reporter
        else if(action.equals(validActions[2])){
            messageList.remove(message);
            userManager.banUserAccount(message.getReporter());
        }
    }
}
