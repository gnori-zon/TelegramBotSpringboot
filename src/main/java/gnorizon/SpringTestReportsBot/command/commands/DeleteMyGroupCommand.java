package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DeleteMyGroupCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final ModifyDataBaseService modifyDataBaseService;

    private final String forCallback= "DelGroup-";

    public DeleteMyGroupCommand(SendBotMessageService sendBotMessageService,
                                ModifyDataBaseService modifyDataBaseService){
        this.sendBotMessageService = sendBotMessageService;
        this.modifyDataBaseService = modifyDataBaseService;
    }

    @Override
    public void execute(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите группу для удаления");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        Map<String,String> myGroups= modifyDataBaseService.getAllGroup(chatId);
        if (!myGroups.isEmpty()){
            int i =0;
            Set<String> namesGroup = myGroups.keySet();
            for(String name : namesGroup) {

                if (!"Участник".equals(myGroups.get(name))) {
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
                    var button = new InlineKeyboardButton();
                    button.setText(name);
                    button.setCallbackData(forCallback + name);
                    rowInline.add(button);
                    rowsInline.add(rowInline);
                    i++;
                }
            }
            if(i!=0){
                List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
                var button = new InlineKeyboardButton();
                button.setText("Oтмена");
                button.setCallbackData("Cancel");
                rowInline2.add(button);
                rowsInline.add(rowInline2);

                markupInline.setKeyboard(rowsInline);
                message.setReplyMarkup(markupInline);

                sendBotMessageService.executeMessage(message);
            }else{
                message.setText("У вас нет групп, которые вы создали");
                sendBotMessageService.executeMessage(message);
            }
        }else{
            message.setText("Вы не состоите в группах");
            sendBotMessageService.executeMessage(message);
        }
    }
}

