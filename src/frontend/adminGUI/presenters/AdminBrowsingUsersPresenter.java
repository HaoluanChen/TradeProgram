package frontend.adminGUI.presenters;

public class AdminBrowsingUsersPresenter {
    /**
     * Returns a string that prompts the user to choose an option
     * @return the string
     */
    public String optionPrompt(){
        return "Choose your option below";
    }

    /**
     * Returns a string telling the user they entered and invalid username
     * @return the string
     */
    public String invalidName(){
        return "Invalid username, try again";
    }

    /**
     * Returns a string telling the user to enter the new limit somewhere
     * @return the string
     */
    public String enterValuePrompt(){
        return "Enter the new limit in bottom right";
    }

    /**
     * Returns a string telling the user they changed the user's banning state
     * @return the string
     */
    public String banStateChangeSuccess(){
        return "User banning state has been changed";
    }

    /**
     * Returns a string telling the user they changed the user's frozen state
     * @return the string
     */
    public String freezeStateChangeSuccess(){
        return "User freezing state has been changed";
    }

    /**
     * Returns a string telling the user they changed the user's weekly limit
     * @return the string
     */
    public String weeklyLimitChangeSuccess(){
        return "Weekly limit successfully changed";
    }

    /**
     * Returns a string telling the user they changed the user's incomplete trade limit
     * @return the string
     */
    public String incompleteLimitChangeSuccess(){
        return "Incomplete trades limit successfully changed";
    }

    /**
     * Returns a string telling the user they changed the user's threshold
     * @return the string
     */
    public String thresholdChangeSuccess(){
        return "Threshold successfully changed";
    }

    /**
     * Returns a string telling the user they entered the wrong format
     * @return the string
     */
    public String wrongFormat(){ return "Enter only numbers"; }

    /**
     * Returns a string saying deleted item has been restored
     * @return the string
     */
    public String deletedItemRestored(){ return "Last deleted item has been restored"; }

    /**
     * Returns a string prompting to enter username
     * @return the string
     */
    public String enterUsername(){ return "Enter your username:"; }

    /**
     * Returns a string saying Search
     * @return the string
     */
    public String searchText(){ return "Search"; }

    /**
     * Returns a string for ban button
     * @return the string
     */
    public String banUnbanText(){ return "Ban/unban user"; }

    /**
     * Returns a string for freeze button
     * @return the string
     */
    public String freezeText(){ return "Freeze/unfreeze user"; }

    /**
     * Returns a string for incomplete trades button
     * @return the string
     */
    public String incompleteText(){ return "Change limit of incomplete trades"; }

    /**
     * Returns a string for weekly trades button
     * @return the string
     */
    public String weeklyText(){ return "Change weekly trade limit"; }

    /**
     * Returns a string for lending button
     * @return the string
     */
    public String lendingText(){ return "Change lending threshold"; }

    /**
     * Returns a string for enter button
     * @return the string
     */
    public String enterText(){ return "Enter"; }

    /**
     * Returns a string for threshold label
     * @return the string
     */
    public String thresholdText(){ return "Change thresholds for all users"; }

    /**
     * Returns a string for exit button
     * @return the string
     */
    public String exitText(){ return "Exit"; }

    /**
     * Returns a string for undo delete label
     * @return the string
     */
    public String undoText(){ return "Undo user deleting items"; }

}
