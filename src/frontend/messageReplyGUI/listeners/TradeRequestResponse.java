package frontend.messageReplyGUI.listeners;

import entities.*;
import exceptions.UserFrozenException;
import frontend.messageReplyGUI.presenters.MessageReplyPresenter;
import frontend.popUp.PopUp;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import use_cases.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TradeRequestResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private TradeRequestManager tradeRequestManager;
    private List<Message> messageList;
    private UserManager userManager;
    private TradeManager tradeManager;
    private GlobalInventoryManager globalInventoryManager;
    private String accountUsername;

    private final String tradeRequestCannotConfirmFilepath = "/frontend/messageReplyGUI/fxml_files/TradeRequestCannotConfirm.fxml";
    private final String tradeRequestEditFilepath = "/frontend/messageReplyGUI/fxml_files/TradeRequestEdit.fxml";

    /**
     * Class constructor.
     * Create a new TradeRequestResponse that responses to the user's action for a trade request
     * @param message the message
     * @param tradeManager the trade manager of the system
     * @param userManager the user manager of the system
     * @param globalInventoryManager the global inventory manager of the system
     * @param messageList the copyed message list from the source of the new item request
     * @param accountName the username of the current user using the system
     */
    TradeRequestResponse(TradeRequest message, List<Message> messageList, UserManager userManager,
                         GlobalInventoryManager globalInventoryManager, TradeManager tradeManager, String accountName){
        this.messageList = messageList;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalInventoryManager = globalInventoryManager;
        accountUsername = accountName;

        tradeRequestManager = new TradeRequestManager(message);

        //Creates a popup window to warning the user if they have reached the max number of edits
        if(!tradeRequestManager.canEdit(accountName)&&!tradeRequestManager.canEdit(message.getSender())){
            new PopUp(messageReplyPresenter.tradeRequestWarning());
        }
    }

    /**
     * Method to get all the possible actions an user can do to a trade request
     * @return list of all possible actions in string
     */
    @Override
    public String[] getActions() {
        return messageReplyPresenter.requestAction(tradeRequestManager.getTradeRequest());
    }

    /**
     * Method that takes in an actions, if it's from the list of possible actions, the method will do the action
     * @param action the action passed in
     */
    @Override
    public void doAction(String action) {
        String[]validActions = getActions();
        TradeRequest message = tradeRequestManager.getTradeRequest();
        //Action: Confirm
        if(action.equals(validActions[0])){
            confirmTrade();
        }
        //Action: Reject
        else if(action.equals(validActions[1])){
            messageList.remove(tradeRequestManager.getTradeRequest());

            //Informing the other user
            MessageBuilder messageBuilder = new MessageBuilder();
            userManager.addUserMessage(message.getSender(),
                    messageBuilder.getSystemMessage("Your trade request:"+message.toString()+
                            "\n is rejected by "+ accountUsername));
        }
        //Action: Edit
        else if(action.equals(validActions[2])){
            //If the max number of edits have been reach, delete the message
            if(!tradeRequestManager.canEdit(accountUsername)&&!tradeRequestManager.canEdit(message.getSender())){
                new PopUp(messageReplyPresenter.tradeRequestCancel());
                messageList.remove(message);
                return;
            }

            tradeRequestEdit();
        }
    }



    //----------------Helpers----------------//
    private void confirmTrade(){
        TradeRequest message = tradeRequestManager.getTradeRequest();
        //Checking if the person can confirm
        if(cannotTrade(message.getUserA(),message.getItemA())||cannotTrade(message.getUserB(), message.getItemB())){
            tradeRequestCannotConfirm();
            return;
        }
        //Confirming the trade
        messageList.remove(message);
        Trade trade = tradeRequestManager.setConfirmation();
        //Add trade to both user's trade history
        tradeManager.addTrade(trade);

        //Removing the items from the GI/personal inventory
        List<Item> list = new ArrayList<>(trade.getTraderAItemsToTrade());
        for(Item i:list) {
            globalInventoryManager.removeItemOnly(i.getItemID());
        }
        list = new ArrayList<>(trade.getTraderBItemsToTrade());
        for(Item i:list) {
            globalInventoryManager.removeItemOnly(i.getItemID());
        }

        //Telling the user their action is done successfully
        new PopUp(messageReplyPresenter.success());
    }

    private boolean cannotTrade(String username, List<Item> userItem){
        //Checking if the user can trade
        try{
            if(!userManager.getCanTrade(username, tradeManager.getBorrowedTimes(username),
                    tradeManager.getLendTimes(username),
                    tradeManager.getIncompleteTimes(username),
                    tradeManager.numberOfTradesCreatedThisWeek(username))) return true;
        }catch(UserFrozenException e){
            return true;
        }
        //Checking if the items in the trade is valid aka in the personal inventory and GI
        for(Item i: userItem){
            if(!globalInventoryManager.contains(i))return true;
            boolean contain = false;
            for(Item j: userItem){
                if(i.isEqual(j))contain = true;
            }
            if(!contain)return true;
        }
        return false;
    }

    private void tradeRequestEdit(){
        //Creating the tradeRequestEdit UI
        setNewWindow(tradeRequestEditFilepath,
                new TradeRequestEditGUI(tradeRequestManager, userManager, messageList, accountUsername));
    }

    private void tradeRequestCannotConfirm(){
        //Creating the tradeRequestCannotConfirm UI
        setNewWindow(tradeRequestCannotConfirmFilepath,
                new TradeRequestCannotConfirmGUI(tradeRequestManager, userManager, messageList));
    }

    private void setNewWindow(String filepath, Initializable controller){
        try {
            Stage window = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(filepath));
            loader.setController(controller);
            Parent root = loader.load();

            window.initModality(Modality.APPLICATION_MODAL);
            window.initStyle(StageStyle.UNDECORATED);
            window.setScene(new Scene(root));
            window.showAndWait();
        }catch(IOException e){
            new PopUp(messageReplyPresenter.error());
        }
    }
}
