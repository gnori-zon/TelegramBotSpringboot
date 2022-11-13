package gnorizon.SpringTestReportsBot.command.commands.callback;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

public class DeleteMeGroupSelectCommand implements Command {
    private final String forCallback= "DelMe-";
    private final SendBotMessageService sendBotMessageService;
    private final ModifyDataBaseService modifyDataBaseService;

    public DeleteMeGroupSelectCommand(SendBotMessageService sendBotMessageService, ModifyDataBaseService modifyDataBaseService) {
        this.sendBotMessageService = sendBotMessageService;
        this.modifyDataBaseService = modifyDataBaseService;
    }

    @Override
    public void execute(Update update) {
        String nameGroup = update.getCallbackQuery().getData().substring(forCallback.length());
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        modifyDataBaseService.deleteFromGroup(nameGroup, chatId);

        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText("Вы покинули группу: "+nameGroup);
        message.setMessageId((int) messageId);
        sendBotMessageService.executeEditMessage(message);
    }
}
