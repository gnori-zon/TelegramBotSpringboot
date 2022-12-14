package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;
/**
 * Add me for group (work with DB) {@link Command}.
 */
public class AddMeForGroupCommand implements Command {
    public static final String NAME_MISSING = "Укажите название группы";
    private final SendBotMessageService sendBotMessageService;
    private final ModifyDataBaseService modifyDataBaseService;

    public AddMeForGroupCommand(SendBotMessageService sendBotMessageService,
                                ModifyDataBaseService modifyDataBaseService) {
        this.sendBotMessageService = sendBotMessageService;
        this.modifyDataBaseService = modifyDataBaseService;
    }
    @Override
    public void execute(Update update) {
        var chatId = update.getMessage().getChatId();
        if (update.getMessage().getText().contains("/addme ")) {
            String response = modifyDataBaseService.addUserInGroup(update.getMessage(), chatId);
            sendBotMessageService.sendMessages(chatId, response);
        } else {
            sendBotMessageService.sendMessages(chatId, NAME_MISSING);
        }
    }
}
