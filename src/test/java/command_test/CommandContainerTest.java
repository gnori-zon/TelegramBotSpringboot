package command_test;

import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.CommandContainer;
import gnorizon.SpringTestReportsBot.command.CommandName;
import gnorizon.SpringTestReportsBot.command.commands.UnknownCommand;
import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemSpecifier;
import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsController;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyServiceImpl;
import gnorizon.SpringTestReportsBot.service.fileManipulation.ReportExcelManipulationServiceImpl;
import gnorizon.SpringTestReportsBot.service.fileManipulation.ReportFileManipulationService;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseServiceImpl;
import gnorizon.SpringTestReportsBot.service.modifyRR.ModifyRepositoryReportsService;
import gnorizon.SpringTestReportsBot.service.modifyRR.ModifyRepositoryReportsServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotAllServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotDocumentService;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest {
    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        ItemSpecifier itemspecifier = Mockito.mock(ItemsController.class);
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotAllServiceImpl.class);
        SendBotDocumentService sendBotDocumentService = Mockito.mock(SendBotAllServiceImpl.class);
        ReportFileManipulationService fileManipulation = Mockito.mock(ReportExcelManipulationServiceImpl.class);
        ModifyDataBaseService modifyDataBaseService = Mockito.mock(ModifyDataBaseServiceImpl.class);
        ModifyRepositoryReportsService modifyRepositoryReportsService = Mockito.mock(ModifyRepositoryReportsServiceImpl.class);
        ClientAttributeModifyService clientAttributeModifyService = Mockito.mock(ClientAttributeModifyServiceImpl.class);

        commandContainer = new CommandContainer(itemspecifier,
                sendBotMessageService,
                sendBotDocumentService,
                fileManipulation,
                modifyDataBaseService,
                modifyRepositoryReportsService,
                clientAttributeModifyService);

    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        //when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });

    }
    @Test
    public void shouldReturnUnknownCommand(){
        //given
        String nameCommand = "/blabla";
        //when
        Command command = commandContainer.retrieveCommand(nameCommand);
        //then
        Assertions.assertEquals(UnknownCommand.class,command.getClass());
    }
}
