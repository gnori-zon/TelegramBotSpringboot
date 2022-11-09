package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;
/**
 * Delete group (work with DB) {@link Command}.
 */
public class DeleteGroupCommand implements Command {
    private final String NAME_MISSING = "Укажите название группы";
    private final SendBotMessageService sendBotMessageService;
    private final ModifyDataBaseService modifyDataBaseService;
    public DeleteGroupCommand(SendBotMessageService sendBotMessageService,ModifyDataBaseService modifyDataBaseService) {
        this.sendBotMessageService = sendBotMessageService;
        this.modifyDataBaseService = modifyDataBaseService;
    }

    @Override
    public void execute(Update update) {
        var chatId = update.getMessage().getChatId();
        if (update.getMessage().getText().contains("/delgroup ")) {
            String response = modifyDataBaseService.dropGroup(update.getMessage(), chatId);
            sendBotMessageService.sendMessages(chatId, response);
        } else {
            sendBotMessageService.sendMessages(chatId, NAME_MISSING);
        }
    }
}
