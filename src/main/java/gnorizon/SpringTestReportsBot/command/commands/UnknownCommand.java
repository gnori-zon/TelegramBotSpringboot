package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotDocumentService;
import org.telegram.telegrambots.meta.api.objects.Update;
/**
 * Unknown command for bot  {@link Command}.
 */
public class UnknownCommand implements Command {
    public static final String TEXT_TO_SEND_UC = "Извините, я такой команды не знаю!";
    public static final String IMAGE_PATH_ERROR = "src/main/resources/static/ErrorBot.jpg";

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
                    TEXT_TO_SEND_UC, IMAGE_PATH_ERROR);
        }
    }
}
