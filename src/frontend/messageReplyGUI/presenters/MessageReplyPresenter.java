package frontend.messageReplyGUI.presenters;

import entities.*;

public class MessageReplyPresenter {

    /**
     * Return a string that tells the user that they have no messages
     * @return a string that tells the user that they have no messages
     */
    public String printNoMessages(){
        return "You have no messages to look through";
    }

    /**
     * Return the message in string
     * @param m the message to be put in string
     * @return the message in string
     */
    public String messageString(Message m){
        return m.toString();
    }

    /**
     * Return a shorten, indexed string for the message
     * @param m the message to be put in string
     * @param index the index of the message in the list of messages
     * @return the message in a shortened, indexed string
     */
    public String messageStringSideBar(Message m, int index){
        return index+" From "+m.getSender();
    }


    /**
     * Return the unqiue options they have to a system message
     * @return the unique options in string
     */
    public String[] systemMessageAction(){
        String[] s = new String[1];
        s[0] = "Delete";
        return s;
    }
    /**
     * Return the unqiue options they have to a private message
     * @return the unqiue options in string
     */
    public String[] privateMessageAction(){
        String[] s = new String[2];
        s[0] = "Delete";
        s[1] = "Report";
        return s;
    }

    /**
     * Return a string warning the user when they reach the max number of edits
     * @return the string warning the user when they reach the max number of edits
     */
    public String tradeRequestWarning(){
        return "Warning, you and the other trader has reached the max number of edits,\nselecting edit means " +
                "you will cancel this trade request";
    }

    /**
     * Return a string warning the user when they reach the max number of edits
     * @return the string warning the user when they reach the max number of edits
     */
    public String tradeRequestCancel(){
        return "Trade request cancelled due to too much edits";
    }

    /**
     * Return the unqiue options they have to a request
     * @param m the message
     * @return the unqiue options in string
     */
    public String[] requestAction(Request m){
        return m.getOptions();
    }

    /**
     * Return a string for exit
     * @return the string for exit
     */
    public String exit(){
        return "Exit";
    }


    /**
     * Return a string for successful action
     * @return the string for successful action
     */
    public String success(){
        return "Success";
    }

    /**
     * Return a string for an error
     * @return the string for an error
     */
    public String error(){
        return "Error";
    }

    /**
     * Return a string for delete
     * @return the string for delete
     */
    public String delete(){
        return "Delete";
    }

    /**
     * Return a string for confirm
     * @return the string for confirm
     */
    public String confirm(){
        return "Confirm";
    }

    /**
     * Return a empty string
     * @return a empty string
     */
    public String emptyString(){
        return "";
    }

    /**
     * Return a string with the instructions to use the message reply gui
     * @return the string with the instructions
     */
    public String instructions(){
        return "Select a Message you want to view on the side list. Then click confirm to view.";
    }



    //-------------------Report System-----------------------//
    /**
     * Return a string that prompts the user to write a report
     * @return the string that prompts the user to write a report
     */
    public String reportPrompt(){
        return "Write your reason for reporting:";
    }

    /**
     * Return the content of a message in string
     * @param m the message
     * @return the content of the message in string
     */
    public String messageContent(Message m){
        return m.getContent();
    }

    /**
     * Return a string for report
     * @return the string for report
     */
    public String report(){
        return "Report";
    }

    /**
     * Return a string for the title of the report system
     * @return the string for the title of the report system
     */
    public String reportTitle(){
        return "Report System";
    }

    /**
     * Return a string that tells the user their reason for reporting is empty
     * @return the string that tells the user their reason for reporting is empty
     */
    public String reportReasonEmpty(){
        return "The reason for the report must not be empty";
    }

    /**
     * Return a string that tells the user that the report is made
     * @return the string that tells the user the report is made
     */
    public String reportSuccess(){
        return "Your report has been sent to the Admin.";
    }

    //--------------------TradeRequestCannotConfirmGUI--------------------//
    /**
     * Return a string for the title of the trade request cannot confirm window
     * @return the string for the title of the trade request cannot confirm window
     */
    public String titleTradeRequestCannotConfirm(){
        return "You are unable to confirm this trade right now";
    }

    /**
     * Return a string that prompts the user to make a decision to a trade request that could not be
     * confirmed at this time
     * @param m the trade request
     * @return the string that prompts the user to make a decision to a trade request that could not be
     *      * confirmed at this time
     */
    public String tradeRequestCannotConfirmPrompt(TradeRequest m){
        return "You or the other trader cannot create a new trade at this time or the items involved " +
                "are not for trade at this time, try again later.\nTrade Request: " +
                m.toString() +
                "\nYou can delete this trade request or skip it for now.";
    }

    /**
     * Return a string for the option to keep and skip the message
     * @return the string for the option to keep and skip the message
     */
    public String keep(){
        return "Keep";
    }

    //--------------------TradeRequestEdit-----------------------//
    /**
     * Return a string for the option to edit
     * @return the string for the option to edit
     */
    public String edit(){
        return "Edit";
    }

    /**
     * Return a string for the title of the trade request edit menu
     * @return the string for the title of the trade request edit menu
     */
    public String titleTradeRequestEdit(){
        return "Make changes to the old trade request:";
    }

    /**
     * Return a string that prompts the user to enter a new date
     * @param oldDate the old date
     * @return the string that prompts the user to enter a new date
     */
    public String datePrompt(String oldDate){
        return "Old date " + oldDate +"\nEnter the new date in the format yyyy-mm-dd hh:mm (or leave it empty):";
    }

    /**
     * Return a string that prompts the user to enter a new place
     * @param oldPlace the old place
     * @return the string that prompts the user to enter a new place
     */
    public String placePrompt(String oldPlace){
        return "Old place "+oldPlace+"\nEnter the new place (or leave it empty):";
    }

    /**
     * Return a string for telling the user they entered the wrong format
     * @return the string for telling the user they entered the wrong format
     */
    public String wrongFormat(){
        return "Wrong format";
    }

    /**
     * Return a string for telling the user they entered a date that pasted
     * @return the string for telling the user they entered a date that pasted
     */
    public String enterDateInPast(){
        return "Enter a time in the future";
    }

    /**
     * Return a string for telling the user they made no edits
     * @return the string for telling the user they made no edits
     */
    public String noEdit(){
        return "Make at least one Edit or Exit";
    }

}
