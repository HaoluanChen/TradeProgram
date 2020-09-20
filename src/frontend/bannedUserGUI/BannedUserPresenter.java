package frontend.bannedUserGUI;

class BannedUserPresenter {

    /**
     * Unban prompt
     * @return Would you like an unban?
     */
    String unbanPrompt() {
        return "You are banned. Would you like to request an unban?";
    }

    /**
     * Confirm button
     * @return send unban request
     */
    String unbanConfirm() {
        return "Plead for mercy.";
    }

    /**
     * Cancel button
     * @return cancel
     */
    String unbanCancel() {
        return "Cancel.";
    }

    /**
     * Unban request sent
     * @return unban request sent
     */
    String unbanRequestSent() { return "Unban request sent."; }
}
