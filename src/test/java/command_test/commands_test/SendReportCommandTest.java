package command_test.commands_test;


import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.SendReportCommand;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyServiceImpl;
import gnorizon.SpringTestReportsBot.service.fileManipulation.ReportExcelManipulationServiceImpl;
import gnorizon.SpringTestReportsBot.service.fileManipulation.ReportFileManipulationService;
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

@DisplayName("Unit-level testing for SendReportCommand")
public class SendReportCommandTest {
    private  ReportFileManipulationService fileManipulation;
    private  SendBotMessageService sendBotMessageService;
    private  SendBotDocumentService sendBotDocumentService;
    private  ClientAttributeModifyService clientAttributeModifyService;
    private  ModifyRepositoryReportsService modifyRepositoryReportsService;
    private Long chatId = 1234565534l;

    private Command command;
    private Report report;

    @BeforeEach
    public void init(){
        fileManipulation = Mockito.mock(ReportExcelManipulationServiceImpl.class);
        sendBotMessageService = Mockito.mock(SendBotAllServiceImpl.class);
        sendBotDocumentService = Mockito.mock(SendBotAllServiceImpl.class);
        clientAttributeModifyService = Mockito.mock(ClientAttributeModifyServiceImpl.class);
        modifyRepositoryReportsService = Mockito.mock(ModifyRepositoryReportsServiceImpl.class);
        report = Mockito.mock(Report.class);
        command = new SendReportCommand(fileManipulation,sendBotMessageService,sendBotDocumentService,clientAttributeModifyService,modifyRepositoryReportsService);
    }

    @Test
    public void shouldProperlyTextForEmptyFillntReport(){
        //given
        String textForUpdate = "/send";
        Update update = AbstractCommandTest.prepareUpdate(chatId,textForUpdate);
        Mockito.when(clientAttributeModifyService.getNameRep()).thenReturn(0);
        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).sendMessages(chatId, "Отчет не заполнен");

    }
    @Test
    public void shouldProperlyTextForEmptyFillnt(){
        //given
        String textForUpdate = "/send";

        Update update = AbstractCommandTest.prepareUpdate(chatId,textForUpdate);
        Mockito.when(clientAttributeModifyService.getNameRep()).thenReturn(1);
        Mockito.when(modifyRepositoryReportsService.getReport(chatId + "_" + INTER_TYPE)).thenReturn(report);

        //when
        command.execute(update);
        //then
        Mockito.verify(sendBotMessageService).sendMessages(chatId, "Пару секунд, заполняю...");
        Mockito.verify(fileManipulation).create(report);
        Mockito.verify(fileManipulation).write(report);
        Mockito.verify(sendBotDocumentService).sendReport(chatId, report.getName());
        Mockito.verify(fileManipulation).delete(report);
        Mockito.verify(modifyRepositoryReportsService).removeReport(report.getName());
        Mockito.verify(clientAttributeModifyService).setNameRep(0);
    }
}

