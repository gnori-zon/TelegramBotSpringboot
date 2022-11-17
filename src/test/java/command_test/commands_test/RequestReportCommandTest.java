package command_test.commands_test;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.RequestReportCommand;
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

@DisplayName("Unit-level testing for RequestReportCommand")
public class RequestReportCommandTest {
    private  SendBotMessageService sendBotMessageService;
    private  ModifyDataBaseService modifyDataBaseService;
    private Command command;
    private Long chatId = 1234567l;
    private String forCallback= "ReqRep-";


    @BeforeEach
    public void init(){
        sendBotMessageService = Mockito.mock(SendBotAllServiceImpl.class);
        modifyDataBaseService = Mockito.mock(ModifyDataBaseServiceImpl.class);

        command = new RequestReportCommand(sendBotMessageService,modifyDataBaseService);
    }

    @Test
    public void shouldProperlyButtons(){
        //given
        String textForCommand = "/reqrep";
        Update update = AbstractCommandTest.prepareUpdate(chatId,textForCommand);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите группу для отправки запроса");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        String name = "group2";
        var button1 = new InlineKeyboardButton();
        button1.setText(name);
        button1.setCallbackData(forCallback + name);
        rowInline.add(button1);
        rowsInline.add(rowInline);

        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        var button = new InlineKeyboardButton();
        button.setText("Oтмена");
        button.setCallbackData("Cancel-");
        rowInline2.add(button);
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
    public void shouldProperlyTextForHaventGroup(){
        //given
        String textForCommand = "/reqrep";
        Update update = AbstractCommandTest.prepareUpdate(chatId,textForCommand);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("У вас нет групп, которые вы создали");

        HashMap<String, String> hashMap = new HashMap<>(Map.of("group1", "Участник", "group2", "Участник"));

        Mockito.when(modifyDataBaseService.getAllGroup(chatId)).thenReturn(hashMap);
        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).executeMessage(message);
    }
    @Test
    public void shouldProperlyTextForEmptyGroup(){
        //given
        String textForCommand = "/reqrep";
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
