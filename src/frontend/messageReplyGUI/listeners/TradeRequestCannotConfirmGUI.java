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
import javafx.stage.Stage;
import use_cases.MessageBuilder;
import use_cases.TradeRequestManager;
import use_cases.UserManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TradeRequestCannotConfirmGUI implements Initializable {
    @FXML private Label title;
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private Label messageContent;

    private TradeRequestManager tradeRequestManager;
    private List<Message> messages;
    private UserManager userManager;

    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();

    /**
     * Class constructor.
     * Create a new TradeRequestCannotConfirmGUI that allows the user to act when they cannot confirm a trade request
     * @param tradeRequestManager the trade request manager with the message in it
     * @param userManager the user manager of the system
     * @param messages the copyed message list from the source of the new item request
     */
    TradeRequestCannotConfirmGUI(TradeRequestManager tradeRequestManager, UserManager userManager,
                                         List<Message> messages){
        this.tradeRequestManager = tradeRequestManager;
        this.userManager = userManager;
        this.messages = messages;
    }

    private void exit(ActionEvent e){
        new PopUp(messageReplyPresenter.success());

        Stage s = (Stage)(((Node) e.getSource()).getScene().getWindow());
        s.close();
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(messageReplyPresenter.titleTradeRequestCannotConfirm());
        messageContent.setText(messageReplyPresenter.tradeRequestCannotConfirmPrompt(tradeRequestManager.getTradeRequest()));
        messageContent.setWrapText(true);
        button1.setText(messageReplyPresenter.keep());
        button1.setOnAction(this::exit);
        button2.setText(messageReplyPresenter.delete());
        button2.setOnAction(e -> {
            MessageBuilder messageBuilder = new MessageBuilder();
            Message message = tradeRequestManager.getTradeRequest();
            messages.remove(message);
            userManager.addUserMessage(message.getSender(),
                    messageBuilder.getSystemMessage("You or the other trader cannot create a " +
                            "new trade at this time or the items involved or not for trade at this time. " +
                            "The other trader has chosen to delete this trade request.\n"+
                            "Trade Request: "+message.toString()));
            exit(e);
        });
    }
}
