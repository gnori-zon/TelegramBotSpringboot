package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemSpecifier;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.modifyRR.ModifyRepositoryReportsService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotDocumentService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

import static gnorizon.SpringTestReportsBot.TelegramBot.FINISH_TYPE;
import static gnorizon.SpringTestReportsBot.TelegramBot.INTER_TYPE;
/**
 * Write data in report {@link Command}.
 */
@Slf4j
public class WriteInReportCommand implements Command {
    private final ItemSpecifier itemSpecifier;
    private final SendBotMessageService sendBotMessageService;
    private final SendBotDocumentService sendBotDocumentService;
    private final ClientAttributeModifyService clientAttributeModifyService;
    private final ModifyRepositoryReportsService modifyRepositoryReportsService;


    public WriteInReportCommand(ItemSpecifier itemSpecifier,
                                SendBotMessageService sendBotMessageService,
                                SendBotDocumentService sendBotDocumentService,
                                ClientAttributeModifyService clientAttributeModifyService,
                                ModifyRepositoryReportsService modifyRepositoryReportsService) {
        this.itemSpecifier = itemSpecifier;
        this.sendBotMessageService = sendBotMessageService;
        this.sendBotDocumentService = sendBotDocumentService;
        this.clientAttributeModifyService = clientAttributeModifyService;
        this.modifyRepositoryReportsService = modifyRepositoryReportsService;

    }

    @Override
    public void execute(Update update) {
        long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();
        int nameRep = clientAttributeModifyService.getNameRep();
        Report report = null;
        if (nameRep == 1) {
            report = modifyRepositoryReportsService.getReport(chatId + "_" + INTER_TYPE);
        } else if (nameRep == 2) {
            report = modifyRepositoryReportsService.getReport(chatId + "_" + FINISH_TYPE);
        }

        if (report != null) {
            log.info("writeInReport to user " + chatId);
            String num = "1234567890";
            //проверка на присутсвие номера шага
            if (!num.contains(String.valueOf(message.charAt(0)))) {
                validation(message, chatId, "Кажется вы забыли указать номер шага");
            }
            // проверка на отсутсвие двухзначного числа
            // try необходимо при неполном заполнении переменной
            if (!num.contains(String.valueOf(message.charAt(1)))) {
                String resp = itemSpecifier.checkingAndWrite(message, report);
                String[] respArr;
                // сообщение из 2 частей
                if (resp.contains("XXX")) {
                    respArr = resp.split("XXX");
                    sendBotMessageService.sendMessages(chatId, respArr[0]);
                    sendBotMessageService.sendMessages(chatId, respArr[1]);
                    //последнее сообщение
                } else if (resp.contains("XSX")) {
                    respArr = resp.split("XSX");
                    sendBotDocumentService.sendPhoto(chatId, "", respArr[0]);
                    sendBotMessageService.sendMessagesAndButton(chatId, respArr[1]);

                } else if (!resp.contains("oups!")) {
                    sendBotMessageService.sendMessages(chatId, resp);
                }
            } else {
                sendBotDocumentService.sendPhoto(chatId, "Извините, такого пункта в отчете нет!",
                        "src/main/resources/static/ErrorBot.jpg");
            }
        }
    }
    private void validation(String message, long chatId, String text) {
        if (!message.equals("Help") && !message.equals("Send report") && !message.equals("New report") &&
                !message.equals("/help") && !message.equals("/send") && !message.equals("/newreport") &&
                !message.equals("/start") && !message.equals("/addme")&& !message.equals("/delme")
                && !message.equals("/newgroup") && !message.equals("/delgroup")&& !message.equals("/reqrep")) {
            if(message.charAt(0)=='/'){
                sendBotMessageService.sendMessages(chatId, "Извините, я такой команды не знаю!");
            }else {
                sendBotMessageService.sendMessages(chatId, text);
            }
        }
    }
}
