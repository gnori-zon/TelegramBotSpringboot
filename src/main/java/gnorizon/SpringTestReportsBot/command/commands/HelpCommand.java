package gnorizon.SpringTestReportsBot.command.commands;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;
/**
 * Help {@link Command}.
 */
public class HelpCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    private final String HELP_TEXT = "Этот бот формирует тест отчет из введенных вами данных и отправляет его вам в формате электронной табоицы Excel (.xlsx)\n\n" +
            "Вы можете использовать команды из главного меню в левом нижнем углу или ввести эту команду\n\n" +
            " *-* используйте /start для запуска бота\n" +
            " *-* используйте /newreprot для создания нового отчета\n" +
            " *-* используйте /send для получения отчета\n" +
            " *-* используйте /help для получения информации о использовании бота\n" +
            " *-* используйте /addme и *название группы* для добавления себя в группу\n" +
            " *-* используйте /newgroup и *название группы* для создания группы\n" +
            " *-* используйте /delme чтобы выйти из группы\n" +
            " *-* используйте /delgroup для удаления всех участников из группы и, затем, удаления группы\n" +
            " *-* используйте /reqrep для отправки ее участникам сообщения \"Подготовьте отчет!\"\n" +
            " *-* используйте /mygroups для получения списка групп\n" +

            "\nВ процессе заполнения отчета вы можете вернуться к любому шагу и изменить сообщение\n"
            + "Удачи!";

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        long chatId = update.getMessage().getChatId();
        sendBotMessageService.sendMessages(chatId,HELP_TEXT);
    }
}
