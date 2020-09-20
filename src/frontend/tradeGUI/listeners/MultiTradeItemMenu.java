package frontend.tradeGUI.listeners;

import entities.Item;
import frontend.tradeGUI.presenters.TradeMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import frontend.globalInventoryGUI.presenters.GlobalInventoryMenuPresenter;
import use_cases.GlobalInventoryManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MultiTradeItemMenu implements Initializable {
    private GlobalInventoryMenuPresenter globalInventoryMenuPresenter = new GlobalInventoryMenuPresenter();
    private String user;
    private GlobalInventoryManager globalInventoryManager;
    private ObservableList<Item> selectedItems = FXCollections.observableArrayList();
    private ObservableList<Item> userItems = FXCollections.observableArrayList();

    @FXML private TableView<Item> userItem;
    @FXML private TableColumn<Item, String> itemName;
    @FXML private TableColumn<Item,String> itemOwner;
    @FXML private TableColumn<Item, String> itemDescription;

    @FXML private TableColumn<Item, String> tradingitemName;
    @FXML private TableColumn<Item,String> tradingitemOwner;
    @FXML private TableColumn<Item, String> tradingitemDescription;
    @FXML private TableView<Item> tradingItem;
    @FXML private Button select;
    @FXML private Button remove;
    @FXML private Button trade;
    @FXML private Label title;
    @FXML private Label message;
    @FXML private Label userItemLabel;
    @FXML private Label tradingItemLabel;
    @FXML private Button exit;

    /**
     * A constructor that is used to allow the given user to select whatever items
     * userB has. Note: globalInventoryManager allows access to userB's inventory; therefore,
     * this variable should not be empty.
     * @param user is a String that represents the current trading user.
     * @param globalInventoryManager is a GlobalInventoryManager that contains all inventory information.
     */
    public MultiTradeItemMenu(String user, GlobalInventoryManager globalInventoryManager) {
        this.user = user;
        this.globalInventoryManager = globalInventoryManager;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userItemLabel.setText(TradeMenu.inventoryPrompt(user));
        tradingItemLabel.setText(TradeMenu.SELECT_ITEM);
        title.setText(TradeMenu.INVENTORY_PROMPT);
        itemName.setText(globalInventoryMenuPresenter.itemName());
        itemDescription.setText(globalInventoryMenuPresenter.itemDescription());
        select.setText(globalInventoryMenuPresenter.select());
        remove.setText(globalInventoryMenuPresenter.remove());
        trade.setText(globalInventoryMenuPresenter.trade());
        exit.setText(globalInventoryMenuPresenter.menuPromptExit());

        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>(globalInventoryMenuPresenter.name()));
        itemDescription.setCellValueFactory(new PropertyValueFactory<Item, String>(globalInventoryMenuPresenter.description()));

        tradingitemName.setText(globalInventoryMenuPresenter.itemName());
        tradingitemDescription.setText(globalInventoryMenuPresenter.itemDescription());
        tradingitemName.setCellValueFactory(new PropertyValueFactory<Item, String>(globalInventoryMenuPresenter.name()));
        tradingitemDescription.setCellValueFactory(new PropertyValueFactory<Item, String>(globalInventoryMenuPresenter.description()));

        userItem.setOnMouseClicked(this::selected);

        exit.setOnAction(this::exit);
        //load data
        loadData();
        userItem.setItems(userItems);
        tradingItem.setItems(getItem());
        select.setOnAction(this::select);
        remove.setOnAction(this::remove);

        trade.setOnAction(this::tradeRequest);
    }


    private void loadData(){
        List<Item> userItemList =  globalInventoryManager.getPersonInventory(user);
        userItems.addAll(userItemList);
    }

    private ObservableList<Item> getItem(){
        return selectedItems;
    }

    /**
     * A getter to return the items that were selected by the user.
     * @return an ArrayList of items the user selected.
     */
    public List<Item> getItems(){
        return new ArrayList<>(getItem());
    }

    private void selected(javafx.scene.input.MouseEvent mouseEvent) {
        Item itemselected = userItem.getSelectionModel().getSelectedItem();
        if (itemselected == null){
            message.setText(globalInventoryMenuPresenter.noItemSelected());
        }
        else {
            message.setText(TradeMenu.itemSelected(itemselected.getName()));
        }
    }

    private void select(ActionEvent event){
        Item itemselected = userItem.getSelectionModel().getSelectedItem();
        if (!(itemselected == null)) {
            userItem.getItems().remove(itemselected);
            tradingItem.getItems().add(itemselected);
        }
    }

    private void remove(ActionEvent event){
        Item itemselected = tradingItem.getSelectionModel().getSelectedItem();
        if (!(itemselected == null)) {
            tradingItem.getItems().remove(itemselected);
            userItem.getItems().add(itemselected);
        }
    }

    private void tradeRequest(ActionEvent event){
        if (selectedItems.size() > 0){
            exit(event);
        }
        else message.setText(TradeMenu.NOITEMS);

    }

    @FXML
    private void exit(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

}
