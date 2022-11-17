package command_test.commands_test;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.WriteInReportCommand;
import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemSpecifier;
import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsController;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.modifyRR.ModifyRepositoryReportsService;
import gnorizon.SpringTestReportsBot.service.modifyRR.ModifyRepositoryReportsServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotAllServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotDocumentService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import static gnorizon.SpringTestReportsBot.TelegramBot.INTER_TYPE;
import static gnorizon.SpringTestReportsBot.controller.itemSpecifier.items.Item10.*;

@DisplayName("Unit-level testing for WriteReportCommand")
public class WriteReportCommandTest {
    private  ItemSpecifier itemSpecifier;
    private  SendBotMessageService sendBotMessageService ;
    private  SendBotDocumentService sendBotDocumentService;
    private  ClientAttributeModifyService clientAttributeModifyService;
    private  ModifyRepositoryReportsService modifyRepositoryReportsService;
    private Command command;
    private Long chatId = 1234565534l;
    private Report report;

    @BeforeEach
    public void init(){
        itemSpecifier = Mockito.mock(ItemsController.class);
        sendBotMessageService = Mockito.mock(SendBotAllServiceImpl.class);
        sendBotDocumentService = Mockito.mock(SendBotAllServiceImpl.class);
        clientAttributeModifyService = Mockito.mock(ClientAttributeModifyService.class);
        modifyRepositoryReportsService = Mockito.mock(ModifyRepositoryReportsServiceImpl.class);
        report = Mockito.mock(Report.class);

        command = new WriteInReportCommand(itemSpecifier,sendBotMessageService,
                                           sendBotDocumentService,clientAttributeModifyService,
                                           modifyRepositoryReportsService);
    }


    @Test
    public void shouldProperlyTextForItem2(){
        //given
        String textForItem = "1 name-234-ready ";
        String returnText1 = "text2";
        String regex = "XXX";
        String returnText2 = "example input";

        Update update = AbstractCommandTest.prepareUpdate(chatId,textForItem);

        Mockito.when(clientAttributeModifyService.getNameRep()).thenReturn(1);
        Mockito.when(modifyRepositoryReportsService.getReport(chatId + "_" + INTER_TYPE)).thenReturn(report);
        Mockito.when(itemSpecifier.checkingAndWrite(textForItem, report)).thenReturn(returnText1+regex+returnText2);

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessages(chatId,returnText1);
        Mockito.verify(sendBotMessageService).sendMessages(chatId,returnText2);
    }

    @Test
    public void shouldProperlyTextForItem(){
        //given
        String textForItem = "1 name-234-ready ";

        Update update = AbstractCommandTest.prepareUpdate(chatId,textForItem);

        Mockito.when(clientAttributeModifyService.getNameRep()).thenReturn(1);
        Mockito.when(modifyRepositoryReportsService.getReport(chatId + "_" + INTER_TYPE)).thenReturn(report);
        Mockito.when(itemSpecifier.checkingAndWrite(textForItem, report)).thenReturn(imagePath+regex+returnText);

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotDocumentService).sendPhoto(chatId, "", imagePath);
        Mockito.verify(sendBotMessageService).sendMessagesAndButton(chatId, returnText);
    }
    @Test
    public void shouldProperlyTextNoNumberItem(){
        //given
        String textForItem = "123 name-234-ready ";

        Update update = AbstractCommandTest.prepareUpdate(chatId,textForItem);

        Mockito.when(clientAttributeModifyService.getNameRep()).thenReturn(1);
        Mockito.when(modifyRepositoryReportsService.getReport(chatId + "_" + INTER_TYPE)).thenReturn(report);

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotDocumentService).sendPhoto(chatId,"Извините, такого пункта в отчете нет!",
                "src/main/resources/static/ErrorBot.jpg");
    }
    @Test
    public void shouldProperlyTextUnknownCommand(){
        //given
        String textForItem = "/124das";

        Update update = AbstractCommandTest.prepareUpdate(chatId,textForItem);

        Mockito.when(clientAttributeModifyService.getNameRep()).thenReturn(1);
        Mockito.when(modifyRepositoryReportsService.getReport(chatId + "_" + INTER_TYPE)).thenReturn(report);

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessages(chatId, "Извините, я такой команды не знаю!");
    }

    @Test
    public void shouldProperlyTextForCaseEmptyNumber() {
        //given
        String textForItem = "dasdfsdfe33r";

        Update update = AbstractCommandTest.prepareUpdate(chatId,textForItem);

        Mockito.when(clientAttributeModifyService.getNameRep()).thenReturn(1);
        Mockito.when(modifyRepositoryReportsService.getReport(chatId + "_" + INTER_TYPE)).thenReturn(report);
        Mockito.when(itemSpecifier.checkingAndWrite(textForItem, report)).thenReturn("oups!");

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessages(chatId, "Кажется вы забыли указать номер шага");
    }
}
