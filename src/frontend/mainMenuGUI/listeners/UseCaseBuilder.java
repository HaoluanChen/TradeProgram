package frontend.mainMenuGUI.listeners;
import entities.*;
import use_cases.*;

import java.util.Map;
import java.util.List;

public class UseCaseBuilder {
    /**
     * build the AdminManager use case
     * @param adminList list of all admins
     * @param messageList list of all messages
     * @return AdminManager use case
     */
    public AdminManager getAdminManager(Map<String, Admin> adminList, List<Message> messageList){
        return new AdminManager(adminList, messageList);
    }
    /**
     * build the UserManager use case
     * @param userList list of all users
     * @return UserManager use case
     */
    public UserManager getUserManager(Map<String, User> userList){
        return new UserManager(userList);
    }
    /**
     * builds the TradeManager use case
     * @param tradeList list of all trades
     * @return TradeManager use case
     */
    public TradeManager getTradeManager(Map<String, List<Trade>> tradeList){
        return new TradeManager(tradeList);
    }
    /**
     * builds the GlobalInventoryManager use case
     * @param globalInventory list of all items in the global inventory
     * @return GlobalInventory use case
     */
    public GlobalInventoryManager getGlobalInventoryManager(GlobalInventory globalInventory){
        return new GlobalInventoryManager(globalInventory);
    }
    /**
     * builds the GlobalWishlistManager use case
     * @param globalWishlist list of all items in the global wishlist
     * @return GlobalWishlistManager use case
     */
    public GlobalWishlistManager getGlobalWishlistManager(GlobalWishlist globalWishlist){
        return new GlobalWishlistManager(globalWishlist);
    }
    /**
     * builds the DemoUserManager use case
     * @param username user's entered username
     * @param password user's entered password
     * @return DemoUserManager use case
     */
    public DemoUserManager getDemoUserManager(String username, String password){
        return new DemoUserManager(username, password);
    }

}
