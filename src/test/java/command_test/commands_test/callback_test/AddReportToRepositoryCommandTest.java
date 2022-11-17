package command_test.commands_test.callback_test;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.callback.AddReportToRepositoryCommand;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyServiceImpl;
import gnorizon.SpringTestReportsBot.service.modifyRR.ModifyRepositoryReportsService;
import gnorizon.SpringTestReportsBot.service.modifyRR.ModifyRepositoryReportsServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotAllServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static gnorizon.SpringTestReportsBot.TelegramBot.INTER_TYPE;
import static gnorizon.SpringTestReportsBot.command.commands.callback.AddReportToRepositoryCommand.INTERMEDIATE;
import static gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsName.ITEM_1;

@DisplayName("Unit-level testing for AddReportToRepositoryCommand")
public class AddReportToRepositoryCommandTest {

    private  ModifyRepositoryReportsService modifyRepositoryReportsService;
    private  SendBotMessageService sendBotMessageService;
    private  ClientAttributeModifyService clientAttributeModifyService;
    private Long chatId = 124125l;
    private Command command;

    @BeforeEach
    public void init(){
        modifyRepositoryReportsService = Mockito.mock(ModifyRepositoryReportsServiceImpl.class);
        sendBotMessageService = Mockito.mock(SendBotAllServiceImpl.class);
        clientAttributeModifyService = Mockito.mock(ClientAttributeModifyServiceImpl.class);

        command = new AddReportToRepositoryCommand(modifyRepositoryReportsService,
                                                   sendBotMessageService,
                                                   clientAttributeModifyService);
    }

    @Test
    public void shouldProperlyReturnSuccessText(){
        //given
        int messageId = 21441;
        Update update = prepareUpdateCB(chatId,INTERMEDIATE,messageId);
        EditMessageText message = new EditMessageText();

        message.setChatId(String.valueOf(chatId));
        message.setText("Вы, выбрали промежуточный Отчет о тестировании");
        message.setMessageId(messageId);

        sendBotMessageService.executeEditMessage(message);
        //when
        command.execute(update);
        //then
        Mockito.verify(modifyRepositoryReportsService).addReport(new Report(chatId + "_" + INTER_TYPE));
        Mockito.verify(clientAttributeModifyService).setNameRep(1);
        Mockito.verify(sendBotMessageService).sendMessages(chatId, ITEM_1.textForStep);
    }


    public static Update prepareUpdateCB(Long chatId,String callBackData,int messageId){
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getMessageId()).thenReturn(messageId);
        CallbackQuery cbq = Mockito.mock(CallbackQuery.class);
        Mockito.when(cbq.getData()).thenReturn(callBackData);
        Mockito.when(cbq.getMessage()).thenReturn(message);
        cbq.setMessage(message);
        update.setCallbackQuery(cbq);
        return update;
    }
}
