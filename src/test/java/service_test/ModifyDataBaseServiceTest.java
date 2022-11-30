package service_test;

import gnorizon.SpringTestReportsBot.repository.Entity.Group;
import gnorizon.SpringTestReportsBot.repository.Entity.User;
import gnorizon.SpringTestReportsBot.repository.GroupRepository;
import gnorizon.SpringTestReportsBot.repository.UserRepository;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseService;
import gnorizon.SpringTestReportsBot.service.modifyDB.ModifyDataBaseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockingDetails;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@DisplayName("Unit-level testing for ModifyDataBaseService")
public class ModifyDataBaseServiceTest {
    ModifyDataBaseService modifyDataBaseService;
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private Message message;
    private Long chatId = 124124l;
    private String nameGroup = "fff";


    @BeforeEach
    public void init(){
        groupRepository = Mockito.mock(GroupRepository.class);
        userRepository = Mockito.mock(UserRepository.class);

        modifyDataBaseService = new ModifyDataBaseServiceImpl(groupRepository,userRepository);
    }
    private User prepareUser(Long chatId,String nameGroup){
        User user = new User();
        user.setId(chatId + nameGroup);
        user.setNameGroup(nameGroup);
        user.setChatId(chatId);
        return user;
    }
    private Group prepareGroup(Long chatId,String nameGroup){
        Group group = new Group();
        group.setNameGroup(nameGroup);
        group.setOwner(chatId);
        return group;
    }

    private void prepareMessage(String nameCommand){
        message = Mockito.mock(Message.class);
        Mockito.when(message.getText()).thenReturn(nameCommand);
    }
    @Test
    public void addUserInGroupSuccess(){
        //given
        String nameCommand = "/addme " + nameGroup;

        prepareMessage(nameCommand);
        User user = prepareUser(chatId,nameGroup);
        Group group = Mockito.mock(Group.class);

        Mockito.when(userRepository.findById(chatId+nameGroup)).thenReturn(Optional.empty());
        Mockito.when(groupRepository.findById(nameGroup)).thenReturn(Optional.of(group));
        //when
        String result = modifyDataBaseService.addUserInGroup(message,chatId);
        //then
        Mockito.verify(userRepository).save(user);
        Assertions.assertEquals("Вы вошли в группу",result);
    }

    @Test
    public void addUserInGroupGroupIsEmpty(){
        //given
        String nameCommand = "/addme " + nameGroup;
        prepareMessage(nameCommand);

        Mockito.when(userRepository.findById(chatId+nameGroup)).thenReturn(Optional.empty());
        Mockito.when(groupRepository.findById(nameGroup)).thenReturn(Optional.empty());
        //when
        String result = modifyDataBaseService.addUserInGroup(message,chatId);
        //then
        Assertions.assertEquals("Такой группы нет",result);
    }

    @Test
    public void addUserInGroupUserAlreadyInGroup(){
        //given
        String nameCommand = "/addme " + nameGroup;
        prepareMessage(nameCommand);

        Mockito.when(userRepository.findById(chatId+nameGroup)).thenReturn(Optional.of(new User()));
        //when
        String result = modifyDataBaseService.addUserInGroup(message,chatId);
        //then
        Assertions.assertEquals("Вы уже есть в этой группе",result);
    }


    @Test
    public void createGroupSuccess(){
        //given
        String nameCommand = "/newgroup " + nameGroup;

        prepareMessage(nameCommand);
        Group group = prepareGroup(chatId,nameGroup);

        Mockito.when(groupRepository.findById(nameGroup)).thenReturn(Optional.empty());
        //when
        String result = modifyDataBaseService.createGroup(message,chatId);
        //then
        Mockito.verify(groupRepository).save(group);
        Assertions.assertEquals("Готово! \n Имя группы: "+ group.getNameGroup(),result);
    }
    @Test
    public void createGroupGroupIsPresent(){
        //given
        String nameCommand = "/newgroup " + nameGroup;

        prepareMessage(nameCommand);
        Group group = prepareGroup(chatId,nameGroup);

        Mockito.when(groupRepository.findById(nameGroup)).thenReturn(Optional.of(group));
        //when
        String result = modifyDataBaseService.createGroup(message,chatId);
        //then
        Assertions.assertEquals("Такая группа уже есть",result);
    }


    @Test
    public void deleteFromGroupSuccess(){
        //given
        User user = prepareUser(chatId,nameGroup);

        Mockito.when(userRepository.findAllBy(nameGroup,chatId)).thenReturn(List.of(user));
        //when
        String result = modifyDataBaseService.deleteFromGroup(nameGroup,chatId);
        //then
        Mockito.verify(userRepository).deleteById(user.getId());
        Assertions.assertEquals("Вы покинули группу: ",result);
    }
    @Test
    public void deleteFromGroupArentInGroup(){
        //given
        Mockito.when(userRepository.findAllBy(nameGroup,chatId)).thenReturn(List.of());
        //when
        String result = modifyDataBaseService.deleteFromGroup(nameGroup,chatId);
        //then
        Assertions.assertEquals("Вы покинули группу: ",result);
    }


    @Test
    public void dropGroupSuccess(){
        //given
        User user = prepareUser(chatId,nameGroup);

        Mockito.when(userRepository.findAllBy(nameGroup)).thenReturn(List.of(user));
        //when
        String result = modifyDataBaseService.dropGroup(nameGroup,chatId);
        //then
        Mockito.verify(userRepository).deleteById(user.getId());
        Mockito.verify(groupRepository).deleteById(nameGroup);
        Assertions.assertEquals("Вы распустили группу: ",result);
    }

    @Test
    public void requestReportsSuccess(){
        //given
        Map<Long,String> dataAboutUsers= new HashMap<>();
        User user = prepareUser(chatId,nameGroup);
        dataAboutUsers.put(user.getChatId(),"Группа: "+nameGroup+"-Подготовьте отчет!");

        Mockito.when(userRepository.findAllBy(nameGroup)).thenReturn(List.of(user));
        //when
        Map<Long,String> dataAboutUsersActual = modifyDataBaseService.requestReports(nameGroup,chatId);
        //then
        Assertions.assertEquals(dataAboutUsers,dataAboutUsersActual);
    }

    @Test
    public void getAllGroupSuccess(){
        //given
        HashMap<String,String> mapGroups = new HashMap<>();
        User user = prepareUser(chatId,nameGroup);
        mapGroups.put(user.getNameGroup(), "Участник");
        Group group = prepareGroup(chatId,nameGroup);
        mapGroups.put(group.getNameGroup(), "Владелец");

        Mockito.when(userRepository.findAllBy(chatId)).thenReturn(List.of(user));
        Mockito.when(groupRepository.findAllByChatId(chatId)).thenReturn(List.of(group));
        //when
        HashMap<String,String> mapGroupsActual = modifyDataBaseService.getAllGroup(chatId);
        //then
        Assertions.assertEquals(mapGroups,mapGroupsActual);
    }

}
