package gnorizon.SpringTestReportsBot.service;

import com.vdurmont.emoji.EmojiParser;
import gnorizon.SpringTestReportsBot.config.BotConfig;

import gnorizon.SpringTestReportsBot.model.DB.GroupRepository;
import gnorizon.SpringTestReportsBot.model.DB.UserRepository;

import gnorizon.SpringTestReportsBot.model.Reports.Report;
import gnorizon.SpringTestReportsBot.model.Reports.RepositoryReports;
import gnorizon.SpringTestReportsBot.service.fileManipulation.ReportFileManipulation;
import gnorizon.SpringTestReportsBot.service.itemSpecifier.ItemSpecifier;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.*;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    ReportFileManipulation fileManipulation;
    @Autowired
    RepositoryReports reports;
    @Autowired
    private ItemSpecifier itemSpecifier;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    final BotConfig config;
    static final String HELP_TEXT = "Этот бот формирует тест отчет из введенных вами данных и отправляет его вам в формате электронной табоицы Excel (.xlsx)\n\n" +
            "Вы можете использовать команды из главного меню в левом нижнем углу или ввести эту команду\n\n" +
            " *-* используйте /start для запуска бота\n" +
            " *-* используйте /newreprot для создания нового отчета\n" +
            " *-* используйте /send для получения отчета\n" +
            " *-* используйте /help для получения информации о использовании бота\n" +
            " *-* используйте /addme и *название группы* для добавления себя в группу\n" +
            " *-* используйте /newgroup и *название группы* для согдания группы\n" +
            " *-* используйте /delme и *название группы* чтобы выйти из группы\n" +
            " *-* используйте /delgroup и *название группы* для удаления всех участников из группы и, затем, удаления группы\n" +
            " *-* используйте /reqrep и *название группы* для отправки ее участникам сообщения \"Подготовьте отчет!\"\n" +
            " *-* используйте /mygroups для получения списка групп\n" +

            "\nВ процессе заполнения отчета вы можете вернуться к любому шагу и изменить сообщение\n"
            + "Удачи!";

    static final String INTERMEDIATE = "INT_REPORT_BUTTON";
    static final String FINAL = "FIN_REPORT_BUTTON";
    static final String ERROR_TEXT = "Error occurred: ";
    public static final String FINISH_TYPE = "Finish";
    static final String INTER_TYPE = "Intermediate";
    static final String NAME_MISSING = "Укажите название группы";
    private int nameRep = 0;
    //передаем конфиг в бот
    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start", "get a welcome message"));
        listOfCommands.add(new BotCommand("/newreport", "create a new test report"));
        listOfCommands.add(new BotCommand("/help", "get info how to use bot"));
        listOfCommands.add(new BotCommand("/send", "bot sends a report"));
        listOfCommands.add(new BotCommand("/addme", "add yourself to the group"));
        listOfCommands.add(new BotCommand("/newgroup", "create new group"));
        listOfCommands.add(new BotCommand("/reqrep", "request to prepare reports"));
        listOfCommands.add(new BotCommand("/delme", "remove yourself from the group"));
        listOfCommands.add(new BotCommand("/delgroup", "drop group"));
        listOfCommands.add(new BotCommand("/mygroups", "get group names"));
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error bot settings command list: " + e.getMessage());
        }
    }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
    @Override
    public String getBotToken() {
        return config.getToken();
    }
    //проверка на изменения
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();
            //проверка вида отчета и заполнение отчета в определенный ранее тип
            if(nameRep!=0) {
                Report report = null;
                if(nameRep==1) {
                    report = reports.getReport(chatId + "_" + INTER_TYPE);
                }else if (nameRep==2) {
                    report = reports.getReport(chatId + "_" + FINISH_TYPE);
                }
                if(report!=null) {
                    writeInReport(messageText, chatId, report);
                    log.info("writeInReport to user " + chatId);
                }
            }
            //проверка ввода на команду
            switch (messageText) {

                case ("/start"):
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    nameRep = 0;
                    break;
                case ("Help"):
                case ("/help"):
                    sendMessages(chatId, HELP_TEXT);
                    break;
                case ("New report"):
                case ("/newreport"):
                    newReport(chatId);
                    log.info("new Report to user " + chatId);
                    nameRep = 0;
                    break;
                case("/mygroups"):
                    getMyGroups(chatId);
                    break;
                case ("Send report"):
                case ("/send"):
                    Report report =null;
                    if(nameRep!=0) {
                        if (nameRep == 2) {
                            report = reports.getReport(chatId + "_" + FINISH_TYPE);
                        } else if (nameRep == 1) {
                            report = reports.getReport(chatId + "_" + INTER_TYPE);
                        }
                        if(report!=null) {
                            fileManipulation.create(report);
                            fileManipulation.write(report);
                            sendReport(chatId, report.getName());
                            reports.removeReport( report.getName());
                            fileManipulation.delete(report);
                            log.info("Created,filled,send and deleted report for user: " + chatId);
                            nameRep = 0;
                        }
                    } else {
                        sendMessages(chatId, "Отчет не заполнен");
                    }
                    break;
                default:
                    // так как после команды должен быть текст с названием группы, то проверки в default
                    //второй if проверяет есть ли пробел после команды
                    if(messageText.contains("/newgroup") || messageText.contains("/addme") ||
                         messageText.contains("/reqrep") || messageText.contains("/delme") ||
                         messageText.contains("/delgroup")) {

                        if(messageText.contains("/newgroup ")) {
                            createGroup(update.getMessage());
                        }
                        else if (messageText.contains("/delgroup ")) {
                            dropGroup(update.getMessage());
                        }
                        else if (messageText.contains("/addme ")) {
                            addUserInGroup(update.getMessage());
                        }
                        else if (messageText.contains("/reqrep ")) {
                            requestReports(update.getMessage());
                        }
                        else if (messageText.contains("/delme ")) {
                            deleteFromGroup(update.getMessage());
                        }else{
                            sendMessages(chatId,NAME_MISSING);
                        }
                    }else if (nameRep == 0){

                                if (messageText.charAt(0) == '/') {
                                    sendPhoto(chatId, "Извините, я такой команды не знаю!", "ErrorBot.jpg");
                                } else {
                                    sendMessages(chatId, "Вы забыли про '/' ");
                                }
                            }
                    break;
            }

        } else if (update.hasCallbackQuery()) {
            String callBackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            switch (callBackData) {
                case (INTERMEDIATE):
                    reports.addReport(new Report(chatId+"_"+INTER_TYPE));
                    executeEditMessageText(1, "Вы, выбрали промежуточный Отчет о тестировании", chatId, messageId);
                    break;
                case (FINAL):
                    reports.addReport(new Report(chatId+"_"+FINISH_TYPE));
                    executeEditMessageText(2, "Вы, выбрали финальный Отчет о тестировании", chatId, messageId);
                    break;

            }
        }

    }

    /**
     * work with DB
     */
    private void getMyGroups(Long chatId){
        HashMap<String,String> listGroups = new ModifyDB(groupRepository,userRepository).getAllGroup(chatId);
        if(!listGroups.isEmpty()) {
            String response = listGroups.toString().replaceAll("\\{", "").
                    replaceAll("\\}", "").
                    replaceAll("=", " ").
                    replaceAll(", ", "\n");
            response = "Группы: \n" + response;
            sendMessages(chatId, response);
        }else {
            sendMessages(chatId,"Вы не состоите в группах");
        }
    }
    private void dropGroup(Message message){
        var chatId = message.getChatId();
        String response = new ModifyDB(groupRepository,userRepository).dropGroup(message,chatId);
        sendMessages(chatId,response);
    }
    private void deleteFromGroup(Message message){
        var chatId = message.getChatId();
        String response = new ModifyDB(groupRepository,userRepository).deleteFromGroup(message,chatId);
        sendMessages(chatId,response);
    }
    private void requestReports(Message message){
        Map<Long,String> dataAboutUsers = new ModifyDB(groupRepository,userRepository).requestReports(message);
        for (Map.Entry<Long, String> user : dataAboutUsers.entrySet()) {
            sendMessages(user.getKey(),user.getValue());
        }
    }
    private void createGroup(Message message) {
        var chatId = message.getChatId();
        String response = new ModifyDB(groupRepository,userRepository).createGroup(message,chatId);
        sendMessages(chatId,response);
    }
    private void addUserInGroup(Message message) {
        var chatId = message.getChatId();
        String response= new ModifyDB(groupRepository,userRepository).addUserInGroup(message,chatId);
        sendMessages(chatId,response);
    }
    /**
     * work with REPORT
     */
    //выбор вида отчета
    private void newReport(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Какова глубина временной выборки?");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        var buttonIntermediate = new InlineKeyboardButton();
        buttonIntermediate.setText("Промежуточный");
        buttonIntermediate.setCallbackData(INTERMEDIATE);
        var buttonFinal = new InlineKeyboardButton();
        buttonFinal.setText("Финальный");
        buttonFinal.setCallbackData(FINAL);
        rowInline.add(buttonIntermediate);
        rowInline.add(buttonFinal);

        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        executeMessage(message);
    }
    // ввод информации в отчет
    public void writeInReport(String message, long chatId, Report report) {
        String num = "1234567890";
        //проверка на присутсвие номера шага
        if (!num.contains(String.valueOf(message.charAt(0)))) {
            validation(message, chatId, "Кажется вы забыли указать номер шага");
        }
        // проверка на отсутсвие двухзначного числа
        // try необходимо при неполном заполнении переменной
        if (!num.contains(String.valueOf(message.charAt(1)))) {
            String resp = itemSpecifier.checkingAndWrite(message,report);
            String[] respArr;
            // сообщение из 2 частей
            if(resp.contains("XXX")){
                respArr = resp.split("XXX");
                sendMessages(chatId,respArr[0]);
                sendMessages(chatId,respArr[1]);
                //последнее сообщение
            }else if (resp.contains("XSX")) {
                respArr = resp.split("XSX");
                sendPhoto(chatId, "", respArr[0]);
                sendMessagesAndButton(chatId, respArr[1]);

            }else if(!resp.contains("oups!")){
                sendMessages(chatId,resp);
            }
        } else {
            sendPhoto(chatId, "Извините, такого пункта в отчете нет!", "ErrorBot.jpg");
        }
    }

    /**
     * work BOT'S LOGIC
     */
    //создает и отправляет сообщение к /start
    private void startCommandReceived(long chatId, String firstName) {
        String answer = EmojiParser.parseToUnicode("Привет " + firstName + ", давай создадим отчет!" + ":bar_chart:");
        log.info("Replied to user " + firstName);
        sendMessagesAndButton(chatId, answer);
    }
    // отправка сообщения
    private void sendMessages(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        sendMessage.setParseMode("Markdown");

        executeMessage(sendMessage);
    }
    //отправка сообщения с кнопками-клавиатурой
    private void sendMessagesAndButton(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Send report");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("New report");
        row.add("Help");
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        sendMessage.setReplyMarkup(keyboardMarkup);

        executeMessage(sendMessage);
    }

    @SneakyThrows
    public void sendReport(long chatId, String filePath) {
        filePath = filePath +".xlsx";
        File sourceFile = new File(filePath);
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(String.valueOf(chatId));
        sendDocument.setDocument(new InputFile(sourceFile));

        try {
            execute(sendDocument);
            log.info("sendDocument to user " + chatId);
        }catch(TelegramApiException e){
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

    @SneakyThrows
    public void sendPhoto(long chatId, String imageCaption, String imagePath) {
        File image = ResourceUtils.getFile(imagePath);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(image));
        sendPhoto.setChatId(String.valueOf(chatId));
        sendPhoto.setCaption(imageCaption);
        sendPhoto.setProtectContent(true);

        try {
            execute(sendPhoto);
            log.info("sendPhoto to user " + chatId);
        }catch(TelegramApiException e){
            log.error(ERROR_TEXT + e.getMessage());
        }
    }
    // изменение сообщения при выборе типа отчета
    private void executeEditMessageText(int nameRep1, String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        nameRep = nameRep1;
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);
        sendMessages(chatId, "Введите *название отчета,релиз и готовность* через запятую \n\nначиная с 1");
        sendMessages(chatId, "\nн: *1 Имя-3-готов*");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }
    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }
    private void validation(String message, long chatId, String text) {
        if (!message.equals("Help") && !message.equals("Send report") && !message.equals("New report") &&
                !message.equals("/help") && !message.equals("/send") && !message.equals("/newreport") &&
                !message.equals("/start") && !message.equals("/addme")&& !message.equals("/delme")
                && !message.equals("/newgroup") && !message.equals("/delgroup")&& !message.equals("/reqrep")) {
            if(message.charAt(0)=='/'){
                sendMessages(chatId, "Извините, я такой команды не знаю!");
            }else {
                sendMessages(chatId, text);
            }
        }
    }

}
