package gnorizon.SpringTestReportsBot.command.commands.callback;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.modifyRR.ModifyRepositoryReportsService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import static gnorizon.SpringTestReportsBot.TelegramBot.FINISH_TYPE;
import static gnorizon.SpringTestReportsBot.TelegramBot.INTER_TYPE;
import static gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsName.ITEM_1;
/**
 * Add report to repository (work with DB) {@link Command}.
 */
@Slf4j
public class AddReportToRepositoryCommand implements Command {
    public static final String INTERMEDIATE = "INT_REPORT_BUTTON";
    public static final String FINAL = "FIN_REPORT_BUTTON";
    private final ModifyRepositoryReportsService modifyRepositoryReportsService;
    private final SendBotMessageService sendBotMessageService;
    private final ClientAttributeModifyService clientAttributeModifyService;

    public AddReportToRepositoryCommand(ModifyRepositoryReportsService modifyRepositoryReportsService,
                                        SendBotMessageService sendBotMessageService,
                                        ClientAttributeModifyService clientAttributeModifyService) {
        this.modifyRepositoryReportsService = modifyRepositoryReportsService;
        this.sendBotMessageService = sendBotMessageService;
        this.clientAttributeModifyService = clientAttributeModifyService;
    }

    @Override
    public void execute(Update update) {
        String callBackData = update.getCallbackQuery().getData();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        switch (callBackData) {
            case (INTERMEDIATE):
                modifyRepositoryReportsService.addReport(new Report(chatId + "_" + INTER_TYPE));
                executeEditMessageText(1, "Вы, выбрали промежуточный Отчет о тестировании", chatId, messageId);
                break;
            case (FINAL):
                modifyRepositoryReportsService.addReport(new Report(chatId + "_" + FINISH_TYPE));
                executeEditMessageText(2, "Вы, выбрали финальный Отчет о тестировании", chatId, messageId);
                break;

        }
    }
    private void executeEditMessageText(int nameRep1, String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        clientAttributeModifyService.setNameRep(nameRep1);
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);
        sendBotMessageService.sendMessages(chatId, ITEM_1.textForStep);

        sendBotMessageService.executeEditMessage(message);

    }
}
