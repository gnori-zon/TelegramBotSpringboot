package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotDocumentService;
import org.telegram.telegrambots.meta.api.objects.Update;
/**
 * Unknown command for bot  {@link Command}.
 */
public class UnknownCommand implements Command {
    private final SendBotDocumentService sendBotDocumentService;
    private final ClientAttributeModifyService clientAttributeModifyService;

    public UnknownCommand(SendBotDocumentService sendBotDocumentService,
                          ClientAttributeModifyService clientAttributeModifyService) {
        this.sendBotDocumentService = sendBotDocumentService;
        this.clientAttributeModifyService = clientAttributeModifyService;
    }

    @Override
    public void execute(Update update) {
        if (clientAttributeModifyService.getNameRep() == 0) {
            long chatId = update.getMessage().getChatId();
            sendBotDocumentService.sendPhoto(chatId,
                    "Извините, я такой команды не знаю!", "src/main/resources/static/ErrorBot.jpg");
        }
    }
}
