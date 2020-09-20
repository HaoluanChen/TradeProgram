package frontend.tradeGUI.listeners;

import entities.Item;
import exceptions.IncompleteTradeException;
import frontend.popUp.PopUp;
import frontend.tradeGUI.presenters.TradeMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import use_cases.TradeRequestManager;
import use_cases.UserManager;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TradeMenuMainLendController implements Initializable {

    private List<Item> itemsToTradeA;
    private String userA;
    private String userB;
    private UserManager allUsers;

    private String tradeType;
    private String placeOfMeeting;
    private LocalDateTime tradeDateTime;

    @FXML private Label titleScreen;
    @FXML private TextField timeOfTrade;
    @FXML private TextField meetTrade;
    @FXML private MenuButton typesOfTrade;
    @FXML private MenuItem temporary;
    @FXML private MenuItem permanent;
    @FXML private Button submit;
    @FXML private Button returnToMainMenu;
    @FXML private DatePicker primaryDate;

    /**
     * A controller for TradeMenuMainLend.fxml
     * @param allUsers is a UserManager that contains all the users in the program
     * @param itemsToTradeA is a List that contains the items that userA have and wants to loan to userB
     * @param userA is a String that contains the current user.
     * @param userB the person this user want to loan his items to
     *
     */
    public TradeMenuMainLendController(UserManager allUsers, List<Item> itemsToTradeA, String userA,
                                       String userB) {
        this.itemsToTradeA = itemsToTradeA;
        this.userA = userA;
        this.userB = userB;
        this.allUsers = allUsers;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // setting up text for GUI.
        titleScreen.setText(TradeMenu.TRADELEND);
        typesOfTrade.setText(TradeMenu.TRADETYPE);
        temporary.setText(TradeMenu.TEMP);
        permanent.setText(TradeMenu.PERM);
        submit.setText(TradeMenu.SUBMIT);
        returnToMainMenu.setText(TradeMenu.EXIT);

        permanent.setOnAction(this::permChoice);
        temporary.setOnAction(this::tempChoice);
        submit.setOnAction(actionEvent -> {
            try {
                submitAction(actionEvent);
            } catch (IncompleteTradeException error) {
                new PopUp(TradeMenu.ERROR);
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
        if ((primaryDate.getValue() != null) &&
                (timeOfTrade.getText() != null) &&
                (!typesOfTrade.getText().equals(TradeMenu.TRADETYPE))) {

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

        TradeRequestManager tradeRequest = new TradeRequestManager("User " + userA + " wants to trade with you.", userA, userA, userB, itemsToTradeA, new ArrayList<>(), perm);
        tradeRequest.setDateAndPlaceFirst(tradeDateTime, placeOfMeeting);

        allUsers.addUserMessage(userB, tradeRequest.getTradeRequest());

        new PopUp(TradeMenu.SUCCESS);
        exitProgram(actionEvent);

    }

    @FXML
    private void exitProgram(ActionEvent e) {
        Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
        s.close();
    }

}
