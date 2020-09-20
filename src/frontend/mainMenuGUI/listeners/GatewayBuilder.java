package frontend.mainMenuGUI.listeners;
import gateways.*;

import java.io.IOException;

public class GatewayBuilder {

    /**
     * @param filepath path where specific file is stored
     * @return AdminAccountGateway based on filepath
     * @throws IOException error if file moved
     * @throws ClassNotFoundException if class not found in filepath
     */
    public AdminAccountGateways getAdminAccountGateways(String filepath) throws IOException, ClassNotFoundException {
        return new AdminAccountGateways(filepath);
    }
    /**
     * @param filepath path where specific file is stored
     * @return AdminMessageGateway based on filepath
     * @throws IOException error if file moved
     * @throws ClassNotFoundException if class not found in filepath
     */
    public AdminMessageGateway getAdminMessageGateways(String filepath) throws IOException, ClassNotFoundException {
        return new AdminMessageGateway(filepath);
    }
    /**
     * @param filepath path where specific file is stored
     * @return UserGateway object based on filepath
     * @throws IOException error if file moved
     * @throws ClassNotFoundException if class not found in filepath
     */
    public UserGateway getUserGateway(String filepath) throws IOException, ClassNotFoundException {
        return new UserGateway(filepath);
    }
    /**
     * @param filepath path where specific file is stored
     * @return getUserGateway object based on filepath
     * @throws IOException error if file moved
     * @throws ClassNotFoundException if class not found in filepath
     */
    public UserTradesGateway getUserTradesGateway(String filepath) throws IOException, ClassNotFoundException {
        return new UserTradesGateway(filepath);
    }
    /**
     * @param filepath path where specific file is stored
     * @return getGlobalWishlistGateway object based on filepath
     * @throws IOException error if file moved
     * @throws ClassNotFoundException if class not found in filepath
     */
    public GlobalWishlistGateway getGlobalWishlistGateway(String filepath) throws IOException, ClassNotFoundException {
        return new GlobalWishlistGateway(filepath);
    }
    /**
     * @param filepath path where specific file is stored
     * @return getGlobalInventoryGateways based on filepath
     * @throws IOException error if file moved
     * @throws ClassNotFoundException if class not found in filepath
     */
    public GlobalInventoryGateways getGlobalInventoryGateways(String filepath) throws IOException, ClassNotFoundException {
        return new GlobalInventoryGateways(filepath);
    }


}
