package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static gnorizon.SpringTestReportsBot.command.commands.AddMeForGroupCommand.NAME_MISSING;

/**
 * Create new group (work with DB) {@link Command}.
 */
public class NewGroupCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final ModifyDataBaseService modifyDataBaseService;

    public NewGroupCommand(SendBotMessageService sendBotMessageService, ModifyDataBaseService modifyDataBaseService) {
        this.sendBotMessageService = sendBotMessageService;
        this.modifyDataBaseService = modifyDataBaseService;
    }

    @Override
    public void execute(Update update) {
        var chatId = update.getMessage().getChatId();
        if (update.getMessage().getText().contains("/newgroup ")) {
            String response = modifyDataBaseService.createGroup(update.getMessage(), chatId);
            sendBotMessageService.sendMessages(chatId, response);
        }else{
            sendBotMessageService.sendMessages(chatId, NAME_MISSING);
        }

    }
}
