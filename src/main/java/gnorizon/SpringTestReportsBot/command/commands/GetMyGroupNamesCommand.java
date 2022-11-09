package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
/**
 * Get my group name (work with DB) {@link Command}.
 */
public class GetMyGroupNamesCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final ModifyDataBaseService modifyDataBaseService;

    public GetMyGroupNamesCommand(SendBotMessageService sendBotMessageService, ModifyDataBaseService modifyDataBaseService) {
        this.sendBotMessageService = sendBotMessageService;
        this.modifyDataBaseService = modifyDataBaseService;
    }

    @Override
    public void execute(Update update) {
        long chatId = update.getMessage().getChatId();
        HashMap<String,String> listGroups = modifyDataBaseService.getAllGroup(chatId);
        if(!listGroups.isEmpty()) {
            String response = listGroups.toString().replaceAll("\\{", "").
                    replaceAll("\\}", "").
                    replaceAll("=", " ").
                    replaceAll(", ", "\n");
            response = "Группы: \n" + response;
            sendBotMessageService.sendMessages(chatId, response);
        }else {
            sendBotMessageService.sendMessages(chatId,"Вы не состоите в группах");
        }
    }
}
