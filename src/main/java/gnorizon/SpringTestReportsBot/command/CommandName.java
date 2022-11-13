package gnorizon.SpringTestReportsBot.command;
/**
 * Enumeration for {@link Command}'s.
 */
public enum CommandName {
    START("/start"),
    NEW_REPORT("/newreport"),
    HELP("/help"),
    SEND("/send"),
    ADD_ME("/addme"),
    NEW_GROUP("/newgroup"),
    REQ_REP("/reqrep"),
    DEL_ME("/delme"),
    DEL_GROUP("/delgroup"),
    MY_GROUPS("/mygroups"),
    NO("nocommand"),
    CANCEL_CB("Cancel-"),
    DEL_GROUP_CB("DelGroup-"),
    DEL_ME_CB("DelMe-"),
    ADD_REP_TO_REPO("INT_REPORT_BUTTON"+"FIN_REPORT_BUTTON"),
    REQ_REP_CB("ReqRep-");
    private final String commandName;

    CommandName(String commandName){
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
