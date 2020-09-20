package frontend.messageReplyGUI.listeners;

import entities.Message;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.util.*;

public class UserMessageReplyGUI extends MessageReplyGUI{
    /**
     * Class constructor.
     * Create a new UserMessageReplySystem that controls and allows the user to reply to their messages
     * @param accountUsername the username of the currently logged in User
     * @param adminManager the admin manager of the system
     * @param userManager the user manager of the system
     * @param tradeManager the trade manager of the system
     * @param globalInventoryManager the global inventory manager of the system
     */
    public UserMessageReplyGUI(AdminManager adminManager, GlobalInventoryManager globalInventoryManager,
                                  TradeManager tradeManager, UserManager userManager, String accountUsername){
        super(adminManager, globalInventoryManager, tradeManager, userManager, accountUsername);
    }

    /**
     * Getter for a copy of the list of messages that this class will be responsible for.
     * Which will be a copy of all the messages for this given user account
     * @return a copy of the list of messages that this class will be responsible for
     */
    public List<Message> getMessage(){
        return userManager.getUserMessages(accountUsername);
    }

    /**
     * Method to save the new list of messages to the system
     * Which will save the new message list back in the given user account
     * @param messages the message list to be saved
     */
    public void saveMessage(List<Message> messages){
        userManager.setUserMessages(accountUsername, messages);
    }
}
