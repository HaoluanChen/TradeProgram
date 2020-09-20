package frontend.adminGUI.listeners;
import entities.Admin;
import frontend.adminGUI.presenters.AdminGUIPresenter;
import frontend.messageReplyGUI.listeners.AdminMessageReplyGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import use_cases.AdminManager;
import use_cases.GlobalInventoryManager;
import use_cases.TradeManager;
import use_cases.UserManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable{

    @FXML private Button messageInboxButton;
    @FXML private Button manageAdminAccountButton;
    @FXML private Button userBrowsingButton;
    @FXML private Button tradeUndoButton;
    @FXML private Button exitButton;
    private Admin admin;

    AdminGUIPresenter adminGUIPresenter;
    private AdminManager adminManager;
    private TradeManager tradeManager;


    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;


    private String AdminAccountFXML = "/frontend/adminGUI/fxml_files/AdminAccount.fxml";
    private String TradeUndoFXML = "/frontend/adminGUI/fxml_files/TradeUndoMenu.fxml";
    private String AdminMessageGUI = "/frontend/messageReplyGUI/fxml_files/MessageGUI.fxml";
    private String AdminBrowsing = "/frontend/adminGUI/fxml_files/AdminBrowsingUsers.fxml";
    /**
     * Class constructor.
     * Create a new AdminSystem that allows admins to manage their message, to manage admin accounts, to manage User
     * accounts, and to undo the on-going trade.
     * @param admin the admin of the currently logged in.
     * @param adminManager the AdminManager will be used to change account information
     * @param userManager the UserManager will be used to change user account information
     * @param globalInventoryManager the GlobalInventory will be used to change item in GlobalInventory
     * @param tradeManager the TradeManager will be used to modify the on-going trades.
     */



    public AdminController(Admin admin, AdminManager adminManager,
                       UserManager userManager, GlobalInventoryManager globalInventoryManager, TradeManager tradeManager) {
        this.admin = admin;

        this.adminManager = adminManager;
        this.userManager = userManager;
        this.globalInventoryManager = globalInventoryManager;
        this.tradeManager = tradeManager;
        adminGUIPresenter = new AdminGUIPresenter();



    }


    private void messageInboxButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(adminGUIPresenter.adminMessageWindow());
        window.setMinHeight(400);
        window.setMinWidth(600);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AdminMessageGUI));

        loader.setController(new AdminMessageReplyGUI(adminManager,globalInventoryManager,
                userManager, admin.getUsername()));
        window.initStyle(StageStyle.UNDECORATED);

        Parent parent = loader.load();
        Scene scene = new Scene(parent);



        window.setScene(scene);


        window.show();


    }


    private void manageAdminAccountButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(adminGUIPresenter.adminAccountWindow());
        window.setMinWidth(600);
        window.setMinHeight(400);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AdminAccountFXML));
        window.initStyle(StageStyle.UNDECORATED);
        loader.setController(new AdminAccountController(admin, adminManager, userManager));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        window.setScene(scene);

        window.show();





    }

    private void userBrowsingButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(adminGUIPresenter.adminBrowsingWindow());
        window.setMinHeight(400);
        window.setMinWidth(600);
        window.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AdminBrowsing)
        );

        loader.setController(new AdminBrowsingUsersController(userManager, globalInventoryManager));

        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        window.setScene(scene);


        window.show();

    }

    private void tradeUndoButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(adminGUIPresenter.adminTradeUndoSearchWindow());
        window.setMinWidth(600);
        window.setMinHeight(400);
        window.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(TradeUndoFXML));

        loader.setController(new TradeUndoController(tradeManager, userManager));

        Parent parent = loader.load();
        Scene scene = new Scene(parent);



        window.setScene(scene);


        window.show();

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
        messageInboxButton.setText(adminGUIPresenter.messageInboxButton());
        manageAdminAccountButton.setText(adminGUIPresenter.adminAccountButton());
        userBrowsingButton.setText(adminGUIPresenter.userBrowsingButton());
        tradeUndoButton.setText(adminGUIPresenter.tradeUndoButton());
        messageInboxButton.setOnAction(e -> {
            try {
                messageInboxButtonPushed(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        exitButton.setText(adminGUIPresenter.exitButton());
        exitButton.setOnAction(this::close);
        manageAdminAccountButton.setOnAction(e -> {
            try {
                manageAdminAccountButtonPushed(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        tradeUndoButton.setOnAction(e -> {
            try {
                tradeUndoButtonPushed(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        userBrowsingButton.setOnAction(e -> {
            try {
                userBrowsingButtonPushed(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });




    }
}
