package command_test.commands_test;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.NotCommand;
import org.junit.jupiter.api.DisplayName;

import static gnorizon.SpringTestReportsBot.command.CommandName.NO;
import static gnorizon.SpringTestReportsBot.command.commands.NotCommand.TEXT_TO_SEND_NC;

@DisplayName("Unit-level testing for NotCommand")
public class NotCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return NO.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return TEXT_TO_SEND_NC;
    }

    @Override
    Command getCommand() {
        return new NotCommand(sendBotMessageService);
    }
}
