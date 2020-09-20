package frontend.adminGUI.listeners;

import frontend.adminGUI.presenters.AdminGUIPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TradeUndoController implements Initializable {
    @FXML private Button searchUserButton;
    @FXML private Button goBackButton;
    @FXML private TextField userNameField;
    @FXML private Label invalidUserLabel;

    private String UndoUnstartedTradeMenuFXML = "/frontend/adminGUI/fxml_files/UndoUnstartedTradeMenu.fxml";
    private UserManager usermanager;
    private TradeManager tradeManager;
    private AdminGUIPresenter adminGUIPresenter;

    /**
     * Class constructor.
     * Create a new AdminSystem that allows admins to search a specific USer with Username and undo the on-going trade
     * of that User.
     * @param userManager the UserManager will be used to change user account information
     * @param tradeManager the TradeManager will be used to modify the on-going trades.
     */
    TradeUndoController(TradeManager tradeManager, UserManager userManager){
        this.usermanager = userManager;
        this.tradeManager = tradeManager;
        adminGUIPresenter = new AdminGUIPresenter();
    }


    private void openTradeWindow(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(adminGUIPresenter.undoUserTradeWindow());
        window.setMinWidth(800);
        window.setMinHeight(400);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(UndoUnstartedTradeMenuFXML));

        loader.setController(new UndoUnstartedTradeMenuController(userNameField.getText(), tradeManager, usermanager));

        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();


        window.setScene(scene);


        window.show();
    }

    private void searchUserButtonPushed(ActionEvent actionEvent) throws IOException {
        if(userNameField.getText().equals("")){
            invalidUserLabel.setText(adminGUIPresenter.userNameCannotBeEmpty());

        }
        else if(usermanager.isValidUser(userNameField.getText())){
            openTradeWindow(actionEvent);
        }
        else {
            invalidUserLabel.setText(adminGUIPresenter.InvalidUserNameLabel());
        }


    }
    private void close(ActionEvent actionEvent){
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invalidUserLabel.setText(" ");
        searchUserButton.setText(adminGUIPresenter.searchButtonText());
        searchUserButton.setOnAction(e -> {
            try {
                searchUserButtonPushed(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        goBackButton.setText(adminGUIPresenter.exitButton());
        goBackButton.setOnAction(this::close);


    }
}
