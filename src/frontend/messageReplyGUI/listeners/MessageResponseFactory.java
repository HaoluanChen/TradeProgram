package frontend.messageReplyGUI.listeners;

import entities.*;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.util.List;

public class MessageResponseFactory {
    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;
    private TradeManager tradeManager;
    private String accountUsername;
    private AdminManager adminManager;
    private List<Message> messageList;

    /**
     * Class constructor.
     * Create a new MessageResponseFactory that creates some type/s of MessageResponse
     * @param accountUsername the username of the currently logged in User
     * @param adminManager the admin manager of the system
     * @param userManager the user manager of the system
     * @param tradeManager the trade manager of the system
     * @param globalInventoryManager the global inventory manager of the system
     */
    MessageResponseFactory(AdminManager adminManager, GlobalInventoryManager globalInventoryManager,
                                  TradeManager tradeManager, UserManager userManager,
                                  String accountUsername){
        this.adminManager = adminManager;
        this.globalInventoryManager = globalInventoryManager;
        this.tradeManager = tradeManager;
        this.userManager = userManager;
        this.accountUsername = accountUsername;
    }

    /**
     * Setter to the copy of the source of all the messages
     * @param messageList copy of the source of all the messages
     */
    void setMessageList(List<Message> messageList){
        this.messageList = messageList;
    }

    /**
     * Getter to the messageList in this class
     * @return the messageList
     */
    List<Message> getMessageList(){
        return messageList;
    }

    /**
     * Creates and return a type of MessageResponse based on the message given
     * @param message a message, the message must be in the messageList
     * @return a new MessageResponse to response to the given message
     */
    MessageResponse getMessageResponse(Message message){
        if(message instanceof SystemMessage){
            return new SystemMessageResponse(message, messageList);
        }
        else if(message instanceof PrivateMessage){
            return new PrivateMessageResponse(message, adminManager, messageList, accountUsername);
        }
        else if(message instanceof FreezeRequest){
            return new FreezeRequestResponse((FreezeRequest) message, messageList, userManager);
        }
        else if(message instanceof UnfreezeRequest){
            return new UnfreezeRequestResponse((UnfreezeRequest) message, messageList, userManager);
        }
        else if(message instanceof ReportRequest){
            return new ReportRequestResponse((ReportRequest) message, messageList, userManager);
        }
        else if(message instanceof UnbanRequest){
            return new UnbanRequestResponse((UnbanRequest) message, messageList, userManager);
        }
        else if(message instanceof NewItemRequest){
            return new NewItemRequestResponse((NewItemRequest)message, messageList, userManager, globalInventoryManager);
        }
        else {
            return new TradeRequestResponse((TradeRequest)message, messageList, userManager, globalInventoryManager,
                    tradeManager, accountUsername);
        }
    }
}
