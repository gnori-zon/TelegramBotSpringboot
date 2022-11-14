package gnorizon.SpringTestReportsBot;

import gnorizon.SpringTestReportsBot.repository.Entity.Group;
import gnorizon.SpringTestReportsBot.repository.Entity.User;
import gnorizon.SpringTestReportsBot.repository.GroupRepository;
import gnorizon.SpringTestReportsBot.repository.UserRepository;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseServiceImpl;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ModifyDataBaseServiceImplTest {
//    private static long chatId = 123;
//    private static String nameGroup = "nameGroup";
//
//    public static String getNameGroup() {
//        return nameGroup;
//    }
//
//    public static long getChatId() {
//        return chatId;
//    }
//
//    private static Message message = new Message();
//    private static User user = mock(User.class);
//    static {
//        user.setId(chatId + nameGroup);
//        user.setNameGroup(nameGroup);
//        user.setChatId(chatId);
//    }
//    private static UserRepository userRepository = mock(UserRepository.class);
//    private static GroupRepository groupRepository = mock(GroupRepository.class);
//    private static ModifyDataBaseServiceImpl service = new ModifyDataBaseServiceImpl(groupRepository,userRepository);
//    public String checkGetAllGroup(Group group, long chatId){
//        message.setText("/mygroups "+ nameGroup);
//        given(userRepository.findAll()).willReturn(List.of(user,new User()));
//        when(user.getId()).thenReturn(String.valueOf(chatId));
//        given(groupRepository.findAll()).willReturn(List.of(group,new Group()));
//
//        String result = service.getAllGroup(chatId).toString();
//        return result;
//    }
//
//    public String checkRequestReports(Optional groupFindWillReturn){
//        message.setText("/reqrep "+ nameGroup);
//        Chat chat = new Chat();
//        chat.setId(chatId);
//        message.setChat(chat);
//
//        given(groupRepository.findById(nameGroup)).willReturn(groupFindWillReturn);
//        given(userRepository.findAll()).willReturn(List.of(user,new User()));
//        when(user.getNameGroup()).thenReturn(nameGroup);
//
//        String result = service.requestReports(message).toString();
//        return result;
//    }
//
//    public String checkDropGroup(Optional groupFindWillReturn,long ownerChatId){
//        message.setText("/delgroup "+ nameGroup);
//        given(groupRepository.findById(nameGroup)).willReturn(groupFindWillReturn);
//        given(userRepository.findAll()).willReturn(List.of(user,new User()));
//        when(user.getNameGroup()).thenReturn(nameGroup);
//
//        String result = service.dropGroup(message,ownerChatId);
//        return result;
//    }
//
//    public String checkDeleteFromGroup(Optional groupFindWillReturn, long chatID){
//        message.setText("/delme "+ nameGroup);
//
//        given(groupRepository.findById(nameGroup)).willReturn(groupFindWillReturn);
//        given(userRepository.findAll()).willReturn(List.of(user,new User()));
//        when(user.getNameGroup()).thenReturn(nameGroup);
//        when(user.getChatId()).thenReturn(chatID);
//
//        String result = service.deleteFromGroup(message,chatId);
//        return result;
//    }
//
//    public String checkCreateGroup(Optional groupFindWillReturn){
//        message.setText("/newgroup "+ nameGroup);
//        given(groupRepository.findById(nameGroup)).willReturn(groupFindWillReturn);
//
//        String result = service.createGroup(message,chatId);
//        return result;
//    }
//
//    public String checkAddUserInGroup(Optional userFindWillReturn,Optional groupFindWillReturn){
//        message.setText("/addme "+ nameGroup);
//
//        given(userRepository.findById(chatId+nameGroup)).willReturn(userFindWillReturn);
//        given(groupRepository.findById(nameGroup)).willReturn(groupFindWillReturn);
//
//        String result = service.addUserInGroup(message,chatId);
//        return result;
//    }
}
