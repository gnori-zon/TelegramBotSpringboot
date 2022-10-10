package gnorizon.SpringTestReportsBot.service;

import com.vdurmont.emoji.EmojiParser;
import gnorizon.SpringTestReportsBot.config.BotConfig;
import gnorizon.SpringTestReportsBot.model.*;
import gnorizon.SpringTestReportsBot.service.IOmethods.IOEngine;

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

            "\nВ процессе заполнения отчета вы можете вернуться к любому шагу и изменить сообщение\n"
            + "Удачи!";

    static final String INTERMEDIATE = "INT_REPORT_BUTTON";
    static final String FINAL = "FIN_REPORT_BUTTON";
    static final String ERROR_TEXT = "Error occurred: ";
    static final String FINISH_TYPE = "Finish";
    static final String INTER_TYPE = "Intermediate";
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


            //проверка вида отчета и заполнение отчета в выбранные ранее тип
            switch (nameRep) {
                case 1:
                    writeInReport(messageText, chatId, INTER_TYPE);
                    log.info("writeInReport to user " + chatId);
                    break;
                case 2:
                    writeInReport(messageText, chatId, FINISH_TYPE);
                    log.info("writeInReport to user " + chatId);
                    break;
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
                case ("Send report"):
                case ("/send"):
                    if (nameRep == 2) {
                        sendReport(chatId, "PatternFinalReport.xlsx");
                        log.info("sendReport to user " + chatId);
                        nameRep = 0;
                    } else if (nameRep == 1) {
                        sendReport(chatId, "PatternInterReport.xlsx");
                        log.info("sendReport to user " + chatId);
                        nameRep = 0;
                    } else {
                        sendMessages(chatId, "Отчет не заполнен");
                    }
                    break;
                default:
                    if(messageText.contains("/newgroup")) {
                        if(messageText.contains("/newgroup ")) {
                            createGroup(update.getMessage());
                        }else{
                            sendMessages(chatId,"Укажите название группы");
                        }
                    } else if (messageText.contains("/addme")) {
                        if (messageText.contains("/addme ")) {
                            addUserInGroup(update.getMessage());
                        }else{
                            sendMessages(chatId,"Укажите название группы");
                        }
                        }else if (messageText.contains("/reqrep")) {
                            if (messageText.contains("/reqrep ")) {
                                requestReports(update.getMessage());
                            }else{
                                sendMessages(chatId,"Укажите название группы");
                            }
                    }else if (messageText.contains("/delme")) {
                        if (messageText.contains("/delme ")) {
                            deleteFromGroup(update.getMessage());
                        }else{
                            sendMessages(chatId,"Укажите название группы");
                        }
                    }else if (messageText.contains("/delgroup")) {
                        if (messageText.contains("/delgroup ")) {
                            dropGroup(update.getMessage());
                        }else{
                            sendMessages(chatId,"Укажите название группы");
                        }
                    }
                    else if (nameRep == 0){
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
                    IOEngine.createReport(chatId, INTER_TYPE);
                    executeEditMessageText(1, "Вы, выбрали промежуточный Отчет о тестировании", chatId, messageId);
                    break;
                case (FINAL):
                    IOEngine.createReport(chatId, FINISH_TYPE);
                    executeEditMessageText(2, "Вы, выбрали финальный Отчет о тестировании", chatId, messageId);
                    break;

            }
        }

    }
    private void dropGroup(Message message){
        var chatId = message.getChatId();
        String nameGroup = getGroupName(message.getText(),9);
        if(groupRepository.findById(nameGroup).isPresent()) {
            Long owner = groupRepository.findById(nameGroup).get().getOwner();
            if (owner.equals(chatId)) {
                var users =userRepository.findAll();
                for(User user : users){
                    if(user.getNameGroup().equals(nameGroup)){
                            userRepository.deleteById(user.getId());
                    }
                }
                groupRepository.deleteById(nameGroup);
                sendMessages(chatId,"Группа распущена и удалена!");
                log.info("user: " + owner+" drop self group: " + nameGroup);
            } else {
                sendMessages(chatId, "Вы не владелец");
            }
        } else {
            sendMessages(chatId, "Такой группы нет");
        }
    }
    private void deleteFromGroup(Message message){
        int success = 0;
        var chatId = message.getChatId();
        String nameGroup = getGroupName(message.getText(),6);
        if(groupRepository.findById(nameGroup).isPresent()){
            var users =userRepository.findAll();
            for(User user : users){
                if(user.getNameGroup().equals(nameGroup)){
                    if(user.getChatId().equals(chatId)){
                        userRepository.deleteById(user.getId());
                        sendMessages(chatId,"Вы вышли из группы!");
                        success+=1;
                        log.info("user: " + user+" remove from group: " + nameGroup);
                    }
                }
            }
            // удаление не произошло
            if (success == 0){
                sendMessages(chatId, "Вы не состоите в этой группе");
            }

        } else {
            sendMessages(chatId, "Такой группы нет");
        }

    }
    private void requestReports(Message message){
        var chatId = message.getChatId();
        String nameGroup = getGroupName(message.getText(),7);
        if(groupRepository.findById(nameGroup).isPresent()) {
            Long owner = groupRepository.findById(nameGroup).get().getOwner();
            if (owner.equals(chatId)) {
                var users =userRepository.findAll();
                for(User user : users){
                    if(user.getNameGroup().equals(nameGroup)){
                        sendMessages(user.getChatId(),"Группа: "+nameGroup+"-Подготовьте отчет!");
                        sendMessages(chatId,"Сообщения отправлено!");
                    }
                }
            } else {
                sendMessages(chatId, "Вы не владелец");
            }
        } else {
            sendMessages(chatId, "Такой группы нет");
        }
    }
    private void createGroup(Message message) {
        var owner = message.getChatId();
        String nameGroup = getGroupName(message.getText(),9);
        if(groupRepository.findById(nameGroup).isEmpty()){
            Group group = new Group();

            group.setNameGroup(nameGroup);
            group.setOwner(owner);
            groupRepository.save(group);
            sendMessages(owner, "Готово! \n Имя группы: "+ group.getNameGroup());
            log.info("group saved: " + group);
        } else {
            sendMessages(owner, "Такая группа уже есть");
        }
    }
    private void addUserInGroup(Message message) {
        var chatId = message.getChatId();
        String nameGroup = getGroupName(message.getText(),6);
        if(userRepository.findById(chatId+nameGroup).isEmpty()){
            if(groupRepository.findById(nameGroup).isPresent()) {
                User user = new User();
                user.setId(chatId + nameGroup);
                user.setNameGroup(nameGroup);
                user.setChatId(chatId);
                userRepository.save(user);
                sendMessages(chatId,"Вы вошли в группу");
                log.info("user saved: " + user);
            }else{
                sendMessages(chatId, "Такой группы нет");
            }
        }else{
            sendMessages(chatId, "Вы уже есть в этой группе");
        }
    }
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
    // ввод информации в отчет
    public void writeInReport(String message, long chatId, String typeReport) {
        String num = "1234567890";
        //проверка на присутсвие номера шага
        if (!num.contains(String.valueOf(message.charAt(0)))) {
            validation(message, chatId, "Кажется вы забыли указать номер шага");
        }
        // проверка на отсутсвие двухзначного числа
        // try необходимо при неполном заполнении переменной
        if (!num.contains(String.valueOf(message.charAt(1)))) {
            switch (message.charAt(0)) {
                case ('1'):
                    try {
                        String[] data = message.substring(1).split("-");
                        IOEngine.setCell1(typeReport,data,chatId);
                        if (typeReport.equals(FINISH_TYPE)) {
                            sendMessages(chatId, "2.Введите *дату начала и окончания тестирования* \n\nначиная с 2 ");
                            sendMessages(chatId, "н: *2 01.01.2001/02.02.2002*");
                        } else {
                            sendMessages(chatId, "2.Введите *дату начала/окончания/количество оставшихся дней и стенд*  \n\nначиная с 2 ");
                            sendMessages(chatId, "н: *2 01.01.2001/02.02.2002/32-имя стенда*");
                        }
                }catch (Exception e){
                    sendMessages(chatId, "Кажется вы что-то забыли ввести, попробуйте снова");
                }
                    break;
                case ('2'):
                    try {
                        String[] date = message.substring(1).split("/");
                        IOEngine.setCell2(typeReport,date,chatId);
                        if (typeReport.equals(FINISH_TYPE)) {
                            sendMessages(chatId, "3.Введите *имя стенд*  \n\nначиная с 3");
                        } else {
                            sendMessages(chatId, "3.Введите *браузеры-всего тест-кейсов/пройденных тест-кейсов - всего багов/закрых багов* через запятую \n\nначиная с 3");
                            sendMessages(chatId, " н: *3 Chrome-12/6-13/2, Safari-15/2-14/2*");
                            }
                    }catch (Exception e){
                        sendMessages(chatId, "Кажется вы что-то забыли ввести или дата некорректна, попробуйте снова");
                    }
                    break;
                case ('3'):
                    try {
                        IOEngine.setCell3(typeReport, message, chatId);

                        if (typeReport.equals(FINISH_TYPE)) {
                            sendMessages(chatId, "4.Введите *операционные системы* через запятую \n\nначиная с 4");
                        } else {
                            sendMessages(chatId, "4.Введите *операционные системы/всего тест-кейсов/пройденных тест-кейсов/всего багов/закрых багов* через запятую \n\nначиная с 4");
                            sendMessages(chatId, "н: *4 Windows-12/6-13/2,MacOS-15/2-14/2*");
                        }
                    }catch (Exception e){
                        sendMessages(chatId, "Кажется вы что-то забыли ввести, попробуйте снова");
                    }
                    break;
                case ('4'):
                    try {
                        String[] arrayOS = message.substring(1).split(",");
                        IOEngine.setCell4(typeReport,arrayOS,chatId);

                        sendMessages(chatId, "5.Введите *функции и количество багов* в них через запятую  \n\nначиная с 5 ");
                        sendMessages(chatId, "н: *5 функция-1,функция-2*");
                    }catch (Exception e){
                        sendMessages(chatId, "Кажется вы что-то забыли ввести, попробуйте снова");
                    }
                    break;
                case ('5'):
                    try {
                        String[] arrayFuncs = message.substring(1).split(",");
                        IOEngine.setCell5(typeReport, arrayFuncs, chatId);

                        sendMessages(chatId, "6.Введите *количество всего багов/закрыто багов и всего улучшений/улучшено через* -  \n\nначиная с 6");
                        sendMessages(chatId, "н: *6 15/12-16/13*");
                    }catch (Exception e){
                        sendMessages(chatId, "Кажется вы что-то забыли ввести, попробуйте снова");
                    }
                    break;
                case ('6'):
                    try {
                        String[] contBugAndImprove = message.substring(1).split("-");
                        IOEngine.setCell6(typeReport,contBugAndImprove,chatId);

                        sendMessages(chatId, "7.Введите *количество багов/закрыто багов по Приоритету (High,Medium,Low)* через запятую  \n\nначиная с 7");
                        sendMessages(chatId, "н: *7 18/12,16/13,15/2*");
                    }catch (Exception e){
                        sendMessages(chatId, "Кажется вы что-то забыли ввести, попробуйте снова");
                    }
                    break;
                case ('7'):
                    try {
                        String[] arrayBugP = message.substring(1).split(",");
                        IOEngine.setCell7(typeReport, arrayBugP, chatId);

                        sendMessages(chatId, "8.Введите *количество багов/закрыто багов по Серьезности (Blocker,Critical,Major,Minor,Trivial)* через запятую  \n\nначиная с 8");
                        sendMessages(chatId, "н: *8 17/16,16/15,15/14,14/13,13/12*");
                    }catch (Exception e){
                        sendMessages(chatId, "Кажется вы что-то забыли ввести, попробуйте снова");
                    }
                    break;
                case ('8'):
                    try {
                        String[] arrayBugS = message.substring(1).split(",");
                        IOEngine.setCell8(typeReport, arrayBugS, chatId);

                        sendMessages(chatId, "9.Введите *Модули (общее количесвто тест-кейсов/пройденно)*  через запятую  \n\nначиная с 9 ");
                        sendMessages(chatId, "н: *9 Модуль1(11/6),Модуль2(15/1)*");
                    }catch (Exception e){
                        sendMessages(chatId, "Кажется вы что-то забыли ввести, попробуйте снова");
                    }
                    break;
                case ('9'):
                    try{
                        String[] arrayModules = message.substring(1).split(",");
                        IOEngine.setCell9(typeReport, arrayModules, chatId);

                        sendMessages(chatId, "0.Введите *Примечание* \n\nначиная с 0 ");
                    }catch (Exception e){
                        sendMessages(chatId, "Кажется вы что-то забыли ввести, попробуйте снова");
                    }
                    break;
                case ('0'):
                    try {
                        String note = message.substring(1);
                        IOEngine.setCell0(typeReport, note, chatId);

                        sendPhoto(chatId, "", "SuccessBot.jpg");
                        sendMessagesAndButton(chatId, "Готово!");
                    }catch (Exception e){
                        sendMessages(chatId, "Кажется вы что-то забыли ввести, попробуйте снова");
                    }
                    break;
            }
        } else {
            sendPhoto(chatId, "Извините, такого пункта в отчете нет!", "ErrorBot.jpg");
        }
    }

    @SneakyThrows
    public void sendReport(long chatId, String filePath) {
        filePath = chatId + filePath;
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

        IOEngine.delete(filePath);
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
                !message.equals("/start")) {
            if(message.charAt(0)=='/'){
                sendMessages(chatId, "Извините, я такой команды не знаю!");
            }else {
                sendMessages(chatId, text);
            }
        }
    }
    private String getGroupName(String messageText, int symbCountOfCommand){
        messageText = messageText.substring(symbCountOfCommand);
        for(int i =0;i<messageText.length();i++){
            messageText= messageText.replaceAll(" ","");
        }
        return messageText;
    }



}
