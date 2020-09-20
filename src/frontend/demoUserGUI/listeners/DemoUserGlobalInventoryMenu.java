package frontend.demoUserGUI.listeners;

import entities.Item;
import frontend.demoUserGUI.presenters.DemoUserfxPresenter;
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
import use_cases.DemoUserManager;
import use_cases.GlobalInventoryManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DemoUserGlobalInventoryMenu implements Initializable {
    private DemoUserfxPresenter demoUserPresenter = new DemoUserfxPresenter();
    private GlobalInventoryManager globalInventoryManager;
    private DemoUserManager demoUserManager;
    private String DemoUserTradeMenuFXML = "/frontend/demoUserGUI/fxml_files/DemoUserTradeMenu.fxml";

    @FXML private TableView<Item> tableView;
    @FXML private TableColumn<Item, String> itemName;
    @FXML private TableColumn<Item,String> itemOwner;
    @FXML private TableColumn<Item, String> itemDescription;
    @FXML private Button addToWishlist;
    @FXML private Button trade;
    @FXML private Button exit;
    @FXML private Label message;

    // for testing

    /**
     * Construct a new DemoUserGlobalInventoryMenu
     * @param demoUserManager a demoUserManager object
     * @param globalInventoryManager a globalInventoryManager
     */
    public DemoUserGlobalInventoryMenu(DemoUserManager demoUserManager, GlobalInventoryManager globalInventoryManager) {
        this.globalInventoryManager = globalInventoryManager;
        this.demoUserManager = demoUserManager;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemName.setText(demoUserPresenter.itemName());
        itemDescription.setText(demoUserPresenter.itemDescription());
        addToWishlist.setText(demoUserPresenter.addToWishlist());
        trade.setText(demoUserPresenter.sendTradeReqeust());
        exit.setText(demoUserPresenter.menuPromptExit());

        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("description"));

        tableView.setOnMouseClicked(this::selected);
        //load data
        tableView.setItems(getItem());
        addToWishlist.setOnAction(e -> addToWishlist());
        exit.setOnAction(this::exit);
        trade.setOnAction(e -> tradeMenu());
    }

    /**
     * Switch to DemoUserTradeMenu
     * @param filename the filename of the DemoUserTradeMenu
     * @throws IOException something went wrong
     */
    private void switchScene(String filename) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        loader.setController(new DemoUserTradeMenu());// call trademenu
        Parent root = loader.load();
        Scene newScene= new Scene(root);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(newScene);
        window.show();
    }

    /**
     * get items in the global inventory
     * @return items in global inventory as an ObservableList
     */
    private ObservableList<Item> getItem(){
        ObservableList<Item> items = FXCollections.observableArrayList();
        List<String> itemids =  globalInventoryManager.getGlobalInventoryData().getItemIdCollection();
        for (Item i : globalInventoryManager.getItemsFromGI((ArrayList<String>)itemids)){
            if (!(i == null)) {
                items.add(i);
            }
        }
        if (items.size() == 0){
            message.setText(demoUserPresenter.emptyglobalinventory());
        }
        return items;
    }

    /**
     * check if user select an item
     * @param mouseEvent mouse click on the items in TableView
     */
    private void selected(javafx.scene.input.MouseEvent mouseEvent) {
        Item itemselected = tableView.getSelectionModel().getSelectedItem();
        if (itemselected == null){
            message.setText(demoUserPresenter.noItemSelected());
        }
        else {
            message.setText(demoUserPresenter.whatToDo(itemselected));
        }
    }

    /**
     * add user selected items to user wish-list
     */
    private void addToWishlist(){
        Item itemselected = tableView.getSelectionModel().getSelectedItem();
        if (itemselected == null) {
            message.setText(demoUserPresenter.noItemSelected());
        }
        else {message.setText(demoUserPresenter.addedToWishlist(itemselected));
            demoUserManager.addDemoWishlist(itemselected);}

    }

    /**
     * swtich demoUser to tradeMenu
     */
    private void tradeMenu() {
        Item itemselected = tableView.getSelectionModel().getSelectedItem();
        if (itemselected == null) {
            message.setText(demoUserPresenter.noItemSelected());
        } else {
            try {
                switchScene(this.DemoUserTradeMenuFXML);
            }
            catch (IOException ex) {
                new PopUp(demoUserPresenter.error());
            }
        }
    }

    /**
     * exit user from global inventory menu
     * @param event mouse click on exit button
     */
    @FXML
    private void exit(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }





}
