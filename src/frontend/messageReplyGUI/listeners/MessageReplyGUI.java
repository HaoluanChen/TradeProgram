package frontend.messageReplyGUI.listeners;

import frontend.messageReplyGUI.presenters.MessageReplyPresenter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import entities.*;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;

public abstract class MessageReplyGUI implements Initializable{
    @FXML private Label messageContent;
    @FXML private ButtonBar buttonBar;
    @FXML private ListView messageListView;
    @FXML private Button confirm;

    UserManager userManager;
    AdminManager adminManager;
    String accountUsername;

    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private MessageResponseFactory factory;
    private MessageResponse messageResponse;

    private HashMap<String, Message> formatter = new HashMap<>();

    /**
     * Class constructor.
     * Create a new MessageReplyGUI that controls and allows the user to reply to their messages
     * @param accountUsername the username of the currently logged in User
     * @param adminManager the admin manager of the system
     * @param userManager the user manager of the system
     * @param tradeManager the trade manager of the system
     * @param globalInventoryManager the global inventory manager of the system
     */
    MessageReplyGUI(AdminManager adminManager, GlobalInventoryManager globalInventoryManager,
                           TradeManager tradeManager, UserManager userManager, String accountUsername){
        this.adminManager = adminManager;
        this.userManager = userManager;
        this.accountUsername = accountUsername;

        //Setting up the message response factory
        factory = new MessageResponseFactory(adminManager, globalInventoryManager, tradeManager,
                userManager, accountUsername);
        factory.setMessageList(getMessage());
    }

    /**
     * Getter for a copy of the list of messages that this class will be responsible for
     * @return a copy of the list of messages that this class will be responsible for
     */
    public abstract List<Message> getMessage();

    /**
     * Method to save the new list of messages to the system
     * @param messages the message list to be saved
     */
    public abstract void saveMessage(List<Message> messages);

    private void setUp(){
        //Clearing the old buttons
        buttonBar.getButtons().clear();

        //Setting up the confirm button
        confirm.setText(messageReplyPresenter.confirm());
        confirm.setOnAction(e -> setUp());

        //Telling the user they have no message
        if(factory.getMessageList().isEmpty()){
            messageContent.setText(messageReplyPresenter.printNoMessages());
            Button button1 = new Button(messageReplyPresenter.exit());
            button1.setOnAction(this::exitGUI);
            buttonBar.getButtons().add(button1);
        }
        //Instructing the user on how to select a message to view
        else if(messageListView.getSelectionModel().getSelectedItems().isEmpty()){
            messageContent.setText(messageReplyPresenter.instructions());
            Button button1 = new Button(messageReplyPresenter.exit());
            button1.setOnAction(this::exitGUI);
            buttonBar.getButtons().add(button1);
        }
        //Showing the message
        else {
            String key = (String) messageListView.getSelectionModel().getSelectedItems().get(0);
            Message message = formatter.get(key);

            messageResponse = factory.getMessageResponse(message);
            String[] s = messageResponse.getActions();
            messageContent.setText(messageReplyPresenter.messageString(message));
            messageContent.setWrapText(true);

            //Adding the buttons for the unqiue actions
            Button[] buttons = new Button[s.length + 1];

            for (int i = 0; i < s.length; i++) {
                String action = s[i];
                buttons[i] = new Button(action);
                buttons[i].setWrapText(true);
                buttons[i].setOnAction(e -> {
                    messageResponse.doAction(action);
                    refresh();
                    setUp();
                });
            }

            //Button to exit menu
            buttons[s.length] = new Button(messageReplyPresenter.exit());
            buttons[s.length].setOnAction(this::exitGUI);

            //Adding the buttons to the window
            buttonBar.getButtons().addAll(buttons);
        }
    }

    private void refresh(){
        //Refreshing the new list of messages after possible addition or deletion
        List<Message> messageList = factory.getMessageList();
        messageListView.getItems().clear();

        for(int i = 1; i<=messageList.size(); i++){
            Message m = messageList.get(i-1);
            String key = messageReplyPresenter.messageStringSideBar(m, i);
            formatter.put(key, m);
            messageListView.getItems().add(key);
        }

        messageListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    private void exitGUI(ActionEvent e){
        //saving the new message list to the system
        saveMessage(factory.getMessageList());
        ((Stage) (((Node) e.getSource()).getScene().getWindow())).close();
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageContent.setWrapText(true);
        refresh();
        setUp();
    }
}
