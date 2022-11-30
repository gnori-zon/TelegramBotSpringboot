package command_test.commands_test.callback_test;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.callback.DeleteMeGroupSelectCommand;
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

@DisplayName("Unit-level testing for DeleteMeGroupSelectCommand")
public class DeleteMeGroupSelectCommandTest {
    private final String forCallback= "DelMe-";
    private SendBotMessageService sendBotMessageService;
    private ModifyDataBaseService modifyDataBaseService;
    private Long chatId = 124125l;
    private Command command;
    @BeforeEach
    public void init(){
        sendBotMessageService = Mockito.mock(SendBotAllServiceImpl.class);
        modifyDataBaseService = Mockito.mock(ModifyDataBaseServiceImpl.class);

        command = new DeleteMeGroupSelectCommand(sendBotMessageService,modifyDataBaseService);
    }

    @Test
    public void shouldProperlyReturnSuccessText(){
        //given
        int messageId = 124124;
        String nameGroup ="name";
        Update update = AddReportToRepositoryCommandTest.prepareUpdateCB(chatId,forCallback+nameGroup,messageId);
        EditMessageText message = new EditMessageText();

        message.setChatId(String.valueOf(chatId));
        message.setText("Вы покинули группу: "+nameGroup);
        message.setMessageId(messageId);
        Mockito.when(modifyDataBaseService.deleteFromGroup(nameGroup,chatId)).thenReturn("Вы покинули группу: ");
        //when
        command.execute(update);
        //then
        Mockito.verify(modifyDataBaseService).deleteFromGroup(nameGroup, chatId);
        Mockito.verify(sendBotMessageService).executeEditMessage(message);
    }
}
