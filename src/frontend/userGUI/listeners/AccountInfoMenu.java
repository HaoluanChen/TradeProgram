package frontend.userGUI.listeners;

import frontend.userGUI.AccountInfo.listeners.BrowseThroughUserCollection;
import frontend.userGUI.AccountInfo.listeners.NewPassword;
import frontend.userGUI.AccountInfo.listeners.RecentTradesTradePartners;
import frontend.userGUI.presenters.UserPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import use_cases.*;
import entities.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AccountInfoMenu implements Initializable {

    // JavaFX stuff
    @FXML private Button tradeHistory;
    @FXML private Button newPassword;
    @FXML private Button tradePartners;
    @FXML private Button recentTrades;
    @FXML private Button inventory;
    @FXML private Button wishlist;
    @FXML private Button exit;
    @FXML private Label systemMessage;

    // instance variables
    private String currUser;
    private UserManager userManager;
    private TradeManager tradeManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;
    private UserPresenter userPresenter;
    private List<Trade> userTrades;
    private List<Item> userInventory;
    private List<Item> userWishlist;
    private Trade[] recentTradeHistory;
    private String[] tradingPartners;
    private Type type;

    // .fxml pathways
    private final String tradeHistoryFXML = "/frontend/userGUI/AccountInfo/fxml_files/BrowseThroughUserTrades.fxml";
    private final String passwordFXML = "/frontend/userGUI/AccountInfo/fxml_files/NewPasswordGUI.fxml";
    private final String tradePartnersFXML = "/frontend/userGUI/AccountInfo/fxml_files/RecentTradeTradePartnersGUI.fxml";
    private final String recentTradesFXML = "/frontend/userGUI/AccountInfo/fxml_files/RecentTradeTradePartnersGUI.fxml";
    private final String inventoryFXML = "/frontend/userGUI/AccountInfo/fxml_files/BrowseThroughUserInventory.fxml";
    private final String wishlistFXML = "/frontend/userGUI/AccountInfo/fxml_files/BrowseThroughUserWishlist.fxml";

    /**
     * Constructs a new BrowseThroughUserInfo object
     * @param currUser String username
     * @param userManager UserManager object
     * @param tradeManager TradeManager object
     * @param globalInventoryManager GlobalInventoryManager object
     * @param globalWishlistManager GlobalWishlistManager object
     */
    public AccountInfoMenu(String currUser, UserManager userManager, TradeManager tradeManager,
                           GlobalInventoryManager globalInventoryManager, GlobalWishlistManager globalWishlistManager) {
        this.currUser = currUser;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
        this.userPresenter = new UserPresenter();
    }

    enum Type {
        TRADE_HISTORY, PASSWORD, TRADE_PARTNERS, RECENT_TRADES, INVENTORY, WISHLIST
    }

    /**
     * Sets up button text/functionality
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set text
        this.tradeHistory.setText(this.userPresenter.accountInfoPromptTradeHistory());
        this.newPassword.setText(this.userPresenter.accountInfoPromptPassword());
        this.tradePartners.setText(this.userPresenter.accountInfoPromptTradingPartners());
        this.recentTrades.setText(this.userPresenter.accountInfoPromptRecentTrades());
        this.inventory.setText(this.userPresenter.accountInfoPromptInventory());
        this.wishlist.setText(this.userPresenter.accountInfoPromptWishlist());
        this.exit.setText(this.userPresenter.menuPromptExit());

        // set button function
        this.tradeHistory.setOnAction(e -> viewTradeHistory());
        this.newPassword.setOnAction(e -> setNewPassword());
        this.tradePartners.setOnAction(e -> viewTradePartners());
        this.recentTrades.setOnAction(e -> viewRecentTrades());
        this.inventory.setOnAction(e -> viewInventory());
        this.wishlist.setOnAction(e -> viewWishlist());
        this.exit.setOnAction(this::returnToMainMenu);
    }

    /**
     * Switches the scene being viewed
     * @param filename the filename of the .fxml file to be loaded
     * @throws IOException for a funky input
     */
    private void switchScene(String filename) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        switch(this.type) {
            // view trade history
            case TRADE_HISTORY:
                loader.setController(new BrowseThroughUserCollection(
                        this.userTrades, this.tradeManager, this.currUser));
                break;
            // change password
            case PASSWORD:
                loader.setController(new NewPassword(this.currUser, this.userManager));
                break;
            // view trade partners
            case TRADE_PARTNERS:
                loader.setController(new RecentTradesTradePartners(this.tradingPartners));
                break;
            // view 3 most recent trades
            case RECENT_TRADES:
                loader.setController(new RecentTradesTradePartners(this.recentTradeHistory));
                break;
            // view user inventory
            case INVENTORY:
                loader.setController(new BrowseThroughUserCollection(this.userInventory,
                        this.globalInventoryManager, this.currUser));
                break;
            // view user wishlist
            case WISHLIST:
                loader.setController(new BrowseThroughUserCollection(this.userWishlist, this.currUser,
                        this.userManager, this.tradeManager, this.globalInventoryManager, this.globalWishlistManager));
                break;
        }
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage window = new Stage();
        window.initStyle(StageStyle.UNDECORATED);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(newScene);
        window.show();
    }

    /**
     * Accesses tradeHistory menu
     */
    private void viewTradeHistory() {
        this.userTrades = this.tradeManager.getTradeHistory(this.currUser);
        // if nothing in trade history
        if(this.userTrades.size() == 0) {
            this.systemMessage.setText(this.userPresenter.isEmpty("trade history"));
        }
        else {
            try {
                this.type = Type.TRADE_HISTORY;
                switchScene(this.tradeHistoryFXML);
            }
            catch(IOException ex) {
                this.systemMessage.setText(this.userPresenter.ioErrorMessage());
            }
        }
    }

    /**
     * Accesses new password menu
     */
    private void setNewPassword() {
        try {
            this.type = Type.PASSWORD;
            switchScene(this.passwordFXML);
        }
        catch(IOException ex) {
            this.systemMessage.setText(this.userPresenter.ioErrorMessage());
        }
    }

    /**
     * Accesses trade Partners menu
     */
    private void viewTradePartners() {
        this.tradingPartners = this.tradeManager.getFrequentTradingPartners(this.currUser, 3);
        // if the first entry is null
        if(this.tradingPartners[0] == null) {
            this.systemMessage.setText(this.userPresenter.noTradingPartners());
        }
        else {
            try {
                this.type = Type.TRADE_PARTNERS;
                switchScene(this.tradePartnersFXML);
            }
            catch(IOException ex) {
                this.systemMessage.setText(this.userPresenter.ioErrorMessage());
            }
        }
    }

    /**
     * Accesses recent trades menu
     */
    private void viewRecentTrades() {
        this.recentTradeHistory = this.tradeManager.getRecentTrade(this.currUser, 3);
        // if the first entry is null
        if (this.recentTradeHistory[0] == null) {
            this.systemMessage.setText(this.userPresenter.noRecentTrades());
        }
        else {
            try {
                this.type = Type.RECENT_TRADES;
                switchScene(this.recentTradesFXML);
            } catch (IOException ex) {
                this.systemMessage.setText(this.userPresenter.ioErrorMessage());
            }
        }
    }

    /**
     * Accesses viewInventory menu
     */
    private void viewInventory() {
        this.userInventory = this.globalInventoryManager.getPersonInventory(this.currUser);
        // check to see if the inventory populated
        if(this.userInventory.size() == 0) {
            this.systemMessage.setText(this.userPresenter.isEmpty("inventory"));
        }
        // if the inventory is populated
        else {
            try {
                this.type = Type.INVENTORY;
                switchScene(this.inventoryFXML);
            }
            catch(IOException ex) {
                this.systemMessage.setText(this.userPresenter.ioErrorMessage());
            }
        }
    }

    /**
     * Accesses viewWishlist menu
     */
    private void viewWishlist() {
        // list of item ids
        List<String> userWishlistIDs = this.globalWishlistManager.getPersonWishlist(this.currUser);
        // check to see if wishlist is empty
        if(userWishlistIDs.size() == 0) {
            this.systemMessage.setText(this.userPresenter.isEmpty("wishlist"));
        }
        // if the wishlist is populated
        else {
            try {
                // construct the user wishlist
                this.userWishlist = new ArrayList<>();
                for(String itemID : userWishlistIDs) {
                    this.userWishlist.add(this.globalInventoryManager.getItemFromGI(itemID));
                }
                this.type = Type.WISHLIST;
                switchScene(this.wishlistFXML);
            }
            catch(IOException ex) {
                this.systemMessage.setText(this.userPresenter.ioErrorMessage());
            }
        }
    }

    /**
     * Returns to main Menu
     * @param actionEvent the ActionEvent object
     */
    private void returnToMainMenu(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
