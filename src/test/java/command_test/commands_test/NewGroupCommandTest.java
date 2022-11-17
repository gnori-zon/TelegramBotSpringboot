package command_test.commands_test;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.NewGroupCommand;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import static gnorizon.SpringTestReportsBot.command.commands.AddMeForGroupCommand.NAME_MISSING;
import static org.mockito.Mockito.when;
@DisplayName("Unit-level testing for NewGroupCommand")
public class NewGroupCommandTest {
    private SendBotMessageService sendBotMessageService;
    private ModifyDataBaseService modifyDataBaseService;
    private Command command;
    private Long chatId = 1234546l;

    @BeforeEach
    public void init(){
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        modifyDataBaseService = Mockito.mock(ModifyDataBaseServiceImpl.class);
        command = new NewGroupCommand(sendBotMessageService,modifyDataBaseService);

    }

    @Test
    public void shouldProperlyText(){
        //given
        String textForCommand = "/newgroup asd";
        Update update = AbstractCommandTest.prepareUpdate(chatId,textForCommand);
        String resp = "Success";
        when(modifyDataBaseService.createGroup(update.getMessage(), chatId)).thenReturn(resp);

        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).sendMessages(chatId, resp);
    }

    @Test
    public void shouldProperlyTextCaseEmptyName(){
        //given
        String textForCommand = "/newgroup";
        Update update = AbstractCommandTest.prepareUpdate(chatId,textForCommand);

        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).sendMessages(chatId, NAME_MISSING);
    }
}
