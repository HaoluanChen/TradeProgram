package frontend.tradeGUI.listeners;

import entities.Item;
import exceptions.IncompleteTradeException;
import frontend.popUp.PopUp;
import frontend.tradeGUI.presenters.TradeMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import use_cases.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TradeMenuMainController implements Initializable {
    private GlobalInventoryManager globalInventoryManager;
    private UserManager allUsers;
    private String userA;
    private List<Item> itemsToTradeB;
    private List<Item> itemsToTradeA;
    private GlobalWishlistManager globalWishlistManager;
    private String userB;

    @FXML private Label titleScreen;
    @FXML private TextField timeOfTrade;
    @FXML private TextField meetTrade;
    @FXML private MenuButton typesOfTrade;
    @FXML private MenuItem temporary;
    @FXML private MenuItem permanent;
    @FXML private MenuButton oneOrTwoWayTrade;
    @FXML private MenuItem oneWayTrade;
    @FXML private MenuItem twoWayTrade;
    @FXML private Button submit;
    @FXML private Button returnToMainMenu;
    @FXML private DatePicker primaryDate;

    private final String filepath = "/frontend/tradeGUI/fxml_files/MultiTradeItemMenu.fxml";

    /**
     * A controller for TradeMenuMain.fxml
     * @param globalInventoryManager is a GlobalInventoryManager that takes in all inventories from different users in the program.
     * @param globalWishlistManager is a the global wishlist manager
     * @param allUsers is a UserManager that contains all the users in the program
     * @param itemsToTradeB is a List that contains the items to trade from userB to userA.
     * @param userA is a String that contains the current user.
     */
    public TradeMenuMainController(GlobalInventoryManager globalInventoryManager, GlobalWishlistManager globalWishlistManager, UserManager allUsers, List<Item> itemsToTradeB, String userA) {
        this.itemsToTradeB = itemsToTradeB;
        this.userA = userA;
        this.userB = itemsToTradeB.get(0).getOwnerName();
        this.allUsers = allUsers;
        this.globalInventoryManager = globalInventoryManager;
        this.globalWishlistManager = globalWishlistManager;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // setting up text for GUI.
        titleScreen.setText(TradeMenu.TRADE);
        titleScreen.setAlignment(Pos.CENTER);
        typesOfTrade.setText(TradeMenu.TRADETYPE);
        temporary.setText(TradeMenu.TEMP);
        permanent.setText(TradeMenu.PERM);
        oneOrTwoWayTrade.setText(TradeMenu.ONETWOWAY);
        oneWayTrade.setText(TradeMenu.ONEWAY);
        twoWayTrade.setText(TradeMenu.TWOWAY);
        submit.setText(TradeMenu.SUBMIT);
        returnToMainMenu.setText(TradeMenu.EXIT);

        // actions
        permanent.setOnAction(this::permChoice);
        temporary.setOnAction(this::tempChoice);
        submit.setOnAction(actionEvent -> {
            try {
                submitAction(actionEvent);
            } catch (IncompleteTradeException error) {
                new PopUp(TradeMenu.ERROR);
            }
        });
        oneWayTrade.setOnAction(this::oneWayChoice);
        twoWayTrade.setOnAction(actionEvent -> {
            try {
                twoWayChoice(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        returnToMainMenu.setOnAction(this::exitProgram);

    }
    
    @FXML
    private void permChoice(ActionEvent actionEvent) {
        typesOfTrade.setText(permanent.getText());
    }

    @FXML
    private void tempChoice(ActionEvent actionEvent) {
        typesOfTrade.setText(temporary.getText());
    }

    @FXML
    private void submitAction(ActionEvent actionEvent) throws IncompleteTradeException {
        LocalDateTime tradeDateTime;
        String placeOfMeeting;
        String tradeType;
        String oneTwoWayTrade;
        if ((primaryDate.getValue() != null) &&
                (timeOfTrade.getText() != null) &&
                (!typesOfTrade.getText().equals(TradeMenu.TRADETYPE)) &&
                (!oneOrTwoWayTrade.getText().equals(TradeMenu.ONETWOWAY))) {

            try {
                // convert LocalDate to LocalDateTime
                LocalDate tradeDate = primaryDate.getValue();
                String datePattern = "H:mm";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
                LocalTime tradeTime = LocalTime.parse(timeOfTrade.getText().replaceAll("\\s+", ""), formatter);
                tradeDateTime = tradeDate.atTime(tradeTime);
            } catch (DateTimeParseException ex) {
                new PopUp(TradeMenu.WRONGFORMAT);
                return;
            }

            if(tradeDateTime.isBefore(LocalDateTime.now())){
                new PopUp(TradeMenu.PASTDATE);
                return;
            }

            // rest of the inputs from the user.
            placeOfMeeting = meetTrade.getText();
            tradeType = typesOfTrade.getText();

        } else {
            throw new IncompleteTradeException();
        }

        boolean perm = false;

        // push it into TradeRequestManager.
        if (tradeType.equals(TradeMenu.PERM)) {
            perm = true;
        }

        TradeRequestManager tradeRequest = new TradeRequestManager("User " + userA + " wants to trade with you.", userA, userA, userB, itemsToTradeA, itemsToTradeB, perm);
        tradeRequest.setDateAndPlaceFirst(tradeDateTime, placeOfMeeting);

        allUsers.addUserMessage(userB, tradeRequest.getTradeRequest());

        new PopUp(TradeMenu.SUCCESS);
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void oneWayChoice(ActionEvent actionEvent) {
        oneOrTwoWayTrade.setText(oneWayTrade.getText());
        itemsToTradeA = new ArrayList<>();
    }

    @FXML
    private void twoWayChoice(ActionEvent actionEvent) throws IOException {
        oneOrTwoWayTrade.setText(twoWayTrade.getText());
        String suggestions = suggestedItems();
        if (!suggestions.isEmpty()) {
            new PopUp(TradeMenu.SUGGEST + suggestions);
        }
        MultiTradeItemMenu multiItemMenu = new MultiTradeItemMenu(userA, globalInventoryManager);
        switchScene(multiItemMenu);
        itemsToTradeA = new ArrayList<>(multiItemMenu.getItems());
    }

    private String suggestedItems() {
        StringBuilder suggestedItems = new StringBuilder();
        ArrayList<Item> listOfSuggestedItems = (ArrayList<Item>) findSuggestedItems(userA, userB);
        // showing suggestions to the user.
        for (Item suggestion : listOfSuggestedItems) {
            suggestedItems.append(suggestion.getName()).append("\n");
        }
        return suggestedItems.toString();
    }

    /**
     * Finds suggested items for the user to use when making a two way trade with another user.
     * @param userA is a String that represents the user making the trade to userB.
     * @param userB is a String that represents the person that is receiving the trade from userA.
     * @return a list of Suggested items for userA to trade with userB.
     */
    private List<Item> findSuggestedItems(String userA, String userB) {
        List<Item> userAInventory = this.globalInventoryManager.getPersonInventory(userA);
        List<Item> suggestedItems = new ArrayList<Item>();
        List<String> interestedItemIDs = this.globalWishlistManager.getInterestedItems(userAInventory, userB);

        if (!interestedItemIDs.isEmpty()) {
            // converting itemIDs into Items.
            for (String itemID : interestedItemIDs) {
                suggestedItems.add(this.globalInventoryManager.getItemFromGI(itemID));
            }
        }
        return suggestedItems;
    }

    private void switchScene(MultiTradeItemMenu multiItemMenu) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filepath));
        loader.setController(multiItemMenu);
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(newScene);
        window.showAndWait();
    }

    @FXML
    private void exitProgram(ActionEvent e) {
        Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
        s.close();
    }
}
