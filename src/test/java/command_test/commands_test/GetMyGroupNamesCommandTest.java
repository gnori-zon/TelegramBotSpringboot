package command_test.commands_test;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.GetMyGroupNamesCommand;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotAllServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@DisplayName("Unit-level testing for GetMyGroupNamesCommand")
public class GetMyGroupNamesCommandTest {
    private  SendBotMessageService sendBotMessageService;
    private  ModifyDataBaseService modifyDataBaseService;
    private Long chatId = 1124523l;
    private Command command;

    @BeforeEach
    public void init(){
        sendBotMessageService = Mockito.mock(SendBotAllServiceImpl.class);
        modifyDataBaseService = Mockito.mock(ModifyDataBaseService.class);
        command = new GetMyGroupNamesCommand(sendBotMessageService,modifyDataBaseService);
    }

    @Test
    public void shouldProperlyReturnListGroup(){
        //given
        String textForCommand = "/mygroups";
        Update update = AbstractCommandTest.prepareUpdate(chatId,textForCommand);
        HashMap<String, String> hashMap = new HashMap<>(Map.of("group1", "Участник", "group2", "Владелец"));
        String response = hashMap.toString().replaceAll("\\{", "").
                replaceAll("\\}", "").
                replaceAll("=", " ").
                replaceAll(", ", "\n");
        response = "Группы: \n" + response;
        Mockito.when(modifyDataBaseService.getAllGroup(chatId)).thenReturn(hashMap);

        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).sendMessages(chatId, response);
    }
    @Test
    public void shouldProperlyReturnText(){
        //given
        String textForCommand = "/mygroups";
        Update update = AbstractCommandTest.prepareUpdate(chatId,textForCommand);
        HashMap<String, String> hashMap = new HashMap<>();

        Mockito.when(modifyDataBaseService.getAllGroup(chatId)).thenReturn(hashMap);

        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).sendMessages(chatId, "Вы не состоите в группах");
    }
}
