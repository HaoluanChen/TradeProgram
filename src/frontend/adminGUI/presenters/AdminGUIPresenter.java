package frontend.adminGUI.presenters;

import entities.Admin;

public class AdminGUIPresenter {


    /**
     * Name of the Admin Browsing Window
     * @return the name of the Admin Browsing Window
     */

    public String adminBrowsingWindow(){
        return "User Account Management";

    }
    /**
     * Name of the Admin Account  Management Window
     * @return the name of the Admin Admin Account  Management Window
     */

    public String adminAccountWindow(){
        return "Admin Account Management";

    }
    /**
     * Name of the window for admin to change password
     * @return the name of the window for admin to change password
     */

    public String adminPasswordChangeWindow(){
        return ("Go set a new password!");

    }
    /**
     * Name of the window for admin to create a new admin account
     * @return the name of the window for admin to create a new admin account
     */
    public String adminAccountCreationWindow(){
        return ("Go create a new pal!");

    }

    /**
     * Name of the window for admin to search for the User to undo the on-going trade
     * @return the name of the window for admin to search for the User to undo the on-going trade
     */

    public String adminTradeUndoSearchWindow(){
        return "Search for the User whom you want to undo the trade for";

    }
    /**
     * Name of the window for admin to undo the on-going trades.
     * @return the name of the window for admin to undo the on-going trades.
     */

    public String undoUserTradeWindow(){
        return "User's Unstarted Trades";

    }
    /**
     * Name of the window for admin to check his mailbox.
     * @return the name of the window for admin to check his mailbox.
     */

    public String adminMessageWindow(){
        return "Admin Message Inbox";
    }

    //Label and Text

    /**
     * Label Text
     * @return the text of the label to tell admin that he searched an invalid User Name.
     */


    public String InvalidUserNameLabel(){
        return "invalid User Name";
    }
    /**
     * Label Text
     * @return the text of the label to tell admin that the UserName cannot be empty.
     */

    public String userNameCannotBeEmpty(){
        return "User name cannot be empty";

    };
    /**
     * Label Text
     * @return the text of the label to tell admin that the new password cannot be empty.
     */

    public String passwordCannotBeEmpty(){
        return "Password cannot be empty";

    };
    /**
     * Label Text
     * @param admin the admin that is currently logging in.
     * @return the text of the label to tell admin that the new password have been changed.
     */


    public String newPasswordCreated(Admin admin){
        return "Congrats! your password has been changed to " + admin.getPassword();
    }
    /**
     * Label Text
     * @return the text of the label to tell admin that the new admin has been created.
     */

    public String newAdminCreated(){ return "Success, admin account created";

    }
    /**
     * Label Text
     * @return the text of the label to tell admin that he failed to create a new admin.
     */

    public String AdminCreationFailed(){
        return "Failed to create new admin because the Username has been taken";
    }
    /**
     * Label Text
     * @return the text of the label to tell admin that he failed to change the password.
     */

    public String newPasswordNotSaved(){
        return "Two passwords don't match, failed to change your password";
    }

    //Button

    /**
     * Button Text
     * @return the name of the button used to exit the current window.
     */

    public String exitButton(){
        return  "Back";
    }
    /**
     * Button Text
     * @return the name of the button used to search UserName.
     */

    public String searchButtonText(){
        return "search";
    }
    /**
     * Button Text
     * @return the name of the button used to save changes.
     */

    public String saveChangeButtonText(){
        return "Save Changes";
    }
    /**
     * Button Text
     * @return the name of the button used to enter the window to add a new admin account.
     */

    public String adminCreationButtonText(){
        return "Add a new admin account";
    }
    /**
     * Button Text
     * @return the name of the button used to enter the window to change password.
     */

    public String adminChangePasswordButton(){
        return "Change your password";
    }
    /**
     * Button Text
     * @return the button name that allows admin to check mailbox.
     */


    public String messageInboxButton(){
        return "Check your message inbox";

    }
    /**
     * Button Text
     * @return the button name that allows admin to manage admin accounts.
     */
    public String adminAccountButton(){
        return "Manage Admin account";
    }
    /**
     * Button Text
     * @return the button name that allows admin to browse users.
     */
    public String userBrowsingButton(){
        return "Access the information of Users";
    }
    /**
     * Button Text
     * @return the button name that allows admin to undo the Trade.
     */

    public String tradeUndoButton(){
        return "Undo the trade of Users";
    }
    /**
     * Button Text
     * @return the button name that allows admin to delete the on-going trade.
     */

    public String deleteSelectedTradeButton(){
        return "delete selected Trade!";
    }
    /**
     * Button Text
     * @return the button name that allows admin to confirm the new admin info and create it.
     */

    public String newAdminButton(){
        return "Create a new Admin!";
    }





}
