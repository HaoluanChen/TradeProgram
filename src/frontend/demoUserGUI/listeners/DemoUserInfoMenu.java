package frontend.demoUserGUI.listeners;

import frontend.demoUserGUI.presenters.DemoUserfxPresenter;
import frontend.popUp.PopUp;
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
import use_cases.DemoUserManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DemoUserInfoMenu implements Initializable {
    private Type type;
    private DemoUserManager demoUserManager;
    private DemoUserfxPresenter demoUserPresenter = new DemoUserfxPresenter();
    private String DemoUserInventoryFXML = "/frontend/userGUI/AccountInfo/fxml_files/BrowseThroughUserInventory.fxml";
    private String DemoUserWishListFXML = "/frontend/userGUI/AccountInfo/fxml_files/BrowseThroughUserWishlist.fxml";

    @FXML private Button tradeHistory;
    @FXML private Button newPassword;
    @FXML private Button tradePartners;
    @FXML private Button recentTrades;
    @FXML private Button inventory;
    @FXML private Button wishlist;
    @FXML private Button exit;
    @FXML private Label message;


    /**
     * a constructor for DemoUserInfoMenu
     * @param demoUserManager demoUserManager object
     */
    public DemoUserInfoMenu(DemoUserManager demoUserManager) {
        this.demoUserManager = demoUserManager;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.tradeHistory.setText(demoUserPresenter.accountInfoPromptTradeHistory());
        this.newPassword.setText(demoUserPresenter.accountInfoPromptPassword());
        this.tradePartners.setText(demoUserPresenter.accountInfoPromptTradingPartners());
        this.recentTrades.setText(demoUserPresenter.accountInfoPromptRecentTrades());
        this.inventory.setText(demoUserPresenter.accountInfoPromptInventory());
        this.wishlist.setText(demoUserPresenter.accountInfoPromptWishlist());
        this.exit.setText(demoUserPresenter.menuPromptExit());

        tradeHistory.setOnAction(e->noAccess());
        newPassword.setOnAction(e->noAccess());
        tradePartners.setOnAction(e->noAccess());
        recentTrades.setOnAction(e->noAccess());
        this.inventory.setOnAction(e -> viewInventory());
        this.wishlist.setOnAction(e -> viewWishlist());
        exit.setOnAction(this::exit);

    }

    enum Type {
        INVENTORY, WISHLIST
    }

    /**
     * Switches the scene being viewed
     * @param filename the filename of the .fxml file to be loaded
     * @throws IOException for a funky input
     */
    private void switchScene(String filename) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        switch(this.type) {
            case INVENTORY:
                loader.setController(new DemoUserInventory(demoUserManager));
                break;
            // view user wishlist
            case WISHLIST:
                loader.setController(new DemoUserWishlist(demoUserManager));
                break;
        }
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(newScene);
        window.show();
    }

    /**
     * view demo user inventory
     */
    @FXML
    private void viewInventory() {
        try{
            type = Type.INVENTORY;
            switchScene(this.DemoUserInventoryFXML);
        } catch (IOException e){
            new PopUp(demoUserPresenter.error());
        }
    }

    /**
     * view demo user wishlist
     */
    @FXML
    private void viewWishlist() {
        try{
            type = Type.WISHLIST;
            switchScene(this.DemoUserWishListFXML);
        } catch (IOException e){
            new PopUp(demoUserPresenter.error());
        }
    }

    /**
     * exit user from this menu
     * @param actionEvent mouse click on exit
     */
    @FXML
    private void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    /**
     * prints no access on screen
     */
    @FXML
    private void noAccess(){
        message.setText(demoUserPresenter.noAccess());

    }
}

