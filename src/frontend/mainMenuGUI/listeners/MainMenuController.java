package frontend.mainMenuGUI.listeners;

import frontend.mainMenuGUI.presenters.MainMenuPresenter;
import gateways.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import use_cases.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML private Button userLoginButton;
    @FXML private Button userSignUpButton;
    @FXML private Button adminLoginButton;
    @FXML private Button demoLoginButton;
    @FXML private Button exitButton;
    @FXML private Label errorMessage;

    private AdminManager adminManager;
    private UserManager userManager;
    private TradeManager tradeManager;
    private GlobalInventoryManager globalInventoryManager;
    private GlobalWishlistManager globalWishlistManager;

    private final String loginFXMLFile = "/frontend/mainMenuGUI/fxml_files/Login.fxml";

    private final String adminFilePath = "data/serializedAdmins.ser";
    private final String userFilePath = "data/serializedUsers.ser";
    private final String globalInventoryFilePath = "data/serializedGlobalInventory.ser";
    private final String adminMessagesFilePath = "data/serializedAdminMessages.ser";
    private final String globalWishlistFilePath = "data/serializedGlobalWishlist.ser";
    private final String tradeFilePath = "data/serializedUserTrades.ser";
    private final String dataFolderPath = "data/";
    private AdminAccountGateways adminAccountGateways;
    private UserGateway userGateway;
    private GlobalInventoryGateways globalInventoryGateways;
    private UserTradesGateway userTradesGateway;
    private GlobalWishlistGateway globalWishlistGateway;
    private AdminMessageGateway adminMessageGateway;

    private MainMenuPresenter mainMenuPresenter = new MainMenuPresenter();

    /**
     * Switches user's present screen to a selected one, which will be their topmost one.
     * @param otherScene String with filepath of the scene to move to
     * @param selectedOption ENUM storing user's option of menu to go to
     * @throws IOException If something is wrong with the filepath or file
     */
    // code for method goToOtherScene is similar to: https://www.youtube.com/watch?v=XCgcQTQCfJQ
    public void goToOtherScene(String otherScene, SelectedOption selectedOption) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(otherScene));

        loader.setController(new LoginController(selectedOption, userManager, tradeManager, adminManager,
                globalInventoryManager, globalWishlistManager));

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UNDECORATED);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    /**
     ** If the user presses the Log In button, passes user input for wanting to log in at the MainMenu to goToOtherScene
     * @throws IOException If something is wrong with the filepath or file
     */
    @FXML
    private void userLoginButtonPressed () throws IOException {
        errorMessage.setText(mainMenuPresenter.stringReset());
        goToOtherScene(loginFXMLFile, SelectedOption.USER_LOGIN);
    }

    /**
     * If the user presses the Sign Up button, passes user input for wanting to sign up at the MainMenu to goToOtherScene
     * @throws IOException If something is wrong with the filepath or file
     */
    @FXML
    private void userSignUpButtonPressed() throws IOException {
        errorMessage.setText(mainMenuPresenter.stringReset());
        goToOtherScene(loginFXMLFile, SelectedOption.USER_SIGNUP);
    }

    /**
     * If the admin presses the Log In button, passes user input for wanting to log in at the MainMenu to goToOtherScene
     * @throws IOException If something is wrong with the filepath or file
     */
    @FXML
    private void adminLoginButtonPressed() throws IOException {
        errorMessage.setText(mainMenuPresenter.stringReset());
        goToOtherScene(loginFXMLFile, SelectedOption.ADMIN_LOGIN);
    }
    /**
     * If the user the Program Demo button, passes user input for wanting to try demo at the MainMenu to goToOtherScene
     * @throws IOException If something is wrong with the filepath or file
     */
    @FXML
    private void programDemoButtonPressed() throws IOException {
        errorMessage.setText(mainMenuPresenter.stringReset());
        goToOtherScene(loginFXMLFile,SelectedOption.DEMO_LOGIN);
    }

    /**
     * If the user presses the Exit button, passes user input for wanting to exit at the MainMenu to goToOtherScene
     * @param actionEvent store information regarding which method to call given a button press
     */
    // code for method closeButtonIsPushed is similar to: https://www.youtube.com/watch?v=i4Fk10U7Sks
    @FXML
    private void closeButtonIsPushed(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
        serialize();
    }

    /**
     * Initialize the scene's labels and buttons with desired text.
     * @param location stores location of path storing buttons and labels.
     * @param resources ResourceBundle to localize root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userLoginButton.setText(mainMenuPresenter.userLoginOption());
        userSignUpButton.setText(mainMenuPresenter.userSignUpOption());
        adminLoginButton.setText(mainMenuPresenter.adminLoginOption());
        demoLoginButton.setText(mainMenuPresenter.demoLoginOption());
        exitButton.setText(mainMenuPresenter.exitOption());
        errorMessage.setAlignment(Pos.CENTER);;

        deserialize();
        serialize();
    }

    /**
     * attempts to deserialize all previously stored objects including Admins, Users, the GlobalInventory, Messages
     * shared by all admins, the GlobalWishList, all UserTrades
     */
    private void deserialize(){
        try {

            GatewayBuilder gatewayBuilder = new GatewayBuilder();
            //deserialize admins
            adminAccountGateways = gatewayBuilder.getAdminAccountGateways(adminFilePath);

            if (adminAccountGateways.getAdminMap().isEmpty()){
                adminAccountGateways.beginAdminMap();
            }
            //deserialize users
            userGateway = gatewayBuilder.getUserGateway(userFilePath);

            //deserialize global inventory
            globalInventoryGateways = gatewayBuilder.getGlobalInventoryGateways(globalInventoryFilePath);

            //deserialize all user trades
            userTradesGateway = gatewayBuilder.getUserTradesGateway(tradeFilePath);

            //deserialize GlobalWishlistGateway
            globalWishlistGateway = gatewayBuilder.getGlobalWishlistGateway(globalWishlistFilePath);

            //deserialize AdminMessageGateway
            adminMessageGateway = gatewayBuilder.getAdminMessageGateways(adminMessagesFilePath);
        }
        catch(IOException | ClassNotFoundException ex) {
            deleteFile(adminFilePath);
            deleteFile(userFilePath);
            deleteFile(globalInventoryFilePath);
            deleteFile(adminMessagesFilePath);
            deleteFile(globalWishlistFilePath);
            deleteFile(tradeFilePath);

            deleteFile(dataFolderPath);
            new File(dataFolderPath).mkdirs();

            deserialize();
            errorMessage.setWrapText(true);
            errorMessage.setText(mainMenuPresenter.corruptedData());
            return;
        }

        UseCaseBuilder useCaseBuilder = new UseCaseBuilder();

        adminManager = useCaseBuilder.getAdminManager(adminAccountGateways.getAdminMap(),
                adminMessageGateway.getMessages());
        userManager = useCaseBuilder.getUserManager(userGateway.getMapOfUsers());
        tradeManager =
                useCaseBuilder.getTradeManager(userTradesGateway.getUserTrades());
        globalInventoryManager =
                useCaseBuilder.getGlobalInventoryManager(globalInventoryGateways.getGlobalInventory());
        globalWishlistManager =
                useCaseBuilder.getGlobalWishlistManager(globalWishlistGateway.getWishlistItems());
    }


    /**
     * attempts to serialize all objects used by the program including Admins, Users, the GlobalInventory, Messages
     * shared by all admins, the GlobalWishList, all UserTrades
     */
    private void serialize() {

        try {
            userGateway.writeToFile(userFilePath, userManager.getUserData());
            globalInventoryGateways.writeToFile(globalInventoryManager.getGlobalInventoryData());
            userTradesGateway.writeToFile(tradeFilePath, tradeManager.getTradeData());
            globalWishlistGateway.writeToFile(globalWishlistFilePath, globalWishlistManager.getGlobalWishlistData());
            adminAccountGateways.saveToFile(adminManager.getAdminData());
            adminMessageGateway.writeToFile(adminMessagesFilePath, adminManager.getAdminMessages());
        }
        catch (IOException e) {
            errorMessage.setText(mainMenuPresenter.savingError());
        }
    }

    // code for deleteFile is similar to: https://www.w3schools.com/java/java_files_delete.asp

    private boolean deleteFile (String fileToDelete) {
        File myObj = new File(fileToDelete);
        return myObj.delete();
    }

}
