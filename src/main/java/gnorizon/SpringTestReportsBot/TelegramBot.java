package gnorizon.SpringTestReportsBot;

import gnorizon.SpringTestReportsBot.command.CommandContainer;
import gnorizon.SpringTestReportsBot.config.BotConfig;

import gnorizon.SpringTestReportsBot.repository.GroupRepository;
import gnorizon.SpringTestReportsBot.repository.UserRepository;

import gnorizon.SpringTestReportsBot.repository.RepositoryReports;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyServiceImpl;
import gnorizon.SpringTestReportsBot.service.fileManipulation.ReportFileManipulationService;
import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemSpecifier;
import gnorizon.SpringTestReportsBot.service.modifyRR.ModifyRepositoryReportsService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotAllServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

import static gnorizon.SpringTestReportsBot.command.CommandName.NO;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig config;
    public static final String FINISH_TYPE = "Finish";
    public static final String INTER_TYPE = "Intermediate";
    private final CommandContainer commandContainer;
    private int nameRep = 0;
    public int getNameRep() {
        return nameRep;
    }

    public void setNameRep(int nameRep) {
        this.nameRep = nameRep;
    }


    //передаем конфиг в бот
    @Autowired
    public TelegramBot(BotConfig config, GroupRepository groupRepository, UserRepository userRepository,
                       ModifyDataBaseService modifyDataBaseService, RepositoryReports reports,
                       ReportFileManipulationService fileManipulation, ModifyRepositoryReportsService modifyRepositoryReportsService,
                       ItemSpecifier itemSpecifier) {

        SendBotAllServiceImpl sendService = new SendBotAllServiceImpl(this);
        ClientAttributeModifyService clientAttributeModifyService = new ClientAttributeModifyServiceImpl(this);

        commandContainer = new CommandContainer(itemSpecifier,sendService, sendService,fileManipulation,
                modifyDataBaseService,modifyRepositoryReportsService,clientAttributeModifyService);

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

            //проверка вида отчета и заполнение отчета в определенный ранее тип
            if (nameRep != 0) {
                commandContainer.writeCommand().execute(update);
            }
            if (messageText.startsWith("/")) {
                String commandIdentifier = messageText.split(" ")[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).execute(update);

            } else {
                String commandIdentifier = "/" + messageText.replaceAll(" ", "")
                        .replaceAll("Sendreport", "send").toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
                if(nameRep==0 && !("/send/help/newreport".contains(commandIdentifier)) ) {
                    commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
                }
            }
        } else if (update.hasCallbackQuery()) {
            commandContainer.retrieveCommandCB(update.getCallbackQuery().getData().substring(0,6)).execute(update);
        }
    }

}
