package frontend.userGUI.AccountInfo.listeners;

import entities.Trade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import frontend.userGUI.presenters.UserPresenter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RecentTradesTradePartners implements Initializable {

    // instance variables
    private List<String> userTradeInfo;
    private UserPresenter userPresenter;

    // fxml stuff
    @FXML private Button exit;
    @FXML private ListView<String> tradeList;

    /**
     * Constructor for listener that handles recent trade viewing
     * @param recentTrades array of 3 trade objects
     */
    public RecentTradesTradePartners(Trade[] recentTrades) {
        this.userTradeInfo = new ArrayList<>();
        for(Trade trade : recentTrades) {
            if(trade != null) {
                this.userTradeInfo.add(trade.toString());
            }
        }
        this.userPresenter = new UserPresenter();
    }

    /**
     * Constructor for listener that handles frequent trade partner viewing
     * @param tradePartners array of frequent trade partner usernames
     */
    public RecentTradesTradePartners(String[] tradePartners) {
        this.userTradeInfo = new ArrayList<>();
        for(String partner : tradePartners) {
            if(partner != null) {
                this.userTradeInfo.add(partner);
            }
        }
        this.userPresenter = new UserPresenter();
    }

    /**
     * Sets up button functionality/list view object
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set up exit button
        this.exit.setText(this.userPresenter.menuPromptExit());
        this.exit.setOnAction(this::exit);

        // set up list view
        this.tradeList.getItems().addAll(this.userTradeInfo);
    }

    /**
     * Exit to AccountInfo menu
     * @param actionEvent the ActionEvent object
     */
    private void exit(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
