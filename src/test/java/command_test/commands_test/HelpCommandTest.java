package command_test.commands_test;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.HelpCommand;
import org.junit.jupiter.api.DisplayName;

import static gnorizon.SpringTestReportsBot.command.CommandName.HELP;
import static gnorizon.SpringTestReportsBot.command.commands.HelpCommand.HELP_TEXT;

@DisplayName("Unit-level testing for HelpCommand")
public class HelpCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return HELP_TEXT;
    }

    @Override
    Command getCommand() {
        return new HelpCommand(sendBotMessageService);
    }
}
