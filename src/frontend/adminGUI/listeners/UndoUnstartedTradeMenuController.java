
package frontend.adminGUI.listeners;

import entities.*;
import frontend.adminGUI.presenters.AdminGUIPresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class UndoUnstartedTradeMenuController implements Initializable {
    @FXML private Button deleteTradeButton;
    @FXML private Button exitButton;
    @FXML
    private TableView<Trade> tableView;
    @FXML private TableColumn<Trade, String> column1;
    @FXML private TableColumn<Trade, String> column2;
    @FXML private TableColumn<Trade, ArrayList<Item>> column3;
    @FXML private TableColumn<Trade, ArrayList<Item>> column4;
    @FXML private TableColumn<Trade, LocalDateTime> column5;
    private String currentUserName;
    private AdminGUIPresenter adminGUIPresenter;

    private UserManager usermanager;
    private TradeManager tradeManager;
    /**
     * Class constructor.
     * Create a new AdminSystem that allows admins to undo the on-going trade of that User.
     * @param currentUserName the valid UserName of the User whose trade information is accessing by admin to
     *                        undo the on-going trade.
     * @param userManager the UserManager will be used to change user account information
     * @param tradeManager the TradeManager will be used to modify the on-going trades.
     */
    UndoUnstartedTradeMenuController(String currentUserName, TradeManager tradeManager, UserManager userManager){
        this.currentUserName = currentUserName;
        this.usermanager = userManager;
        this.tradeManager = tradeManager;
        adminGUIPresenter = new AdminGUIPresenter();
    }


    private void deleteTradeButtonPushed(){
        ObservableList<Trade> allTrades;
        Trade selectedRow;

        allTrades = tableView.getItems();
        selectedRow = tableView.getSelectionModel().getSelectedItem();

        allTrades.remove(selectedRow);
        tradeManager.removeTrade(selectedRow);
        Message tradeDeletedNotification = new SystemMessage("Your trade has been deleted.");
        usermanager.addUserMessage(selectedRow.getTraderA(), tradeDeletedNotification);
        usermanager.addUserMessage(selectedRow.getTraderB(), tradeDeletedNotification);
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
        tableView.setItems(getTrade());
        exitButton.setText(adminGUIPresenter.exitButton());
        exitButton.setOnAction(this::close);
        deleteTradeButton.setText(adminGUIPresenter.deleteSelectedTradeButton());
        column1.setCellValueFactory(new PropertyValueFactory<Trade, String>("TraderA"));
        column2.setCellValueFactory(new PropertyValueFactory<Trade, String>("TraderB"));
        column3.setCellValueFactory(new PropertyValueFactory<Trade, ArrayList<Item>>("traderAItemsToTrade"));
        column4.setCellValueFactory(new PropertyValueFactory<Trade, ArrayList<Item>>("traderBItemsToTrade"));
        column5.setCellValueFactory(new PropertyValueFactory<Trade, LocalDateTime>("startDate"));
        deleteTradeButton.setOnAction(e -> deleteTradeButtonPushed());

    }
    public ObservableList<Trade> getTrade(){
        ObservableList<Trade> unstartedTrade = FXCollections.observableArrayList();
        unstartedTrade.addAll(tradeManager.getUnstartTrades(currentUserName));
        return unstartedTrade;
    }


}

