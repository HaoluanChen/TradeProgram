package frontend.messageReplyGUI.listeners;

public interface MessageResponse {
    /**
     * Method to get all the possible actions an user can do to a message
     * @return list of all possible actions in string
     */
    String[] getActions();

    /**
     * Method that takes in an actions, if it's from the list of possible actions, the method will do the action
     * @param action the action passed in
     */
    void doAction(String action);
}
