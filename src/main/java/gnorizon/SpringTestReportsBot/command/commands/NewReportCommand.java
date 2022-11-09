package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
/**
 * New report {@link Command}.
 */
@Slf4j
public class NewReportCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final ClientAttributeModifyService clientAttributeModifyService;

    private final String INTERMEDIATE = "INT_REPORT_BUTTON";
    private final String FINAL = "FIN_REPORT_BUTTON";

    public NewReportCommand(SendBotMessageService sendBotMessageService,
                            ClientAttributeModifyService clientAttributeModifyService) {
        this.sendBotMessageService = sendBotMessageService;
        this.clientAttributeModifyService = clientAttributeModifyService;
    }

    @Override
    public void execute(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Какова глубина временной выборки?");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        var buttonIntermediate = new InlineKeyboardButton();
        buttonIntermediate.setText("Промежуточный");
        buttonIntermediate.setCallbackData(INTERMEDIATE);
        var buttonFinal = new InlineKeyboardButton();
        buttonFinal.setText("Финальный");
        buttonFinal.setCallbackData(FINAL);
        rowInline.add(buttonIntermediate);
        rowInline.add(buttonFinal);

        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);


        sendBotMessageService.executeMessage(message);

        log.info("new Report to user " + chatId);
        clientAttributeModifyService.setNameRep(0);
    }
}
