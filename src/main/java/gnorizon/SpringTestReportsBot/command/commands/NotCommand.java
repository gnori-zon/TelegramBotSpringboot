package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;
/**
 * Not command  {@link Command}.
 */
public class NotCommand implements Command {
    public static String TEXT_TO_SEND_NC = "Вы забыли про '/' ";
    public final SendBotMessageService sendBotMessageService;


    public NotCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
            long chatId = update.getMessage().getChatId();
            sendBotMessageService.sendMessages(chatId,TEXT_TO_SEND_NC);
    }
}
