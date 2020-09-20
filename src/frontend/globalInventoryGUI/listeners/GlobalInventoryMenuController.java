package frontend.globalInventoryGUI.listeners;

import entities.Item;
import exceptions.UserFrozenException;
import frontend.globalInventoryGUI.presenters.GlobalInventoryMenuPresenter;
import frontend.popUp.PopUp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import use_cases.GlobalInventoryManager;
import use_cases.GlobalWishlistManager;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GlobalInventoryMenuController implements Initializable {
    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, String> itemName;
    @FXML private TableColumn<Item,String> itemOwner;
    @FXML private TableColumn<Item, String> itemDescription;
    @FXML private Button addToWishlist;
    @FXML private Button trade;
    @FXML private Button exit;
    @FXML private Label message;

    private GlobalInventoryManager globalInventoryManager;
    private GlobalInventoryMenuPresenter globalInventoryMenuPresenter= new GlobalInventoryMenuPresenter();
    private UserManager userManager;
    private String user;
    private TradeManager tradeManager;
    private GlobalWishlistManager globalWishlistManager;
    private String MultiItemMenuFXML = "/frontend/globalInventoryGUI/fxml_files/MultiItemMenu.fxml";

    /**
     * construct a new GlobalInventoryMenuController
     * @param user username of current user
     * @param globalInventoryManager GlobalInventoryManager object
     * @param userManager UserManager object
     * @param tradeManager TradeManager object
     * @param globalWishlistManager GlobalWishlistManager object
     */
    public GlobalInventoryMenuController(String user, GlobalInventoryManager globalInventoryManager, UserManager userManager,
                                         TradeManager tradeManager, GlobalWishlistManager globalWishlistManager) {
        this.globalInventoryManager = globalInventoryManager;
        this.userManager = userManager;
        this.tradeManager = tradeManager;
        this.globalWishlistManager = globalWishlistManager;
        this.user = user;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemName.setText(globalInventoryMenuPresenter.itemName());
        itemOwner.setText(globalInventoryMenuPresenter.itemOwner());
        itemDescription.setText(globalInventoryMenuPresenter.itemDescription());
        addToWishlist.setText(globalInventoryMenuPresenter.addToWishlist());
        trade.setText(globalInventoryMenuPresenter.sendTradeReqeust());
        exit.setText(globalInventoryMenuPresenter.menuPromptExit());

        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>(globalInventoryMenuPresenter.name()));
        itemOwner.setCellValueFactory(new PropertyValueFactory<Item, String>(globalInventoryMenuPresenter.ownerName()));
        itemDescription.setCellValueFactory(new PropertyValueFactory<Item, String>(globalInventoryMenuPresenter.description()));

        tableView.setOnMouseClicked(this::selected);
        //load data
        tableView.setItems(getItem());
        addToWishlist.setOnAction(e-> addToWishlist());
        exit.setOnAction(this::exit);
        trade.setOnAction(e-> {
            try {
                MultiItemMenu();
            } catch (UserFrozenException ex) {
                new PopUp(globalInventoryMenuPresenter.frozenAcc());
            }
        });
    }

    /**
     * A method for user to select items on screen
     * @param mouseEvent mouse click
     */
    private void selected(javafx.scene.input.MouseEvent mouseEvent) {
        Item itemselected = tableView.getSelectionModel().getSelectedItem();
        if (itemselected == null){
            message.setText(globalInventoryMenuPresenter.noItemSelected());
        }
        else {
            message.setText(globalInventoryMenuPresenter.whatToDo(itemselected));
        }
    }

    /**
     * to switch to MultiItemMenu when user clicked on trade button
     * @param filename file name of the MultiItemMenu FXML file
     * @param item the item user selected
     * @throws IOException some thing went wrong
     */
    private void switchScene(String filename, Item item) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        loader.setController(new MultiItemMenu(item, user, globalInventoryManager, userManager, globalWishlistManager));// call Multimenu
        Parent root = loader.load();
        Scene newScene= new Scene(root);
        Stage window = new Stage();
        window.initStyle(StageStyle.UNDECORATED);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(newScene);
        window.show();
    }

    /**
     * To load the data from globalInventory to a ObervableList for TableView
     * @return a ObservableList contains all items in gloabl inventory
     */
    private ObservableList<Item> getItem(){
        ObservableList<Item> items = FXCollections.observableArrayList();
        List<String> itemids =  globalInventoryManager.getGlobalInventoryData().getItemIdCollection();
        for (Item i : globalInventoryManager.getItemsFromGI((ArrayList<String>)itemids)){
            if (!(i == null)) {
                items.add(i);
            }
        }
        return items;
    }

    /**
     * Adding items to user wish-list
     */
    private void addToWishlist(){
        Item itemselected = tableView.getSelectionModel().getSelectedItem();
        if (itemselected == null) {
            message.setText(globalInventoryMenuPresenter.noItemSelected());
        }
        else if (globalWishlistManager.getPersonWishlist(user).contains(itemselected)){
            message.setText(globalInventoryMenuPresenter.alreadyHave());
        }
        else if (itemselected.getOwnerName().equals(user)){
            message.setText(globalInventoryMenuPresenter.ownItem());
        }
        else {
            message.setText(globalInventoryMenuPresenter.addedToWishlist(itemselected));
            globalWishlistManager.addWish(itemselected.getItemID(), user);
        }


    }

    /**
     * Switch to MultiItemMenu for user to select multiple items
     * @throws UserFrozenException user account is frozen
     */
    private void MultiItemMenu() throws UserFrozenException {
        Item itemselected = tableView.getSelectionModel().getSelectedItem();
        if (itemselected == null) {
            message.setText(globalInventoryMenuPresenter.noItemSelected());
        } else if (globalInventoryManager.getPersonInventory(user).contains(itemselected)){
            message.setText(globalInventoryMenuPresenter.ownItem());
        }
        else {
            if (userManager.getCanTrade(user, tradeManager.getBorrowedTimes(user),
                    tradeManager.getLendTimes(user), tradeManager.getIncompleteTimes(user),
                    tradeManager.numberOfTradesCreatedThisWeek(user))){

                try {
                    switchScene(MultiItemMenuFXML, itemselected);
                }
                catch (IOException ex) {
                    new PopUp(globalInventoryMenuPresenter.error());
                }
            }
            else message.setText(globalInventoryMenuPresenter.FrozenAcc());
        }
    }

    /**
     * Exit the global inventory menu
     * @param event mouse click on Exit button
     */
    @FXML
    private void exit(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

}
