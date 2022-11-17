package service_test;

import gnorizon.SpringTestReportsBot.TelegramBot;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotAllServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Unit-level testing for SendBotMessageService")
public class SendBotMessageServiceTest {
    private SendBotMessageService sendBotMessageService;
    private  TelegramBot bot;
    private Long chatId = 124124l;
    private String textToSend = "blablabla";

    @BeforeEach
    public void init(){
        bot = Mockito.mock(TelegramBot.class);
        sendBotMessageService = new SendBotAllServiceImpl(bot);
    }

    @Test
    public void sendMessagesSuccess() throws TelegramApiException {
        //given
        SendMessage sendMessage = prepareSendMessage(chatId,textToSend);
        //when
        sendBotMessageService.sendMessages(chatId,textToSend);
        //then
        Mockito.verify(bot).execute(sendMessage);

    }

    @Test
    public void sendMessagesAndButtonSuccess() throws TelegramApiException {
        //given
        SendMessage sendMessage = prepareSendMessage(chatId,textToSend);
        sendMessage.setParseMode(null);
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Send report");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("New report");
        row.add("Help");
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        sendMessage.setReplyMarkup(keyboardMarkup);
        //when
        sendBotMessageService.sendMessagesAndButton(chatId,textToSend);
        //then
        Mockito.verify(bot).execute(sendMessage);

    }
    private SendMessage prepareSendMessage(Long chatId,String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        sendMessage.setParseMode("Markdown");
        return sendMessage;
    }
}
