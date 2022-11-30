package command_test.commands_test.callback_test;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.callback.DeleteMyGroupSelectCommand;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotAllServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@DisplayName("Unit-level testing for DeleteMyGroupSelectCommand")
public class DeleteMyGroupSelectCommandTest {
    private final String forCallback= "DelGroup-";
    private SendBotMessageService sendBotMessageService;
    private ModifyDataBaseService modifyDataBaseService;
    private Long chatId = 124125l;
    private Command command;
    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotAllServiceImpl.class);
        modifyDataBaseService = Mockito.mock(ModifyDataBaseServiceImpl.class);

        command = new DeleteMyGroupSelectCommand(sendBotMessageService,modifyDataBaseService);
    }

    @Test
    public void shouldProperlyReturnSuccessText(){
        //given
        int messageId = 123432;
        String nameGroup = "nameG";
        Update update = AddReportToRepositoryCommandTest.prepareUpdateCB(chatId,forCallback+nameGroup,messageId);

        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText("Вы распустили группу: "+nameGroup);
        message.setMessageId(messageId);
        Mockito.when(modifyDataBaseService.dropGroup(nameGroup,chatId)).thenReturn("Вы распустили группу: ");
        //when
        command.execute(update);
        //then
        Mockito.verify(modifyDataBaseService).dropGroup(nameGroup,chatId);
        Mockito.verify(sendBotMessageService).executeEditMessage(message);
    }

}
