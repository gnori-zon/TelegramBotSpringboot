package command_test.commands_test.callback_test;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.callback.CancelSelectCommand;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotAllServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import static command_test.commands_test.callback_test.AddReportToRepositoryCommandTest.prepareUpdateCB;

@DisplayName("Unit-level testing for CancelSelectCommand")
public class CancelSelectCommandTest {
    private final String forCallback = "Cancel-";
    private SendBotMessageService sendBotMessageService;
    private Command command;
    private Long chatId = 12414l;

    @BeforeEach
    public void init(){
        sendBotMessageService = Mockito.mock(SendBotAllServiceImpl.class);

        command = new CancelSelectCommand(sendBotMessageService);
    }

    @Test
    public void shouldProperlyReturnSuccessText(){
        //given
        int messageId = 124;
        Update update = prepareUpdateCB(chatId,forCallback,messageId);
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText("Отменено.");
        message.setMessageId((int) messageId);
        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).executeEditMessage(message);
    }
}
