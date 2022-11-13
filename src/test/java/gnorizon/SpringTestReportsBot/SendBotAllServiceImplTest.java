package gnorizon.SpringTestReportsBot;

import gnorizon.SpringTestReportsBot.service.sendBot.SendBotAllServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SendBotAllServiceImplTest {
    private static long chatId = 123;
    private static String textToSend = "Test text";
    private static TelegramBot bot = mock(TelegramBot.class);
    private static SendBotAllServiceImpl SBAService = new SendBotAllServiceImpl(bot);
    private static SendMessage message = new SendMessage();
    private static ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    private static List<KeyboardRow> keyboardRows = new ArrayList<>();
    private static KeyboardRow row = new KeyboardRow();
    private static EditMessageText editMessage = new EditMessageText();
        static {
            message.setText(textToSend);
            message.setChatId(String.valueOf(chatId));

            row.add("Send report");
            keyboardRows.add(row);
            row = new KeyboardRow();
            row.add("New report");
            row.add("Help");
            keyboardRows.add(row);
            keyboardMarkup.setKeyboard(keyboardRows);
            keyboardMarkup.setResizeKeyboard(true);
            keyboardMarkup.setOneTimeKeyboard(true);

        }

    @Test
    @SneakyThrows
    public void checkSendMessage() {
        message.setParseMode("Markdown");

        SBAService.sendMessages(chatId,textToSend);
        verify(bot).execute(message);
    }

    @Test
    @SneakyThrows
    public void checkSendMessageAndButton(){
        message.setReplyMarkup(keyboardMarkup);

        SBAService.sendMessagesAndButton(chatId,textToSend);
        verify(bot).execute(message);
    }

    @Test
    @SneakyThrows
    public void checkExecuteMessage(){
        SBAService.executeMessage(message);
        verify(bot).execute(message);
    }
    @Test
    @SneakyThrows
    public void checkExecuteEditMessage(){
        SBAService.executeEditMessage(editMessage);
        verify(bot).execute(editMessage);
    }


}



