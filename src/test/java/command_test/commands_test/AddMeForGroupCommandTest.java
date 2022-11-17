package command_test.commands_test;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.AddMeForGroupCommand;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotAllServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import static gnorizon.SpringTestReportsBot.command.commands.AddMeForGroupCommand.NAME_MISSING;

@DisplayName("Unit-level testing for AddMeForGroupCommand")
public class AddMeForGroupCommandTest {
    private SendBotMessageService sendBotMessageService;
    private ModifyDataBaseService modifyDataBaseService;
    private Long chatId = 1241245l;
    private Command command;

    @BeforeEach
    public void init(){
        sendBotMessageService = Mockito.mock(SendBotAllServiceImpl.class);
        modifyDataBaseService = Mockito.mock(ModifyDataBaseServiceImpl.class);

        command = new AddMeForGroupCommand(sendBotMessageService,modifyDataBaseService);
    }

    @Test
    public void shouldProperlyReturnSuccessText(){
        //given
        String textForCommand = "/addme ";
        Update update = AbstractCommandTest.prepareUpdate(chatId,textForCommand);
        String response = "in sss";
        Mockito.when(modifyDataBaseService.addUserInGroup(update.getMessage(), chatId)).thenReturn(response);
        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).sendMessages(chatId, response);
    }
    @Test
    public void shouldProperlyReturnFailText(){
        //given
        String textForCommand = "/addme";
        Update update = AbstractCommandTest.prepareUpdate(chatId,textForCommand);

        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).sendMessages(chatId, NAME_MISSING);
    }

}
