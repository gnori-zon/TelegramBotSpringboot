package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.modifyRR.ModifyRepositoryReportsService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotDocumentService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import gnorizon.SpringTestReportsBot.service.fileManipulation.ReportFileManipulationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;
/**
 * Send me report {@link Command}.
 */
@Slf4j
public class SendReportCommand implements Command {
    @Autowired
    private final ReportFileManipulationService fileManipulation;
    private final SendBotMessageService sendBotMessageService;
    private final SendBotDocumentService sendBotDocumentService;
    private final ModifyRepositoryReportsService modifyRepositoryReportsService;
    private final ClientAttributeModifyService clientAttributeModifyService;
    private final String FINISH_TYPE = "Finish";
    private final String INTER_TYPE = "Intermediate";

    public SendReportCommand(ReportFileManipulationService fileManipulation,
                             SendBotMessageService sendBotMessageService,
                             SendBotDocumentService sendBotDocumentService,
                             ClientAttributeModifyService clientAttributeModifyService,
                             ModifyRepositoryReportsService modifyRepositoryReportsService) {
        this.fileManipulation = fileManipulation;
        this.sendBotMessageService = sendBotMessageService;
        this.sendBotDocumentService = sendBotDocumentService;
        this.clientAttributeModifyService = clientAttributeModifyService;
        this.modifyRepositoryReportsService = modifyRepositoryReportsService;
    }

    @Override
    public void execute(Update update) {
        long chatId = update.getMessage().getChatId();
        Report report = null;
        int nameRep = clientAttributeModifyService.getNameRep();
        if(nameRep!=0) {
            sendBotMessageService.sendMessages(chatId, "Пару секунд, заполняю...");
            if (nameRep == 2) {
                report = modifyRepositoryReportsService.getReport(chatId + "_" + FINISH_TYPE);
            } else if (nameRep == 1) {
                report = modifyRepositoryReportsService.getReport(chatId + "_" + INTER_TYPE);
            }
            fileManipulation.create(report);
            fileManipulation.write(report);
            sendBotDocumentService.sendReport(chatId, report.getName());
            fileManipulation.delete(report);
            modifyRepositoryReportsService.removeReport(report.getName());
            log.info("Created,filled,send and deleted report for user: " + chatId);
            clientAttributeModifyService.setNameRep(0);
        } else {
            sendBotMessageService.sendMessages(chatId, "Отчет не заполнен");
        }
    }
}
