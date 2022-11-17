package command_test.commands_test;

import gnorizon.SpringTestReportsBot.TelegramBot;
import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.NewReportCommand;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotAllServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static gnorizon.SpringTestReportsBot.command.CommandName.NEW_REPORT;
import static gnorizon.SpringTestReportsBot.command.commands.NewReportCommand.FINAL;
import static gnorizon.SpringTestReportsBot.command.commands.NewReportCommand.INTERMEDIATE;

@DisplayName("Unit-level testing for NewReportCommand")
public class NewReportCommandTest {
    private TelegramBot telegramBot = Mockito.mock(TelegramBot.class);
    private SendBotMessageService sendBotMessageService = new SendBotAllServiceImpl(telegramBot);
    private ClientAttributeModifyService clientAttributeModifyService = new ClientAttributeModifyServiceImpl(telegramBot);
    private String commandMessage = "Какова глубина временной выборки?";
    String getCommandName(){
        return NEW_REPORT.getCommandName();
    }
    String getCommandMessage(){
        return commandMessage;
    }
    Command getCommand(){
        return new NewReportCommand(sendBotMessageService,clientAttributeModifyService);
    }

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        //given
        Long chatId = 1234565534l;

        Update update = AbstractCommandTest.prepareUpdate(chatId, getCommandName());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(getCommandMessage());

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

        sendMessage.setReplyMarkup(markupInline);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(telegramBot).execute(sendMessage);
        Assertions.assertEquals(0,telegramBot.getNameRep());
    }
}
