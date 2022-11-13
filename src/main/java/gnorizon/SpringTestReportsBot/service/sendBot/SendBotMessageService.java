package gnorizon.SpringTestReportsBot.service.sendBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
/**
 * Service for send/edit message to client
 */
public interface SendBotMessageService  {
    void sendMessages(long chatId, String textToSend);
    void sendMessagesAndButton(long chatId, String textToSend);
    void executeMessage(SendMessage message);
    void executeEditMessage(EditMessageText message);
}