package frontend.messageReplyGUI.listeners;

import entities.Message;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.UserManager;

import java.util.*;

public class AdminMessageReplyGUI extends MessageReplyGUI{

    /**
     * Class constructor.
     * Create a new UserMessageReplySystem that controls and allows the admin to reply to system messages
     * @param accountUsername the username of the currently logged in User
     * @param userManager the user manager of the system
     * @param adminManager the admin manager of the system
     * @param globalInventoryManager the global inventory manager of the system
     */
    public AdminMessageReplyGUI(AdminManager adminManager, GlobalInventoryManager globalInventoryManager,
                               UserManager userManager, String accountUsername){
        super(adminManager, globalInventoryManager, null, userManager, accountUsername);
    }

    /**
     * Getter for a copy of the list of messages that this class will be responsible for.
     * Which will be a copy of all the admin messages
     * @return a copy of the list of messages that this class will be responsible for
     */
    @Override
    public List<Message> getMessage(){
        return new ArrayList<>(adminManager.getAdminMessages());
    }

    /**
     * Method to save the new list of messages to the system
     * Which will save the new message list back in the admin messages
     * @param messages the message list to be saved
     */
    @Override
    public void saveMessage(List<Message> messages){
        adminManager.setAdminMessages(messages);
    }
}