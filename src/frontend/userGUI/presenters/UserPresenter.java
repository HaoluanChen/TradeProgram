package frontend.userGUI.presenters;

public class UserPresenter {

    /**
     * Print user is frozen message.
     * @return you are frozen!
     */
    public String userAccountFrozen() {
        return "Your account has been frozen, and you are unable to trade.\n";
    }

    /**
     * Prompt for the Unconfirmed trades menu.
     * @return unconfirmed trades prompt
     */
    public String promptUserToConfirmTrades() {
        return "Here are all of your unconfirmed trades. Please confirm any that are complete.\n";
    }

    /**
     * AccountInfo button
     * @return Access account info
     */
    public String userMenuPromptAccountInfo() {
        return "Access your account information";
    }

    /**
     * GlobalInventory button
     * @return access global inventory
     */
    public String userMenuPromptGlobalInventory() {
        return "Browse through the global inventory";
    }

    /**
     * LoanMenu button
     * @return loan one of your items
     */
    public String userMenuPromptLoanMenu() {
        return "Loan one of your items to another user";
    }

    /**
     * Message menu button
     * @return look at your inbox
     */
    public String userMenuPromptMessageMenu() {
        return "Look at your message inbox";
    }

    /**
     * NewItem button
     * @return create a new item
     */
    public String userMenuPromptNewItem() {
        return "Add a new item to the system";
    }

    /**
     * Unfreeze button
     * @return send unfreeze request
     */
    public String userMenuPromptUnfreeze() {
        return "Send admins an unfreeze request";
    }

    /**
     * PrivateMessage button
     * @return send a private message
     */
    public String userMenuPromptPrivateMessage() {
        return "Send a private message to a user";
    }

    /**
     * exit button
     * @return exit and log out
     */
    public String userMenuPromptLogout() {
        return "Exit and log out";
    }

    /**
     * The prompt for new item prompt.
     * @param select 0 for item name input prompt, 1 for item's description prompt.
     * @return create new item prompt
     */
    public String createNewItemPrompt(int select) {
        if(select == 0) {
            return "Enter in the item's name.";
        }
        else {
            return "Enter the item's description.";
        }
    }

    /**
     * Prompt for setting a new password.
     * @return password prompt
     */
    public String setNewPasswordPrompt() {
        return "Enter in your new password.";
    }

    /**
     * Reenter new password
     * @return password confirmation
     */
    public String setNewPasswordPromptSecundus() {
        return "Reenter your new password";
    }

    /**
     * Remove button
     * @return remove
     */
    public String menuPromptRemove() {
        return "Remove";
    }

    /**
     * Exit button
     * @return exit
     */
    public String menuPromptExit() {
        return "Exit";
    }


    /**
     * Wishlist trade offer prompt
     * @return send a trade offer to this user
     */
    public String wishlistPromptTradeOffer() {
        return "Send a trade offer";
    }

    /**
     * Prints that a collection (inventory, wishlist, etc.) is empty
     * @param collection the collection name
     * @return your thing is empty
     */
    public String isEmpty(String collection) {
        return "Your " + collection + " is empty!\n";
    }

    /**
     * Prompt for item removal
     * @return item removed
     */
    public String itemRemoved() {
        return "Item removed.\n";
    }

    /**
     * Prompt for too many incomplete trades.
     * @return too many incomplete trades
     */
    public String tooManyIncompleteTrades() {
        return "You have too many incomplete trades, \nand your account has been requested to be frozen.";
    }

    /**
     * Prompt for too many borrows and loans.
     * @param difference the difference of borrows - loans
     * @return you have X more borrows than loans
     */
    public String tooManyBorrowsVLoans(int difference) {
        return "You have " + difference +  " more borrows than loans! \nYour account has been requested to be frozen.";
    }

    /**
     * Prompt for too many trade offers made this week.
     * @return too many trade offers made this week
     */
    public String tooManyTradesThisWeek() {
        return "You made too many trade offers this week! You can trade again next week.\n";
    }

    /**
     * Prompt for no trading partners.
     * @return no trading partners
     */
    public String noTradingPartners() {
        return "You don't have any frequent trading partners yet!\n";
    }

    /**
     * TradeHistory button
     * @return view trade history
     */
    public String accountInfoPromptTradeHistory() {
        return "View your trade history";
    }

    /**
     * Password button
     * @return set new password
     */
    public String accountInfoPromptPassword() {
        return "Set new password";
    }

    /**
     * Trading partners button
     * @return view frequent trading partners
     */
    public String accountInfoPromptTradingPartners() {
        return "View your three most frequent trading partners";
    }

    /**
     * Recent trades button
     * @return view recent trades
     */
    public String accountInfoPromptRecentTrades() {
        return "View your three most recent trades";
    }

    /**
     * Inventory button
     * @return view user inventory
     */
    public String accountInfoPromptInventory() {
        return "View your inventory";
    }

    /**
     * Wishlist button
     * @return view user wishlist
     */
    public String accountInfoPromptWishlist() {
        return "View your wishlist";
    }

    /**
     * Prompt for a new item message successfully sent to admins.
     * @return new item creation successful
     */
    public String newItemMessageSentToAdmin() {
        return "Item approval request sent. You can exit now.\n";
    }

    /**
     * Confirm prompt
     * @return the meeting happened
     */
    public String confirmMeetingPrompt() {
        return "Confirm";
    }

    /**
     * Deny prompt
     * @return the meeting did not happen
     */
    public String denyMeetingPrompt() {
        return "Deny";
    }

    /**
     * No more meetings to confirm
     * @return all unconfirmed meetings confirmed
     */
    public String unconfirmedMeetingAllDone() {
        return "All unconfirmed trades addressed. You can exit now.";
    }

    /**
     * Prompt to tell user that they have no recent trades.
     * @return no recent trades
     */
    public String noRecentTrades() {
        return "You have no recent trades!" + "\n";
    }

    /**
     * Confirmation of unfreeze request message success.
     * @return unfreeze request sent
     */
    public String unfreezeRequestSent() {
        return "Admins have been sent an unfreeze request!\n";
    }

    /**
     * Confirm that the user is not frozen.
     * @return not frozen
     */
    public String userNotFrozen() {
        return "You are not frozen right now.\n";
    }

    /**
     * If user attempts to loan but has not items in personal inventory
     * @return nothing to lend
     */
    public String emptyPersonalInventoryWhileLoaning() {
        return "You have nothing to lend!\n";
    }

    /**
     * If none of the user's items are wanted by others
     * @return none of your items in other wishlist
     */
    public String itemNotInOtherUsersWishlist() {
        return "None of your items exists in another user's wishlist. :(\n";
    }

    /**
     * If the global inventory is empty.
     * @return nothing in global inventory
     */
    public String emptyGlobalInventory() {
        return "There are no items in the global inventory! Add some of your own! :)";
    }

    /**
     * User loan prompt
     * @return would you like to loan this item
     */
    public String userLoanPrompt() {
        return "Would you like to loan this item to this user?";
    }

    /**
     * Loan menu item label
     * @return item label
     */
    public String userLoanPromptOfferedItemLabel() {
        return "Your offered item:";
    }

    /**
     * Loan menu recipient label
     * @return recipient label
     */
    public String userLoanPromptRecipientLabel() {
        return "Recipient User:";
    }

    /**
     * Loan menu confirm button
     * @return confirm text
     */
    public String userLoanPromptConfirm() {
        return "Confirm.";
    }

    /**
     * Loan menu deny button
     * @return deny text
     */
    public String userLoanPromptCancel() {
        return "Cancel.";
    }

    /**
     * Message successfully sent
     * @return message sent
     */
    public String userPrivateMessageConfirmation() {
        return "Message sent. It is safe to exit.";
    }

    /**
     * Notify user on return to main menu.
     * @return return to main menu
     */
    public String returnToMainMenu() {
        return "Returning to main menu.";
    }

    /**
     * Message prompt
     * @return user input prompt
     */
    public String userMessagePrompt() {
        return "Who would you like to send a message to?";
    }

    /**
     * Message message prompt
     * @return message input prompt
     */
    public String userMessagePromptSecundus() {
        return "What would you like to say?";
    }

    /**
     * Invalid username
     * @return not a valid username
     */
    public String invalidUsername() {
        return "This is not a valid username, please try again.";
    }

    /**
     * Invalid message input
     * @return fill out both text fields
     */
    public String invalidMessageInput() {
        return "Please fill out both text fields.";
    }

    /**
     * invalid password input
     * @return 2 mismatching passwords
     */
    public String invalidPasswordInputs() {
        return "Passwords must match.";
    }

    /**
     * Password changed
     * @return password changed
     */
    public String passwordConfirmation() {
        return "Password successfully changed.";
    }

    /**
     * Valid entry prompt
     * @return String valid entry
     */
    public String selectValidObject() {
        return "Please select a valid entry.";
    }

    /**
     * Inform user that an IOException has occurred
     * @return IOException occurred
     */
    public String ioErrorMessage() {
        return "IOException occurred!";
    }

    /**
     * Error prompt for not filling out both text inputs
     * @return Please fill out both the name and description inputs.
     */
    public String newItemMenuInputErrorPrompt() {
        return "Please fill out both the name and description inputs.";
    }
}
