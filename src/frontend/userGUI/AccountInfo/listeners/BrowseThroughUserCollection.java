package frontend.userGUI.AccountInfo.listeners;

import entities.PermTrade;
import exceptions.UserFrozenException;
import frontend.globalInventoryGUI.listeners.MultiItemMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Item;
import entities.Trade;
import javafx.stage.StageStyle;
import use_cases.GlobalInventoryManager;
import use_cases.GlobalWishlistManager;
import use_cases.TradeManager;
import use_cases.UserManager;
import frontend.userGUI.presenters.UserPresenter;

public class BrowseThroughUserCollection implements Initializable {

    // instance variables
    private List<Item> userItemCollection;
    private List<Trade> userTradeCollection;
    private String currUser;
    private GlobalInventoryManager globalInventoryManager;
    private TradeManager tradeManager;
    private UserManager userManager;
    private GlobalWishlistManager globalWishlistManager;
    private UserPresenter userPresenter;
    private Type type;
    private Item selectedItem;
    private Trade selectedTrade;

    // fxml stuff
    @FXML private Label systemMessage;
    @FXML private Label unconfirmedTradesPrompt;
    @FXML private Button remove;
    @FXML private Button sendTradeRequest;
    @FXML private Button confirm;
    @FXML private Button deny;
    @FXML private Button exit;
    @FXML private ListView<Item> itemList;
    @FXML private ListView<Trade> tradeList;

    // file paths
    private final String multiMenuFXML = "/frontend/globalInventoryGUI/fxml_files/MultiItemMenu.fxml";

    /**
     * ENUM of Collection types
     */
    enum Type {
        INVENTORY, WISHLIST, UNCONFIRMED, TRADE_HISTORY
    }

    /**
     * Instantiate a browseThroughUserTrades object
     * @param userTrades the list of user trades
     * @param tradeManager the TradeManager object
     * @param currUser the currently logged in user
     */
    public BrowseThroughUserCollection(List<Trade> userTrades, TradeManager tradeManager, String currUser) {
        this.userTradeCollection = userTrades;
        this.tradeManager = tradeManager;
        this.currUser = currUser;
        this.userPresenter = new UserPresenter();
        this.type = Type.TRADE_HISTORY;
        this.selectedTrade = null;
    }

    /**
     * Instantiate a browseThroughUnconfirmedTrades listener
     * @param currUser the currently logged in user
     * @param tradeManager the TradeManager object
     * @param globalWishlistManager the GlobalWishlistManager object
     * @param unconfirmedTrades list of unconfirmed trades this user have
     */
    public BrowseThroughUserCollection(String currUser, TradeManager tradeManager,
                                       GlobalWishlistManager globalWishlistManager, List<Trade> unconfirmedTrades) {
        this.currUser = currUser;
        this.tradeManager = tradeManager;
        this.globalWishlistManager = globalWishlistManager;
        this.userTradeCollection = unconfirmedTrades;
        this.userPresenter = new UserPresenter();
        this.type = Type.UNCONFIRMED;
        this.selectedTrade = null;
    }

    /**
     * Instantiate a browseThroughUserInventory object
     * @param userInventory the user's inventory
     * @param globalInventoryManager the GlobalInventoryManager object
     * @param currUser the currently logged in user
     */
    public BrowseThroughUserCollection(List<Item> userInventory, GlobalInventoryManager globalInventoryManager,
                                       String currUser) {
        this.userItemCollection= userInventory;
        this.globalInventoryManager = globalInventoryManager;
        this.currUser = currUser;
        this.userPresenter = new UserPresenter();
        this.type = Type.INVENTORY;
        this.selectedItem = null;
    }

    /**
     * Instantiate a browseThroughUserWishlist object
     * @param userWishlist the user's wishlist
     * @param currUser the currently logged in user
     * @param userManager the UserManager object
     * @param tradeManager the TradeManager object
     * @param globalInventoryManager the GlobalInventoryManager object
     * @param globalWishlistManager the GlobalWishlistManager object
     */
    public BrowseThroughUserCollection(List<Item> userWishlist, String currUser, UserManager userManager,
                                       TradeManager tradeManager, GlobalInventoryManager globalInventoryManager,
                                     GlobalWishlistManager globalWishlistManager) {
        this.userItemCollection = userWishlist;
        this.currUser = currUser;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
        this.userPresenter = new UserPresenter();
        this.type = Type.WISHLIST;
        this.selectedItem = null;
    }

    /**
     * Set up button functionality/label text
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set up exit button
        this.exit.setText(this.userPresenter.menuPromptExit());
        this.exit.setOnAction(this::exit);

        // set up for types INVENTORY/WISHLIST
        if(this.type == Type.INVENTORY || this.type == Type.WISHLIST) {
            // set up exit button
            this.remove.setText(this.userPresenter.menuPromptRemove());
            this.remove.setOnAction(e -> remove());

            // set up list view
            this.itemList.getItems().addAll(this.userItemCollection);
            this.itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                    this.selectedItem = newValue);

            // if this instance is a wishlist collection
            if(this.type == Type.WISHLIST) {
                this.sendTradeRequest.setText(this.userPresenter.wishlistPromptTradeOffer());
                this.sendTradeRequest.setOnAction(e -> sendTradeRequest());
            }
        }
        // set up for trade history
        else if(this.type == Type.TRADE_HISTORY) {
            this.tradeList.getItems().addAll(this.userTradeCollection);
        }
        // set up for UNCONFIRMED TRADES
        else {
            // set up text
            this.unconfirmedTradesPrompt.setText(this.userPresenter.promptUserToConfirmTrades());
            this.confirm.setText(this.userPresenter.confirmMeetingPrompt());
            this.deny.setText(this.userPresenter.denyMeetingPrompt());

            // set up button functionality
            this.confirm.setOnAction(e -> confirm());
            this.deny.setOnAction(e -> deny());

            // set up list view
            this.tradeList.getItems().addAll(this.userTradeCollection);
            this.tradeList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                    this.selectedTrade = newValue);
        }
    }

    /**
     * Remove the currently viewed item from the user inventory/wishlist
     */
    private void remove() {
        // if there are no items left
        if(this.itemList.getItems().size() == 0) {
            this.systemMessage.setText(this.userPresenter.isEmpty("collection"));
        }
        // if there is no item selected
        else if(this.selectedItem == null) {
            this.systemMessage.setText(this.userPresenter.selectValidObject());
        }
        else {
            switch(this.type) {
                // remove from global inventory and reset this instance's local list
                case INVENTORY:
                    this.globalInventoryManager.removeItemAndAddToGarbageBin(this.selectedItem.getItemID());
                    break;
                // remove from global wishlist and reset this instance's local list
                case WISHLIST:
                    this.globalWishlistManager.removeWish(this.selectedItem.getItemID(), this.currUser);
                    break;
            }
            this.itemList.getItems().remove(this.selectedItem);
            this.systemMessage.setText(this.userPresenter.itemRemoved());

            // round about fix for bug where last item would not be selected
            if(this.itemList.getItems().size() == 1) {
                this.selectedItem = this.itemList.getItems().get(0);
            }
            else {
                this.selectedItem = null;
            }
        }
    }

    /**
     * Method for confirming unconfirmed user trades
     */
    private void confirm() {
        // if there is nothing left in unconfirmed
        if(this.tradeList.getItems().size() == 0) {
            this.systemMessage.setText(this.userPresenter.unconfirmedMeetingAllDone());
        }
        // if there is no selected trade
        else if(this.selectedTrade == null) {
            this.systemMessage.setText(this.userPresenter.selectValidObject());
        }
        else {
            this.tradeManager.setConfirm(this.currUser, this.selectedTrade, true);

            // if this trade is a PermTrade
            if (this.selectedTrade instanceof PermTrade) {
                // remove all traderA items from Global wishlist if not empty
                removeTradingItemsFromWishlist(this.selectedTrade);
            }
            this.tradeList.getItems().remove(this.selectedTrade);

            // round about fix for bug where last item would not be selected
            if(this.tradeList.getItems().size() == 1) {
                this.selectedTrade = this.tradeList.getItems().get(0);
            }
            else {
                this.selectedTrade = null;
            }
        }
    }

    /**
     * Method for denying unconfirmed user trades
     */
    private void deny() {
        // if there are no unconfirmed trades left
        if(this.tradeList.getItems().size() == 0) {
            this.systemMessage.setText(this.userPresenter.unconfirmedMeetingAllDone());
        }
        // if there is no trade selected
        else if(this.selectedTrade == null) {
            this.systemMessage.setText(this.userPresenter.selectValidObject());
        }
        else {
            this.tradeManager.setConfirm(this.currUser, this.selectedTrade, false);
            this.tradeList.getItems().remove(this.selectedTrade);

            // round about fix for bug where last item would not be selected
            if(this.tradeList.getItems().size() == 1) {
                this.selectedTrade = this.tradeList.getItems().get(0);
            }
            else {
                this.selectedTrade = null;
            }
        }
    }

    /**
     * Helper to remove the items from the global wishlist if necessary
     * @param trade the Trade item to use
     */
    private void removeTradingItemsFromWishlist(Trade trade) {
        if(trade.getTraderAItemsToTrade().size() != 0) {
            for(Item item : trade.getTraderAItemsToTrade()) {
                // check to make sure that this item exists on the global wishlist
                if(this.globalWishlistManager.isItemWanted(item.getItemID())) {
                    this.globalWishlistManager.removeItem(item.getItemID());
                }
            }
        }
        // remove all tradeB items from Global and Personal wishlists if not empty
        if(trade.getTraderBItemsToTrade().size() != 0) {
            for (Item item : trade.getTraderBItemsToTrade()) {
                // check to make sure item exists in global wishlist
                if(this.globalWishlistManager.isItemWanted(item.getItemID())) {
                    this.globalWishlistManager.removeItem(item.getItemID());
                }
            }
        }
    }

    /**
     * Send a trade request
     */
    private void sendTradeRequest() {
        if(this.itemList.getItems().size() == 0) {
            this.systemMessage.setText(this.userPresenter.isEmpty("collection"));
        }
        else if(this.selectedItem == null) {
            this.systemMessage.setText(this.userPresenter.selectValidObject());
        }
        else {
            try {
                if(this.userManager.getCanTrade(this.currUser, this.tradeManager.getBorrowedTimes(this.currUser),
                        this.tradeManager.getLendTimes(this.currUser),
                        this.tradeManager.getIncompleteTimes(this.currUser),
                        this.tradeManager.numberOfTradesCreatedThisWeek(this.currUser))) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(this.multiMenuFXML));
                    loader.setController(new MultiItemMenu(this.selectedItem, this.currUser,
                            this.globalInventoryManager, this.userManager, this.globalWishlistManager));
                    Parent root = loader.load();
                    Scene newScene = new Scene(root);
                    Stage window = new Stage();
                    window.initStyle(StageStyle.UNDECORATED);
                    window.initModality(Modality.APPLICATION_MODAL);
                    window.setScene(newScene);
                    window.show();
                }
            }
            catch(IOException ex) {
                this.systemMessage.setText(this.userPresenter.ioErrorMessage());
            }
            catch(UserFrozenException ex) {
                this.systemMessage.setText(this.userPresenter.userAccountFrozen());
            }
        }
    }

    /**
     * Exit the scene
     * @param actionEvent ActionEvent object
     */
    private void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
