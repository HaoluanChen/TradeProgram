package frontend.userGUI.listeners;

import entities.Item;
import frontend.tradeGUI.listeners.TradeMenuMainLendController;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoanMenu implements Initializable {

    private List<Item> userInventory;
    private List<String> itemsToLend;
    private String currUser;
    private UserManager userManager;
    private TradeManager tradeManager;
    private GlobalWishlistManager globalWishlistManager;
    private UserPresenter userPresenter;
    private GlobalInventoryManager globalInventoryManager;
    private List<Item> userItemChoice;

    @FXML private Label itemPrompt;
    @FXML private Label recipientPrompt;
    @FXML private Label userItem;
    @FXML private Label recipientUser;
    @FXML private Label prompt;
    @FXML private Button confirmTrade;
    @FXML private Button denyTrade;

    private final String tradeRunFromLoanFXML = "/frontend/tradeGUI/fxml_files/TradeMenuLend.fxml";

    /**
     * Instantiates a LoanMenu listener
     * @param userInventory the UserInventory object
     * @param itemsToLend the String list of [this user's item's itemID, recipient username]
     * @param currUser the current user
     * @param userManager the UserManager object
     * @param tradeManager the TradeManager object
     * @param globalWishlistManager the GlobalWishlistManager object
     * @param globalInventoryManager the GlobalInventoryManager object
     */
    public LoanMenu(List<Item> userInventory, List<String> itemsToLend, String currUser, UserManager userManager,
                    TradeManager tradeManager, GlobalWishlistManager globalWishlistManager,
                    GlobalInventoryManager globalInventoryManager) {
        this.userInventory = userInventory;
        this.itemsToLend = itemsToLend;
        this.currUser = currUser;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalWishlistManager = globalWishlistManager;
        this.globalInventoryManager = globalInventoryManager;
        this.userPresenter = new UserPresenter();
    }

    /**
     * Sets up button functionality/text
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set text
        this.userItemChoice = setUpTradeOptions();
        this.userItem.setText(userItemChoice.get(0).toString());
        this.recipientUser.setText(this.itemsToLend.get(1));
        this.itemPrompt.setText(this.userPresenter.userLoanPromptOfferedItemLabel());
        this.recipientPrompt.setText(this.userPresenter.userLoanPromptRecipientLabel());
        this.confirmTrade.setText(this.userPresenter.userLoanPromptConfirm());
        this.denyTrade.setText(this.userPresenter.userLoanPromptCancel());
        this.prompt.setText(this.userPresenter.userLoanPrompt());

        // set button function
        this.confirmTrade.setOnAction(e -> sendTradeRequest());
        this.denyTrade.setOnAction(this::returnToMainMenu);
    }

    /**
     * Returns a list of an item in this user's inventory that someone else wants
     * @return List of an Item
     */
    private List<Item> setUpTradeOptions() {
        List<Item> userItem = new ArrayList<>();
        for(Item item : this.userInventory) {
            if (item.getItemID().equals(this.itemsToLend.get(0))) {
                userItem.add(item);
            }
        }
        return userItem;
    }

    /**
     * Brings up the trade request menu
     */
    private void sendTradeRequest() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.tradeRunFromLoanFXML));
            loader.setController(new TradeMenuMainLendController(this.userManager,
                    this.userItemChoice, this.currUser, this.itemsToLend.get(1)));
            Parent root = loader.load();
            Scene newScene= new Scene(root);
            Stage window = new Stage();
            window.initStyle(StageStyle.UNDECORATED);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setScene(newScene);
            window.show();
        }
        catch(IOException ex) {
            // error message here
        }
    }

    /**
     * Returns to the main menu
     * @param actionEvent ActionEvent object
     */
    private void returnToMainMenu(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
