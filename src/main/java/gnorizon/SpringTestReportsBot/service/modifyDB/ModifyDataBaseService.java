package gnorizon.SpringTestReportsBot.service.modifyDB;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for working with data base
 */
public interface ModifyDataBaseService {
    HashMap<String,String> getAllGroup(Long chatId);
    Map<Long,String> requestReports(String nameGroup,Long chatId);
    String dropGroup(String nameGroup,Long chatId);
    String deleteFromGroup(String nameGroup, Long chatId);
    String createGroup(Message message,Long owner);
    String addUserInGroup(Message message, Long chatId);
}
