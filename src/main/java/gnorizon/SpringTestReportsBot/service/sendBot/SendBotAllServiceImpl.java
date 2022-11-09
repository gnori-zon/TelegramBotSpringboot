package gnorizon.SpringTestReportsBot.service.sendBot;

import gnorizon.SpringTestReportsBot.TelegramBot;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class SendBotAllServiceImpl implements SendBotMessageService,SendBotDocumentService{
    static final String ERROR_TEXT = "Error occurred: ";
    private final TelegramBot bot;

    @Autowired
    public SendBotAllServiceImpl(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessages(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        sendMessage.setParseMode("Markdown");

        executeMessage(sendMessage);
    }

    @Override
    public void sendMessagesAndButton(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

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

        executeMessage(sendMessage);
    }
    @Override
    public void executeMessage(SendMessage message) {
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }
    @Override
    public void executeEditMessage(EditMessageText message) {
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

    @Override
    public void sendReport(long chatId, String filePath) {
        filePath = filePath +".xlsx";
        File sourceFile = new File(filePath);
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(String.valueOf(chatId));
        sendDocument.setDocument(new InputFile(sourceFile));

        try {
            bot.execute(sendDocument);
            log.info("sendDocument to user " + chatId);
        }catch(TelegramApiException e){
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public void sendPhoto(long chatId, String imageCaption, String imagePath) {
        File image = ResourceUtils.getFile(imagePath);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(image));
        sendPhoto.setChatId(String.valueOf(chatId));
        sendPhoto.setCaption(imageCaption);
        sendPhoto.setProtectContent(true);

        try {
            bot.execute(sendPhoto);
            log.info("sendPhoto to user " + chatId);
        }catch(TelegramApiException e){
            log.error(ERROR_TEXT + e.getMessage());
        }
    }
}
