package frontend.demoUserGUI.listeners;

import entities.Item;
import frontend.demoUserGUI.presenters.DemoUserfxPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import use_cases.DemoUserManager;

import java.net.URL;
import java.util.ResourceBundle;

public class DemoUserWishlist implements Initializable {

    private DemoUserfxPresenter demoUserPresenter = new DemoUserfxPresenter();
    private DemoUserManager demoUserManager;
    private Item selectedItem;

    @FXML private Label systemMessage;
    @FXML private ListView<Item> itemList;
    @FXML private Button remove;
    @FXML private Button exit;
    @FXML private Button sendTradeRequest;

    /**
     * constructor for DemoUserWishlist
     * @param demoUserManager a demoUserManager object
     */
    public DemoUserWishlist(DemoUserManager demoUserManager) {
        this.demoUserManager = demoUserManager;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set up exit button
        this.exit.setText(this.demoUserPresenter.menuPromptExit());
        this.exit.setOnAction(this::exit);
        this.remove.setText(this.demoUserPresenter.menuPromptRemove());
        this.remove.setOnAction(e -> remove());
        this.sendTradeRequest.setText(this.demoUserPresenter.sendTradeReqeust());
        this.sendTradeRequest.setOnAction(e -> sendTradeRequest());

        // set up list view
        this.itemList.getItems().addAll(demoUserManager.getUserWishlist());
        this.itemList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                this.selectedItem = newValue);
    }

    /**
     * remove item from demo user wish-list
     */
    private void remove(){
        // if there are no items left
        if(this.itemList.getItems().size() == 0) {
            this.systemMessage.setText(demoUserPresenter.wishlistIsEmpty());
        }
        // if there is no item selected
        else if(this.selectedItem == null) {
            this.systemMessage.setText(demoUserPresenter.noItemSelected());
        }
        else {
            demoUserManager.getUserWishlist().remove(selectedItem);
            systemMessage.setText(demoUserPresenter.itemRemoved());
            }
            this.itemList.getItems().remove(this.selectedItem);
            this.selectedItem = null;
        }

    /**
     * exit demo user from their perosnal wish-list
     * @param actionEvent mouse click
     */
    private void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    /**
     * set no access text on screen when demo user select to send a trade request
     */
    private void sendTradeRequest(){
        systemMessage.setText(demoUserPresenter.noAccess());
    }
}
