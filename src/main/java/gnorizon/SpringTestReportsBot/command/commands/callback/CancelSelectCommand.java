package gnorizon.SpringTestReportsBot.command.commands.callback;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import static gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsName.ITEM_1;

public class CancelSelectCommand implements Command {
    private final String forCallback = "Cancel-";
    private final SendBotMessageService sendBotMessageService;

    public CancelSelectCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText("Отменено.");
        message.setMessageId((int) messageId);
        sendBotMessageService.executeEditMessage(message);
    }

}
