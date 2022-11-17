package command_test.commands_test;

import gnorizon.SpringTestReportsBot.TelegramBot;
import gnorizon.SpringTestReportsBot.command.Command;
import gnorizon.SpringTestReportsBot.command.commands.UnknownCommand;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyService;
import gnorizon.SpringTestReportsBot.service.clientAttribute.ClientAttributeModifyServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotAllServiceImpl;
import gnorizon.SpringTestReportsBot.service.sendBot.SendBotDocumentService;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;

import static gnorizon.SpringTestReportsBot.command.commands.UnknownCommand.IMAGE_PATH_ERROR;
import static gnorizon.SpringTestReportsBot.command.commands.UnknownCommand.TEXT_TO_SEND_UC;
import static org.mockito.Mockito.mock;

@DisplayName("Unit-level testing for UnknownCommand")
public class UnknownCommandTest {
    private TelegramBot telegramBot = mock(TelegramBot.class);
    private SendBotDocumentService sendBotDocumentService = new SendBotAllServiceImpl(telegramBot);
    private ClientAttributeModifyService clientAttributeModifyService = new ClientAttributeModifyServiceImpl(telegramBot);
    String getCommandName(){
        return "/asdfre";
    }
    String getCommandMessage(){
        return TEXT_TO_SEND_UC;
    }
    Command getCommand(){
        clientAttributeModifyService.setNameRep(0);
        return new UnknownCommand(sendBotDocumentService,clientAttributeModifyService);
    }

    //@Test- don't work
    public void shouldProperlyExecuteCommand() throws TelegramApiException, FileNotFoundException {
        //given
        Long chatId = 1234565534l;

        Update update = AbstractCommandTest.prepareUpdate(chatId, getCommandName());

        File image = ResourceUtils.getFile(IMAGE_PATH_ERROR);
        SendPhoto sendPhoto = Mockito.mock(SendPhoto.class);
        sendPhoto.setPhoto(new InputFile(image));
        sendPhoto.setChatId(String.valueOf(chatId));
        sendPhoto.setCaption(TEXT_TO_SEND_UC);
        sendPhoto.setProtectContent(true);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(telegramBot).execute(sendPhoto);
    }
}
