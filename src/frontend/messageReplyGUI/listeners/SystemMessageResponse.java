package frontend.messageReplyGUI.listeners;

import entities.Message;
import frontend.messageReplyGUI.presenters.MessageReplyPresenter;

import java.util.List;

public class SystemMessageResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private Message message;
    private List<Message> messageList;

    /**
     * Class constructor.
     * Create a new SystemMessageResponse that responses to the user's action for a system message
     * @param message the message
     * @param messageList the copyed message list from the source of the new item request
     */
    SystemMessageResponse(Message message, List<Message> messageList){
        this.message = message;
        this.messageList = messageList;
    }

    /**
     * Method to get all the possible actions an user can do to a report request
     * @return list of all possible actions in string
     */
    @Override
    public String[] getActions() {
        return messageReplyPresenter.systemMessageAction();
    }

    /**
     * Method that takes in an actions, if it's from the list of possible actions, the method will do the action
     * @param action the action passed in
     */
    @Override
    public void doAction(String action) {
        String[]validActions = getActions();
        //Action: Deletion
        if(action.equals(validActions[0])){
            messageList.remove(message);
        }
    }
}
