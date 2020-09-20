package frontend.userGUI.listeners;

import frontend.userGUI.presenters.UserPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserStatusPopUp implements Initializable {

    private String userFrozen;
    private String incompleteTrades;
    private String borrowsVLoans;
    private String tooManyTrades;

    @FXML private Label frozen;
    @FXML private Label incomplete;
    @FXML private Label borrows;
    @FXML private Label trades;
    @FXML private Button exit;

    private UserPresenter userPresenter;

    /**
     * Create the popup with all non frozen messages
     * @param errorMessages the array of error messages
     */
    public UserStatusPopUp(String[] errorMessages) {
        this.userPresenter = new UserPresenter();
        this.incompleteTrades = errorMessages[0];
        this.borrowsVLoans = errorMessages[1];
        this.tooManyTrades = errorMessages[2];
        this.userFrozen = " ";
    }

    /**
     * Create the popup listener with just the user frozen message
     */
    public UserStatusPopUp() {
        this.userPresenter = new UserPresenter();
        this.incompleteTrades = " ";
        this.borrowsVLoans = " ";
        this.tooManyTrades = " ";
        this.userFrozen = this.userPresenter.userAccountFrozen();
    }

    /**
     * Set button functionality/label text
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set text
        this.frozen.setText(this.userFrozen);
        this.incomplete.setText(this.incompleteTrades);
        this.borrows.setText(this.borrowsVLoans);
        this.trades.setText(this.tooManyTrades);
        this.exit.setText(this.userPresenter.menuPromptExit());

        // set up button functionality
        this.exit.setOnAction(this::exit);
    }

    /**
     * Close this window
     * @param actionEvent the ActionEvent object
     */
    private void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
