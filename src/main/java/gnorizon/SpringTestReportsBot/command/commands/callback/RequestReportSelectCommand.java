package gnorizon.SpringTestReportsBot.command.commands.callback;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class RequestReportSelectCommand implements Command {
    private final String forCallback= "ReqRep-";
    private final SendBotMessageService sendBotMessageService;
    private final ModifyDataBaseService modifyDataBaseService;

    public RequestReportSelectCommand(SendBotMessageService sendBotMessageService, ModifyDataBaseService modifyDataBaseService) {
        this.sendBotMessageService = sendBotMessageService;
        this.modifyDataBaseService = modifyDataBaseService;
    }

    @Override
    public void execute(Update update) {
        String nameGroup = update.getCallbackQuery().getData().substring(forCallback.length());
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        Map<Long, String> dataAboutUsers = modifyDataBaseService.requestReports(nameGroup,chatId);
        for (Map.Entry<Long, String> user : dataAboutUsers.entrySet()) {
            if(user.getKey()!=chatId) {
                sendBotMessageService.sendMessages(user.getKey(), user.getValue());
            }
        }

        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText("Вы запросили отчеты : "+nameGroup);
        message.setMessageId((int) messageId);
        sendBotMessageService.executeEditMessage(message);
    }
}
