package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;
/**
 * Delete me for group {@link Command}.
 */
public class DeleteMeForGroupCommand implements Command {
    private final String NAME_MISSING = "Укажите название группы";
    private final SendBotMessageService sendBotMessageService;
    private final ModifyDataBaseService modifyDataBaseService;

    public DeleteMeForGroupCommand(SendBotMessageService sendBotMessageService,ModifyDataBaseService modifyDataBaseService) {
        this.sendBotMessageService = sendBotMessageService;
        this.modifyDataBaseService = modifyDataBaseService;
    }
    @Override
    public void execute(Update update) {
        var chatId = update.getMessage().getChatId();
        if (update.getMessage().getText().contains("/delme ")) {
            String response = modifyDataBaseService.deleteFromGroup(update.getMessage(), chatId);
            sendBotMessageService.sendMessages(chatId, response);
        }else{
            sendBotMessageService.sendMessages(chatId, NAME_MISSING);
        }
    }
}
