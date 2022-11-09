package gnorizon.SpringTestReportsBot.command.commands;

import com.vdurmont.emoji.EmojiParser;
import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String name = update.getMessage().getChat().getFirstName();
        String answer = EmojiParser.parseToUnicode("Привет "+ name + ", давай создадим отчет!"+":bar_chart:");
        long chatId = update.getMessage().getChatId();

        sendBotMessageService.sendMessagesAndButton(chatId, answer);
    }
}
