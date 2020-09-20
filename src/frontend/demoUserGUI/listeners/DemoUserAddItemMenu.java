package frontend.demoUserGUI.listeners;

import entities.Item;
import frontend.demoUserGUI.presenters.DemoUserfxPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import use_cases.DemoUserManager;

import java.net.URL;
import java.util.ResourceBundle;

public class DemoUserAddItemMenu implements Initializable {
    private DemoUserManager demoUserManager;
    private DemoUserfxPresenter demoUserPresenter = new DemoUserfxPresenter();


    @FXML private Button confirm;
    @FXML private Button cancel;
    @FXML private Label namePrompt;
    @FXML private Label descriptionPrompt;
    @FXML private TextField nameInput;
    @FXML private TextArea descriptionInput;
    @FXML private Label errorMessage;


    /**
     * Construct a new DemoUserAddItemMenu
     * @param demoUserManager DemoUserManager object
     */
    public DemoUserAddItemMenu(DemoUserManager demoUserManager) {
        this.demoUserManager = demoUserManager;
    }

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        namePrompt.setText(demoUserPresenter.enterItemName());
        descriptionPrompt.setText(demoUserPresenter.enterItemDescription());
        this.confirm.setText(demoUserPresenter.Confirm());
        this.cancel.setText(demoUserPresenter.menuPromptExit());

        // set button function
        this.confirm.setOnAction(e -> demoAddItem());
        this.cancel.setOnAction(this::returnToMainMenu);

    }

    /**
     * Add new items to demo user's personal inventory
     */
    private void demoAddItem(){
        Item item = new Item(nameInput.getText(), "Demo", descriptionInput.getText());
        demoUserManager.addToInventory(item);
        errorMessage.setText(demoUserPresenter.demoaddingitem());
        errorMessage.setWrapText(true);
    }

    /**
     * Exit the global inventory menu
     * @param event mouse click on Exit button
     */
    @FXML
    private void returnToMainMenu(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }






}
