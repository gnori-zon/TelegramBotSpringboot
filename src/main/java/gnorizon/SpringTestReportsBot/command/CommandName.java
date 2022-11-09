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
    NO("nocommand");
    private final String commandName;

    CommandName(String commandName){
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
