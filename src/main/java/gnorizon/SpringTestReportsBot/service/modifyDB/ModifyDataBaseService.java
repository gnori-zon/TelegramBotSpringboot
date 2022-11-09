package gnorizon.SpringTestReportsBot.service.modifyDB;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.Map;

public interface ModifyDataBaseService {
    HashMap<String,String> getAllGroup(Long chatId);
    Map<Long,String> requestReports(Message message);
    String dropGroup(Message message,Long chatId);
    String deleteFromGroup(Message message, Long chatId);
    String createGroup(Message message,Long owner);
    String addUserInGroup(Message message, Long chatId);
}
