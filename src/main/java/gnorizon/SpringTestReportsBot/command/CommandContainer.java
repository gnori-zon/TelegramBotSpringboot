package gnorizon.SpringTestReportsBot.command;

import com.google.common.collect.ImmutableMap;
import gnorizon.SpringTestReportsBot.command.commands.*;
import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemSpecifier;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.fileManipulation.ReportFileManipulationService;
import gnorizon.SpringTestReportsBot.service.modifyRR.ModifyRepositoryReportsService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotDocumentService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.springframework.beans.factory.annotation.Autowired;

import static gnorizon.SpringTestReportsBot.command.CommandName.*;

public class CommandContainer {
    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;
    private final Command writeCommand;
    private final Command addReportToRepo;
    @Autowired
    public CommandContainer(ItemSpecifier itemspecifier,
                            SendBotMessageService sendBotMessageService,
                            SendBotDocumentService sendBotDocumentService,
                            ReportFileManipulationService fileManipulation,
                            ModifyDataBaseService modifyDataBaseService,
                            ModifyRepositoryReportsService modifyRepositoryReportsService,
                            ClientAttributeModifyService clientAttributeModifyService){
        commandMap = ImmutableMap.<String,Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService))
                .put(NEW_REPORT.getCommandName(), new NewReportCommand(sendBotMessageService,
                                                                       clientAttributeModifyService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(SEND.getCommandName(), new SendReportCommand(fileManipulation,
                                                                  sendBotMessageService,
                                                                  sendBotDocumentService,
                                                                  clientAttributeModifyService,
                                                                  modifyRepositoryReportsService))
                .put(ADD_ME.getCommandName(), new AddMeForGroupCommand(sendBotMessageService,
                                                                       modifyDataBaseService))
                .put(NEW_GROUP.getCommandName(), new NewGroupCommand(sendBotMessageService,
                                                                     modifyDataBaseService))
                .put(REQ_REP.getCommandName(), new RequestForReportCommand(sendBotMessageService,
                                                                           modifyDataBaseService))
                .put(DEL_ME.getCommandName(), new DeleteMeForGroupCommand(sendBotMessageService,
                                                                          modifyDataBaseService))
                .put(DEL_GROUP.getCommandName(), new DeleteGroupCommand(sendBotMessageService,
                                                                        modifyDataBaseService))
                .put(MY_GROUPS.getCommandName(), new GetMyGroupNamesCommand(sendBotMessageService,
                                                                            modifyDataBaseService))
                .put(NO.getCommandName(), new NotCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotDocumentService,
                                            clientAttributeModifyService);
        writeCommand = new WriteInReportCommand(itemspecifier,
                                                sendBotMessageService,
                                                sendBotDocumentService,
                                                clientAttributeModifyService,
                                                modifyRepositoryReportsService);
        addReportToRepo = new AddReportToRepositoryCommand(modifyRepositoryReportsService,
                                                    sendBotMessageService,
                                                    clientAttributeModifyService);
    }

    public Command retrieveCommand(String commandIdentifier){
        return commandMap.getOrDefault(commandIdentifier,unknownCommand);
    }
    public Command writeCommand(){
        return writeCommand;
    }
    public Command addReportToRepo(){
        return addReportToRepo;
    }
}
