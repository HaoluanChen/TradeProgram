package frontend.messageReplyGUI.listeners;

import entities.UnbanRequest;
import entities.Message;
import frontend.messageReplyGUI.presenters.MessageReplyPresenter;
import use_cases.UserManager;

import java.util.List;

public class UnbanRequestResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private UnbanRequest message;
    private List<Message> messageList;
    private UserManager userManager;

    /**
     * Class constructor.
     * Create a new UnbanRequestResponse that responses to the user's action for a unban request
     * @param message the message
     * @param userManager the user manager of the system
     * @param messageList the copyed message list from the source of the new item request
     */
    UnbanRequestResponse(UnbanRequest message, List<Message> messageList, UserManager userManager){
        this.message = message;
        this.messageList = messageList;
        this.userManager = userManager;
    }

    /**
     * Method to get all the possible actions an user can do to a unban request
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
        //Action: Unban
        if(action.equals(validActions[0])){
            messageList.remove(message);
            if(userManager.getUserIsBanned(message.getUser())){
                userManager.unFreezeUserAccount(message.getUser());
            }
        }
        //Action: Ignore
        else if(action.equals(validActions[1])){
            messageList.remove(message);
        }
    }
}
