package frontend.demoUserGUI.listeners;

import frontend.demoUserGUI.presenters.DemoUserfxPresenter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DemoUserTradeMenu implements Initializable {
    DemoUserfxPresenter demoUserPresenter = new DemoUserfxPresenter();
    @FXML private Label titleScreen;
    @FXML private TextField timeOfTrade;
    @FXML private TextField meetTrade;
    @FXML private MenuButton typesOfTrade;
    @FXML private MenuItem temporary;
    @FXML private MenuItem permanent;
    @FXML private MenuButton oneOrTwoWayTrade;
    @FXML private MenuItem oneWayTrade;
    @FXML private MenuItem twoWayTrade;
    @FXML private Button submit;
    @FXML private Button returnToMainMenu;
    @FXML private DatePicker primaryDate;
    @FXML private Label message;

    /**
     * Called to initialize a controller after its root element has been completely processed. (Java doc from Initializable)
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleScreen.setText(demoUserPresenter.trade());
        typesOfTrade.setText(demoUserPresenter.whattype());
        temporary.setText(demoUserPresenter.temp());
        permanent.setText(demoUserPresenter.perm());
        oneOrTwoWayTrade.setText(demoUserPresenter.oneOrTwo());
        oneWayTrade.setText(demoUserPresenter.oneWay());
        twoWayTrade.setText(demoUserPresenter.twoWay());
        submit.setText(demoUserPresenter.submit());
        returnToMainMenu.setText(demoUserPresenter.back());
        returnToMainMenu.setOnAction(this::exit);
        submit.setOnAction(e->demoTrade());
        permanent.setOnAction(e->permChoice());
        temporary.setOnAction(e->tempChoice());
        oneWayTrade.setOnAction(e->oneWayChoice());
        twoWayTrade.setOnAction(e->twoWayChoice());
    }

    /**
     * exit user from tradeMenu
     * @param event mouse click on exit button
     */
    @FXML
    private void exit(ActionEvent event) {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    /**
     * setup text on screen when user select perm trade
     */
    @FXML
    private void permChoice() {
        typesOfTrade.setText(permanent.getText());
    }

    /**
     * setup text on screen when user select temp trade
     */
    @FXML
    private void tempChoice() {
        typesOfTrade.setText(temporary.getText());
    }

    /**
     * ask user to create a account to trade when user click on trade
     */
    private void demoTrade(){
        message.setText(demoUserPresenter.createAcc());
    }

    /**
     * setup text on screen when user click on one way trade
     */
    @FXML
    private void oneWayChoice() {
        oneOrTwoWayTrade.setText(oneWayTrade.getText());
    }

    /**
     * setup text on screen when user click on two way trade
     */
    @FXML
    private void twoWayChoice() {
        oneOrTwoWayTrade.setText(twoWayTrade.getText());
    }
}

