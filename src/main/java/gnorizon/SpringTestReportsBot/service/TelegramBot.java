package gnorizon.SpringTestReportsBot.service;

import gnorizon.SpringTestReportsBot.config.BotConfig;
import gnorizon.SpringTestReportsBot.service.IOmethods.IOEngine;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot{

    final BotConfig config;
    static final String HELP_TEXT = "Этот бот формирует тест отчет из введенных вами данных и отправляет его вам в формате электронной табоицы Excel (.xlsx)\n\n" +
            "Вы можете использовать команды из главного меню в левом нижнем углу или ввести эту команду\n\n" +
            " - используйте /start для запуска бота\n"+
            " - используйте /newreprot для создания нового отчета\n"+
            " - используйте /send для отправки отчета\n" +
            " - используйте /help для получения информации о использовании бота\n\n"
            + "Удачи!";

    static final String INTERMEDIATE = "INT_REPORT_BUTTON" ;
    static final String FINAL = "FIN_REPORT_BUTTON";
    static final String ERROR_TEXT="Error occurred: ";
    static final String FINISH_TYPE = "Finish";
    static final String INTER_TYPE = "Intermediate";

    //передаем конфиг в бот
    public TelegramBot(BotConfig config){
        this.config =config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/start","get a welcome message"));
        listOfCommands.add(new BotCommand("/newreport","create a new test report"));
        listOfCommands.add(new BotCommand("/help","get info how to use bot"));
        listOfCommands.add(new BotCommand("/send","bot sends a report"));
        try{
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(),null));
        }catch (TelegramApiException e){
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
    int nameRep = 0;

    //проверка на изменения
    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();


            long chatId = update.getMessage().getChatId();
            //проверка вида отчета
            switch (nameRep){
                case 1:
                    // заполнение отчета
                    writeInReport(messageText,chatId,INTER_TYPE);
                    log.info("writeInReport to user "+ chatId);
                    break;
                case 2:
                    // заполнение отчета
                    writeInReport(messageText,chatId,FINISH_TYPE);
                    log.info("writeInReport to user "+ chatId);
                    break;
            }


            //проверка ввода на команду{
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
                    log.info("new Report to user "+ chatId);
                    nameRep = 0;
                    break;
                case ("Send report"):
                case ("/send"):
                    if(nameRep == 2){
                        sendReport(chatId,"PatternFinalReport.xlsx");
                        log.info("sendReport to user "+ chatId);
                        nameRep = 0;
                    } else if (nameRep == 1) {
                        sendReport(chatId,"PatternInterReport.xlsx");
                        log.info("sendReport to user "+ chatId);
                        nameRep = 0;
                    }else{
                        sendMessages(chatId,"Отчет не заполнен");
                    }
                    break;
            }

        } else if (update.hasCallbackQuery()) {

            String callBackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            switch (callBackData){
                case(INTERMEDIATE):
                    IOEngine.createReport(chatId,INTER_TYPE);
                    executeEditMessageText(1,"Вы, выбрали промежуточный Отчет о тестировании",chatId,messageId);
                    break;
                case(FINAL):
                    IOEngine.createReport(chatId,FINISH_TYPE);
                    executeEditMessageText(2,"Вы, выбрали финальный Отчет о тестировании",chatId,messageId);
                    break;

            }

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
    private void startCommandReceived(long chatId, String firstName){

        String answer = "Привет "+firstName+", давай создадим отчет!";
        log.info("Replied to user "+ firstName);
        sendMessagesAndButton(chatId,answer);
    }
    // отправка сообщения
    private void sendMessages(long chatId, String textToSend){

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        executeMessage(sendMessage);
    }
    //отправка сообщения с кнопками-клавиатурой
    private void sendMessagesAndButton(long chatId, String textToSend){

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

        sendMessage.setReplyMarkup(keyboardMarkup);

        executeMessage(sendMessage);
    }


    public void writeInReport(String message,long chatId,String typeReport) {
        String num = "1234567890";
        if (!num.contains(String.valueOf(message.charAt(1)))) {
            switch (message.charAt(0)){
                case ('1'):
                    String nameReport = message.substring(1,message.indexOf("-"));
                    String release = message.substring(message.indexOf("-")+1,message.lastIndexOf("-"));
                    String readiness = message.substring(message.lastIndexOf('-')+1);
                    if (typeReport.equals(FINISH_TYPE)) {
                        IOEngine.setCell1F(nameReport,release,readiness,chatId);
                        sendMessages(chatId, "2.Введите дату начала и окончания \n\nначиная с '2' \nн: 2 01.01.2001/02.02.2002");
                    } else {
                        IOEngine.setCell1I(nameReport,release,readiness,chatId);
                        sendMessages(chatId, "2.Введите дату начала/окончания/количество оставшихся дней и стенд  \n\nначиная с '2' \nн: 2 01.01.2001/02.02.2002/32-имя стенда");
                    }
                    break;
                case ('2'):
                    if (typeReport.equals(FINISH_TYPE)) {
                        String startDate = message.substring(1,message.indexOf("/"));
                        String finishDate =message.substring(message.indexOf("/")+1);
                        IOEngine.setCell2F(startDate,finishDate,chatId);
                        sendMessages(chatId, "3.Введите стенд  \n\nначиная с '3'");
                    } else {
                        String startDate = message.substring(1,message.indexOf("/"));
                        String finishDate = message.substring(message.indexOf("/")+1,message.lastIndexOf('/'));
                        String countDay =message.substring(message.lastIndexOf('/')+1,message.indexOf("-"));
                        String nameStand = message.substring(message.indexOf("-")+1);
                        IOEngine.setCell2I(startDate,finishDate,countDay,nameStand,chatId);
                        sendMessages(chatId, "3.Введите браузеры-всего тест-кейсов/пройденных тест-кейсов - всего багов/закрых багов через запятую \n\nначиная с '3'\n н: 3 Chrome-12/6-13/2, Safari-15/2-14/2");
                    }
                    break;
                case ('3'):
                    if (typeReport.equals(FINISH_TYPE)) {
                        String nameStand = message.substring(1);
                        IOEngine.setCell3F(nameStand,chatId);
                        sendMessages(chatId, "4.Введите операционные системы через запятую \n\nначиная с '4'");
                    } else {
                        String[] arrayBrowsers =message.substring(1).split(",");
                        IOEngine.setCell3I(arrayBrowsers,chatId);
                        sendMessages(chatId, "4.Введите операционные системы/всего тест-кейсов/пройденных тест-кейсов/всего багов/закрых багов через запятую \n\nначиная с '4'\nн: 4 Windows-12/6-13/2,MacOS-15/2-14/2");
                    }
                    break;
                case ('4'):
                    String[] arrayOS =message.substring(1).split(",");
                    if (typeReport.equals(FINISH_TYPE)) {
                        IOEngine.setCell4F(arrayOS,chatId);
                        sendMessages(chatId, "5.Введите функции и количество багов в них через запятую  \n\nначиная с '5' \nн: 5 функция-1,функция-2");
                    } else {
                        IOEngine.setCell4I(arrayOS,chatId);
                        sendMessages(chatId, "5.Введите функции и количество багов в них через запятую  \n\nначиная с '5' \nн: 5 функция-1,функция-2");
                    }
                    break;
                case ('5'):
                    String[] arrayFuncs =message.substring(1).split(",");
                    IOEngine.setCell5(typeReport,arrayFuncs,chatId);
                    sendMessages(chatId, "6.Введите количество всего багов/закрыто багов и всего улучшений/улучшено через -  \n\nначиная с '6' \nн: 6 15/12-16/13");
                    break;
                case ('6'):
                    String countBug = message.substring(1,message.indexOf("/"));
                    String countClosedBug = message.substring(message.indexOf("/")+1,message.indexOf("-"));
                    String countImprovement = message.substring(message.indexOf("-")+1,message.lastIndexOf("/"));
                    String countClosedImprovement = message.substring(message.lastIndexOf("/")+1);
                    IOEngine.setCell6(typeReport,countBug,countClosedBug,countImprovement,countClosedImprovement,chatId);
                    sendMessages(chatId, "7.Введите количество багов/закрыто багов по Приоритету (High,Medium,Low) через запятую  \n\nначиная с '7' \nн: 7 18/12,16/13,15/2");
                    break;
                case ('7'):
                    String[] arrayBugP =message.substring(1).split(",");
                    IOEngine.setCell7(typeReport,arrayBugP,chatId);
                    sendMessages(chatId, "8.Введите количество багов/закрыто багов по Серьезности (Blocker,Critical,Major,Minor,Trivial) через запятую  \n\nначиная с '8' \nн: 8 17/16,16/15,15/14,14/13,13/12");
                    break;
                case ('8'):
                    String[] arrayBugS =message.substring(1).split(",");
                    IOEngine.setCell8(typeReport,arrayBugS,chatId);
                    sendMessages(chatId, "9.Введите Модули (общее количесвто тест-кейсов/пройденно)  через запятую  \n\nначиная с '9' \nн: 9 Модуль1(11/6),Модуль2(15/1)");
                    break;
                case ('9'):
                    String[] arrayModules =message.substring(1).split(",");
                    IOEngine.setCell9(typeReport,arrayModules,chatId);
                    sendMessages(chatId, "0.Введите Примечание \n\nначиная с '0' ");
                    break;
                case ('0'):
                    String note = message.substring(1);
                    IOEngine.setCell0(typeReport,note,chatId);
                    sendMessagesAndButton(chatId, "Готово!");
                    break;
            }
        } else {
            sendMessages(chatId, "Извините, такой команды нет");
        }
    }

    @SneakyThrows
    public void sendReport(long chatId, String filePath){
        filePath = chatId+filePath;
        File sourceFile = new File(filePath);
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(String.valueOf(chatId));
        sendDocument.setDocument(new InputFile(sourceFile));
        log.info("sendDocument to user "+ chatId);
        execute(sendDocument);
        IOEngine.delete(filePath);
        }

    private void executeEditMessageText(int nameRep1,String text,long chatId,long messageId){
        EditMessageText message = new EditMessageText();
        nameRep = nameRep1;
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);
        sendMessages(chatId, "Введите название отчета,релиз и готовность через запятую \n\nначиная с 1\nн: 1 Имя-3-готов");

        try{
            execute(message);
        }catch (TelegramApiException e){
            log.error(ERROR_TEXT+ e.getMessage());
        }
    }

    private void executeMessage(SendMessage message){
        try{
            execute(message);
        }catch (TelegramApiException e){
            log.error(ERROR_TEXT+ e.getMessage());
        }
    }


}
