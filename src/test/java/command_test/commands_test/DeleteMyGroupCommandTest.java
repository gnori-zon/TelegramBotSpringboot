package command_test.commands_test;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.DeleteMyGroupCommand;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotAllServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DisplayName("Unit-level testing for DeleteMyGroupCommand")
public class DeleteMyGroupCommandTest {
    private SendBotMessageService sendBotMessageService;
    private ModifyDataBaseService modifyDataBaseService;
    private Long chatId = 1124523l;
    private final String forCallback= "DelGroup-";
    private Command command;

    @BeforeEach
    public void init(){
        sendBotMessageService = Mockito.mock(SendBotAllServiceImpl.class);
        modifyDataBaseService = Mockito.mock(ModifyDataBaseServiceImpl.class);
        command = new DeleteMyGroupCommand(sendBotMessageService,modifyDataBaseService);
    }

    @Test
    public void shouldProperlyReturnListGroup(){
        //given
        String textForCommand = "/delgroup";
        Update update = AbstractCommandTest.prepareUpdate(chatId,textForCommand);
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите группу для удаления");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText("group2");
        button.setCallbackData(forCallback + "group2");
        rowInline.add(button);
        rowsInline.add(rowInline);

        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        var button2 = new InlineKeyboardButton();
        button2.setText("Oтмена");
        button2.setCallbackData("Cancel");
        rowInline2.add(button2);
        rowsInline.add(rowInline2);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        HashMap<String, String> hashMap = new HashMap<>(Map.of("group1", "Участник", "group2", "Владелец"));
        Mockito.when(modifyDataBaseService.getAllGroup(chatId)).thenReturn(hashMap);
        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).executeMessage(message);
    }
    @Test
    public void shouldProperlyTextForCaseEmptySelfGroup(){
        //given
        String textForCommand = "/delgroup";
        Update update = AbstractCommandTest.prepareUpdate(chatId,textForCommand);
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("У вас нет групп, которые вы создали");


        HashMap<String, String> hashMap = new HashMap<>(Map.of("group1", "Участник"));
        Mockito.when(modifyDataBaseService.getAllGroup(chatId)).thenReturn(hashMap);
        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).executeMessage(message);
    }
    @Test
    public void shouldProperlyTextForCaseEmptyListGroup(){
        //given
        String textForCommand = "/delgroup";
        Update update = AbstractCommandTest.prepareUpdate(chatId,textForCommand);
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Вы не состоите в группах");


        HashMap<String, String> hashMap = new HashMap<>();
        Mockito.when(modifyDataBaseService.getAllGroup(chatId)).thenReturn(hashMap);
        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).executeMessage(message);
    }

}
