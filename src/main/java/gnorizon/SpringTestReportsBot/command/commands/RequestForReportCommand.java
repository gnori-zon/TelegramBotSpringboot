package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
/**
 * Send all group users request for report  (work with DB) {@link Command}.
 */
public class RequestForReportCommand implements Command {
    private final String NAME_MISSING = "Укажите название группы";

    private final SendBotMessageService sendBotMessageService;
    private final ModifyDataBaseService modifyDataBaseService;

    public RequestForReportCommand(SendBotMessageService sendBotMessageService,ModifyDataBaseService modifyDataBaseService) {
        this.sendBotMessageService = sendBotMessageService;
        this.modifyDataBaseService = modifyDataBaseService;
    }
    @Override
    public void execute(Update update) {
        if (update.getMessage().getText().contains("/reqrep ")) {
            Map<Long, String> dataAboutUsers = modifyDataBaseService.requestReports(update.getMessage());
            for (Map.Entry<Long, String> user : dataAboutUsers.entrySet()) {
                sendBotMessageService.sendMessages(user.getKey(), user.getValue());
            }
        }else{
            sendBotMessageService.sendMessages(update.getMessage().getChatId(), NAME_MISSING);
        }
    }
}
