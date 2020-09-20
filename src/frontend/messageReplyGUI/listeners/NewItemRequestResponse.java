package frontend.messageReplyGUI.listeners;

import entities.Item;
import entities.NewItemRequest;
import entities.Message;
import frontend.messageReplyGUI.presenters.MessageReplyPresenter;
import use_cases.GlobalInventoryManager;
import use_cases.MessageBuilder;
import use_cases.UserManager;

import java.util.List;

public class NewItemRequestResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private NewItemRequest message;
    private List<Message> messageList;
    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;

    /**
     * Class constructor.
     * Create a new NewItemRequestResponse that responses to the user's action for a new item request
     * @param message the new item request
     * @param userManager the user manager of the system
     * @param messageList the copyed message list from the source of the new item request
     * @param globalInventoryManager the global inventory manager of the system
     */
    NewItemRequestResponse(NewItemRequest message, List<Message> messageList, UserManager userManager,
                           GlobalInventoryManager globalInventoryManager){
        this.message = message;
        this.messageList = messageList;
        this.userManager = userManager;
        this.globalInventoryManager = globalInventoryManager;
    }

    /**
     * Method to get all the possible actions an user can do to a new item request
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
        //Action: Approval
        if(action.equals(validActions[0])){
            messageList.remove(message);
            MessageBuilder messageBuilder = new MessageBuilder();

            Item item = message.getNewItem();
            globalInventoryManager.addItemToHashMap(item);
            //Informing the other user
            userManager.addUserMessage(item.getOwnerName(),
                    messageBuilder.getSystemMessage("Your Item: "+item+
                            "\nHas been successfully added to the system"));
        }
        //Action: Rejection
        else if(action.equals(validActions[1])){
            messageList.remove(message);
            MessageBuilder messageBuilder = new MessageBuilder();

            Item item = message.getNewItem();

            //Informing the other user
            userManager.addUserMessage(item.getOwnerName(),
                    messageBuilder.getSystemMessage("Your Item: "+item+
                            "\nHas been rejected"));
        }
    }
}
