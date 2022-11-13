package gnorizon.SpringTestReportsBot.command;

import com.google.common.collect.ImmutableMap;
import gnorizon.SpringTestReportsBot.command.commands.*;
import gnorizon.SpringTestReportsBot.command.commands.callback.*;
import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemSpecifier;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.fileManipulation.ReportFileManipulationService;
import gnorizon.SpringTestReportsBot.service.modifyRR.ModifyRepositoryReportsService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotDocumentService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static gnorizon.SpringTestReportsBot.command.CommandName.*;
/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
public class CommandContainer {
    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;
    private final Command writeCommand;
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
                .put(REQ_REP.getCommandName(), new RequestReportCommand(sendBotMessageService,
                                                                        modifyDataBaseService))
                .put(DEL_ME.getCommandName(), new DeleteMeGroupCommand(sendBotMessageService,
                                                                       modifyDataBaseService))
                .put(DEL_GROUP.getCommandName(), new DeleteMyGroupCommand(sendBotMessageService,
                                                                          modifyDataBaseService))
                .put(MY_GROUPS.getCommandName(), new GetMyGroupNamesCommand(sendBotMessageService,
                                                                          modifyDataBaseService))
                .put(NO.getCommandName(), new NotCommand(sendBotMessageService))
                .put(CANCEL_CB.getCommandName(), new CancelSelectCommand(sendBotMessageService))
                .put(DEL_ME_CB.getCommandName(), new DeleteMeGroupSelectCommand(sendBotMessageService,
                                                                                modifyDataBaseService))
                .put(DEL_GROUP_CB.getCommandName(), new DeleteMyGroupSelectCommand(sendBotMessageService,
                                                                                   modifyDataBaseService))
                .put(REQ_REP_CB.getCommandName(), new RequestReportSelectCommand(sendBotMessageService,
                                                                                 modifyDataBaseService))
                .put(ADD_REP_TO_REPO.getCommandName(), new AddReportToRepositoryCommand(modifyRepositoryReportsService,
                        sendBotMessageService,
                        clientAttributeModifyService))
                .build();

        unknownCommand = new UnknownCommand(sendBotDocumentService,
                                            clientAttributeModifyService);
        writeCommand = new WriteInReportCommand(itemspecifier,
                                                sendBotMessageService,
                                                sendBotDocumentService,
                                                clientAttributeModifyService,
                                                modifyRepositoryReportsService);

    }

    public Command retrieveCommand(String commandIdentifier){
        return commandMap.getOrDefault(commandIdentifier,unknownCommand);
    }
    public Command retrieveCommandCB(String commandIdentifier){
        Set<String> key = commandMap.keySet();
        for(String name : key) {
            if(name.contains(commandIdentifier)) {
                return commandMap.getOrDefault(name, unknownCommand);
            }
        }
        return unknownCommand;
    }

    public Command writeCommand(){
        return writeCommand;
    }
}
