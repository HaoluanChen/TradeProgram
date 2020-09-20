package frontend.messageReplyGUI.listeners;

import entities.Message;
import frontend.messageReplyGUI.presenters.MessageReplyPresenter;
import frontend.popUp.PopUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import use_cases.TradeRequestManager;
import use_cases.UserManager;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;

public class TradeRequestEditGUI implements Initializable {
    @FXML private Label title;
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private Label dateLabel;
    @FXML private Label placeLabel;
    @FXML private TextField dateTextField;
    @FXML private TextField placeTextField;

    private TradeRequestManager tradeRequestManager;
    private String accountUsername;
    private UserManager userManager;
    private List<Message> messages;
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();

    /**
     * Class constructor.
     * Create a new TradeRequestEditGUI that allows the user to edit a trade request
     * @param tradeRequestManager the trade request manager with the message in it
     * @param userManager the user manager of the system
     * @param accountUsername the username of the user that is currently login
     * @param messages the copyed message list from the source of the new item request
     */
    TradeRequestEditGUI (TradeRequestManager tradeRequestManager, UserManager userManager,
                                List<Message> messages,
                                String accountUsername){
        this.userManager = userManager;
        this.tradeRequestManager = tradeRequestManager;
        this.accountUsername = accountUsername;
        this.messages =messages;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(messageReplyPresenter.titleTradeRequestEdit());
        button2.setText(messageReplyPresenter.exit());
        button2.setOnAction(this::exit);
        button1.setText(messageReplyPresenter.edit());
        button1.setOnAction(this::editTradeRequest);
        dateLabel.setText(messageReplyPresenter.datePrompt(tradeRequestManager.getTradeRequest().getDate().toString()));
        placeLabel.setText(messageReplyPresenter.placePrompt(tradeRequestManager.getTradeRequest().getPlace()));
        dateTextField.setText(messageReplyPresenter.emptyString());
        placeTextField.setText(messageReplyPresenter.emptyString());
    }

    private void exit(ActionEvent e){
        Stage s = (Stage)(((Node) e.getSource()).getScene().getWindow());
        s.close();
    }

    private void editTradeRequest(ActionEvent e){
        String place = placeTextField.getText();
        String date = dateTextField.getText();
        LocalDateTime newDate = tradeRequestManager.getTradeRequest().getDate();
        String newPlace = tradeRequestManager.getTradeRequest().getPlace();
        String receiver = tradeRequestManager.getTradeRequest().getSender();

        if(date.length() != 0) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                newDate = LocalDateTime.parse(date, dtf);
            } catch (DateTimeParseException ex) {
                new PopUp(messageReplyPresenter.wrongFormat());
                return;
            }

            if (newDate.isBefore(LocalDateTime.now())) {
                new PopUp(messageReplyPresenter.enterDateInPast());
                return;
            }
        }

        else if(place.length() != 0){
            newPlace = place;
        }
        else{
            new PopUp(messageReplyPresenter.noEdit());
            return;
        }
        messages.remove(tradeRequestManager.getTradeRequest());
        tradeRequestManager.setDateAndPlace(accountUsername, newDate, newPlace);
        userManager.addUserMessage(receiver, tradeRequestManager.getTradeRequest());
        new PopUp(messageReplyPresenter.success());
        exit(e);
    }

}
