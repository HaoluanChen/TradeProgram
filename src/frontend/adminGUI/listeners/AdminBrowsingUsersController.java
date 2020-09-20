package frontend.adminGUI.listeners;

import frontend.adminGUI.presenters.AdminBrowsingUsersPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import use_cases.GlobalInventoryManager;
import use_cases.UserManager;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminBrowsingUsersController implements Initializable {

    @FXML private TextField usernameText;
    @FXML private TextField optionText;
    @FXML private Button searchButton;
    @FXML private Button lendingButton;
    @FXML private Button freezeButton;
    @FXML private Button exitButton;
    @FXML private Button banButton;
    @FXML private Button weeklyButton;
    @FXML private Button incompleteButton;
    @FXML private Button optionButton;
    @FXML private Button undoButton;
    @FXML private Label mainLabel;
    @FXML private Label userLabel;
    @FXML private Label allLabel;
    @FXML private Label usernameLabel;

    private UserManager users;
    private AdminBrowsingUsersPresenter browse;
    private GlobalInventoryManager globalinventory;
    private String user = "";
    private boolean lendingLimit = false;
    private boolean weeklyLimit = false;
    private boolean incompleteLimit = false;
    private boolean allLimit;

    /**
     * Construct an instance, takes in an usermanager object, creates a AdminBrowsingUsersPresenter.
     *
     * @param system usermanager instance
     * @param globalinventory globalinventory instance
     */
    public AdminBrowsingUsersController(UserManager system, GlobalInventoryManager globalinventory) {
        this.browse = new AdminBrowsingUsersPresenter();
        this.users = system;
        this.globalinventory = globalinventory;
    }


    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from
     * Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location
     *                 is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userLabel.setText(browse.enterUsername());
        searchButton.setText(browse.searchText());
        banButton.setText(browse.banUnbanText());
        freezeButton.setText(browse.freezeText());
        weeklyButton.setText(browse.weeklyText());
        incompleteButton.setText(browse.incompleteText());
        lendingButton.setText(browse.lendingText());
        optionButton.setText(browse.enterText());
        exitButton.setText(browse.exitText());
        undoButton.setText(browse.undoText());
        allLabel.setText(browse.thresholdText());
        usernameLabel.setText(browse.enterUsername());

        allLimit = true;
        allLabel.setVisible(true);
        undoButton.setVisible(false);
        freezeButton.setVisible(false);
        banButton.setVisible(false);
        userLabel.setVisible(false);
        mainLabel.setText("");
        undoButton.setOnAction(e -> undoDelete());
        exitButton.setOnAction(this::exit);
        lendingButton.setOnAction(e -> lending());
        freezeButton.setOnAction(e -> freeze());
        searchButton.setOnAction(e -> search());
        banButton.setOnAction(e -> ban());
        weeklyButton.setOnAction(e -> weeklyLimit());
        incompleteButton.setOnAction(e -> incompleteLimit());
        optionButton.setOnAction(e -> optionInput());
        allLabel.setAlignment(Pos.CENTER);
        usernameLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    private void search() {
        String input = usernameText.getText();
        if (users.isValidUser(input)) {
            allLimit = false;
            user = input;
            allLabel.setVisible(false);
            undoButton.setVisible(true);
            lendingButton.setVisible(true);
            freezeButton.setVisible(true);
            weeklyButton.setVisible(true);
            incompleteButton.setVisible(true);
            banButton.setVisible(true);
            userLabel.setText(users.getUserInfo(user));
            userLabel.setVisible(true);
            mainLabel.setText(browse.optionPrompt());
        } else {
            mainLabel.setText(browse.invalidName());
        }
        mainLabel.setAlignment(Pos.CENTER);

    }


    @FXML
    private void incompleteLimit() {
        mainLabel.setText(browse.enterValuePrompt());
        incompleteLimit = true;
        mainLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    private void weeklyLimit() {
        mainLabel.setText(browse.enterValuePrompt());
        weeklyLimit = true;
        mainLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    private void ban() {
        if (users.getUserIsBanned(user)) {
            users.unFreezeUserAccount(user);
        } else {
            users.banUserAccount(user);
        }
        mainLabel.setText(browse.banStateChangeSuccess());
        userLabel.setText(users.getUserInfo(user));
        mainLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    private void freeze() {
        if (users.getUserFrozenStatus(user)) {
            users.unFreezeUserAccount(user);
        } else {
            users.freezeUserAccount(user);
        }
        mainLabel.setText(browse.freezeStateChangeSuccess());
        userLabel.setText(users.getUserInfo(user));
        mainLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    private void lending() {
        mainLabel.setText(browse.enterValuePrompt());
        lendingLimit = true;
        mainLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    private void undoDelete() {
        globalinventory.undoDeleteItem(user);
        mainLabel.setText(browse.deletedItemRestored());
        mainLabel.setAlignment(Pos.CENTER);
    }

    @FXML
    private void optionInput() {
        String limit = optionText.getText();
        if (limit.matches("\\d+")) {
            if (allLimit && weeklyLimit) {
                users.setWeeklyTrades(Integer.parseInt(limit));
                mainLabel.setText(browse.weeklyLimitChangeSuccess());
            }
            else if (allLimit && incompleteLimit) {
                users.setLimitOfIncompleteTrades(Integer.parseInt(limit));
                mainLabel.setText(browse.incompleteLimitChangeSuccess());
            }
            else if (allLimit && lendingLimit) {
                users.setNewThreshold(Integer.parseInt(limit));
                mainLabel.setText(browse.incompleteLimitChangeSuccess());
                mainLabel.setText(browse.thresholdChangeSuccess());
            }
            else if (weeklyLimit) {
                users.setWeeklyTradesForOneUser(user, Integer.parseInt(limit));
                mainLabel.setText(browse.weeklyLimitChangeSuccess());
                incompleteLimit = false;
                userLabel.setText(users.getUserInfo(user));
            } else if (incompleteLimit) {
                users.setLimitOfIncompleteTradesForOneUser(user, Integer.parseInt(limit));
                mainLabel.setText(browse.incompleteLimitChangeSuccess());
                incompleteLimit = false;
                userLabel.setText(users.getUserInfo(user));
            } else if (lendingLimit) {
                users.setNewThresholdForOneUser(user, Integer.parseInt(limit));
                mainLabel.setText(browse.thresholdChangeSuccess());
                lendingLimit = false;
                userLabel.setText(users.getUserInfo(user));
            }
        }
        else { mainLabel.setText(browse.wrongFormat());}
        mainLabel.setAlignment(Pos.CENTER);
    }
        @FXML
        private void exit (ActionEvent actionEvent){
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.close();
        }
    }