package frontend.messageReplyGUI.listeners;

import entities.UnfreezeRequest;
import entities.Message;
import frontend.messageReplyGUI.presenters.MessageReplyPresenter;
import use_cases.MessageBuilder;
import use_cases.UserManager;

import java.util.List;

public class UnfreezeRequestResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private UnfreezeRequest message;
    private List<Message> messageList;
    private UserManager userManager;

    /**
     * Class constructor.
     * Create a new UnfreezeRequestResponse that responses to the user's action for a unfreeze request
     * @param message the unfreeze request message
     * @param userManager the user manager of the system
     * @param messageList the copyed message list from the source of the freeze request
     */
    UnfreezeRequestResponse(UnfreezeRequest message, List<Message> messageList, UserManager userManager){
        this.message = message;
        this.messageList = messageList;
        this.userManager = userManager;
    }

    /**
     * Method to get all the possible actions an user can do to a unfreeze request
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
        //Action: Unfreeze
        if(action.equals(validActions[0])){
            messageList.remove(message);
            String username = message.getUser();
            userManager.unFreezeUserAccount(username);

            //informing the other user
            MessageBuilder messageBuilder = new MessageBuilder();
            userManager.addUserMessage(username,
                    messageBuilder.getSystemMessage("Your account is unfrozen"));
        }
        //Action: Reject
        else if(action.equals(validActions[1])){
            messageList.remove(message);
            String username = message.getUser();


            //Informing the other user
            MessageBuilder messageBuilder = new MessageBuilder();
            userManager.addUserMessage(username,
                    messageBuilder.getSystemMessage("Your request is rejected"));

        }
    }
}
