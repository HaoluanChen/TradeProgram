package frontend.mainMenuGUI.presenters;

public class MainMenuPresenter {

    /**
     *Tells the user that they are exiting the program
     */
    public void printExit(){
        System.out.println("Exiting you from the program");
    }
    /**
     * Tells user that their stored data files were corrupted and that they were cleared.
     * @return String telling them that their information was corrupted and their data was cleared.
     */
    public String corruptedData() {
        return "Because your was corrupted or manually removed, all data has been reset.";
    }
    /**
     * Text for "User Log In" if they press that Main Menu option to put on button.
     * @return String telling user "User Log In"
     */
    public String userLoginOption(){
        return "User Log In";
    }
    /**
     * Text for "User Sign Up" if they press that Main Menu option to put on button.
     * @return String telling user "User Log In"
     */
    public String userSignUpOption(){
        return "User Sign Up";
    }
    /**
     * Text for "Admin Log In" if they press that Main Menu option to put on button.
     * @return String telling user "Admin Log In"
     */
    public String adminLoginOption(){
        return "Admin Login";
    }
    /**
     * Text for "Demo Log In" if they press that Main Menu option to put on button.
     * @return String telling user "Program Demo"
     */
    public String demoLoginOption(){
        return "Program Demo";
    }
    /**
     * Text for "Exit" if they press that Main Menu option to put on button.
     * @return String telling user "Exit"
     */
    public String exitOption(){
        return "Exit";
    }

    /**
     * Tells user there was an issue in saving to their data files.
     * @return String telling user "An error has occurred with saving."
     */
    public String savingError(){
        return "An error has occurred with saving.";
    }
    /**
     * Tells user that they failed to log in
     * @return String telling user "Failed to log in."
     */
    public String failedLogin(){
        return "Failed to log in.";
    }
    /**
     * Text for a Log In button
     * @return String "log in."
     */
    public String login(){
        return "Log In";
    }
    /**
     * Text for a button returnToMainMenu button
     * @return String "Return to Main Menu".
     */
    public String returnToMainMenu(){
        return "Return to Main Menu";
    }
    /**
     * Text for a button with text "Sign Up"
     * @return String "Sign Up".
     */
    public String signUp(){
        return "Sign Up";
    }
    /**
     * Empty String if resetting a button's or label's text
     * @return empty string ""
     */
    public String stringReset(){
        return "";
    }
    /**
     * returns text for a label if the user's username was either taken or invalid.
     * @return String informing user their username was taken or invalid.
     */
    public String takenOrInvalidUsername(){
        return "Your username is taken or invalid, try again.";
    }
    /**
     * returns text for a label or button if the user successfully created an a account.
     * @return String with text that their account was successfully created.
     */
    public String successfulAccountCreation(){
        return "New account successfully created";
    }
    /**
     * returns text for a label or button if the user enters incorrect login information.
     * @return String with text that their login information was incorrect and to try logging in again.
     */
    public String wrongLogin(){
        return "Wrong login, try again.";
    }

    /**
     * returns String for a window Label with specific program name text
     * @return String with text containing program name
     */
    public String programName(){
        return "Trade System 2.0";
    }






}
