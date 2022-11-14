package gnorizon.SpringTestReportsBot.service.modifyDB;

import gnorizon.SpringTestReportsBot.repository.Entity.Group;
import gnorizon.SpringTestReportsBot.repository.GroupRepository;
import gnorizon.SpringTestReportsBot.repository.Entity.User;
import gnorizon.SpringTestReportsBot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation {@link ModifyDataBaseService}
 */
@Slf4j
@Service
public class ModifyDataBaseServiceImpl implements ModifyDataBaseService {
    private final UserRepository userRepository;
    private final  GroupRepository groupRepository;
    @Autowired
    public ModifyDataBaseServiceImpl(GroupRepository groupRepository, UserRepository userRepository){
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }
    @Override
    public String addUserInGroup(Message message, Long chatId) {
        String nameGroup = getGroupName(message.getText(),6);
        if(userRepository.findById(chatId+nameGroup).isEmpty()){
            if(groupRepository.findById(nameGroup).isPresent()) {
                User user = new User();
                user.setId(chatId + nameGroup);
                user.setNameGroup(nameGroup);
                user.setChatId(chatId);
                userRepository.save(user);
                log.info("user saved: " + user);
                return "Вы вошли в группу";
            }else{
                return "Такой группы нет";
            }
        }else{
            return "Вы уже есть в этой группе";
        }
    }

    @Override
    public String createGroup(Message message,Long owner) {
        String nameGroup = getGroupName(message.getText(),9);
        if(groupRepository.findById(nameGroup).isEmpty()){
            Group group = new Group();

            group.setNameGroup(nameGroup);
            group.setOwner(owner);
            groupRepository.save(group);
            log.info("group saved: " + group);
            return "Готово! \n Имя группы: "+ group.getNameGroup();
        } else {
            return "Такая группа уже есть";
        }
    }
    @Override
    public String deleteFromGroup(String nameGroup, Long chatId){
        var users = userRepository.findAllBy(nameGroup,chatId);
        for(User user : users){
            userRepository.deleteById(user.getId());
            log.info("user: " + user+" remove from group: " + nameGroup);
            return "Вы вышли из группы!";
        }
            return "Вы не состоите в этой группе";
    }
    @Override
    public String dropGroup(String  nameGroup,Long chatId){
        var users = userRepository.findAllBy(nameGroup);
        users.forEach(user -> userRepository.deleteById(user.getId()));
        groupRepository.deleteById(nameGroup);
        log.info("user: " + chatId +" drop self group: " + nameGroup);
        return "Группа распущена и удалена!";
    }
    @Override
    public Map<Long,String> requestReports(String nameGroup,Long chatId){
        Map<Long,String> dataAboutUsers= new HashMap<>();
        Long owner = groupRepository.findById(nameGroup).get().getOwner();
        var users = userRepository.findAllBy(nameGroup);
        users.forEach(user -> dataAboutUsers.put(user.getChatId(),"Группа: "+nameGroup+"-Подготовьте отчет!"));
        return dataAboutUsers;
    }
    @Override
    public HashMap<String,String> getAllGroup(Long chatId){
        HashMap<String,String> mapGroups = new HashMap<>();
        userRepository.findAllBy(chatId).forEach(element -> mapGroups.put(element.getNameGroup(), "Участник"));
        groupRepository.findAllByChatId(chatId).forEach(element -> mapGroups.put(element.getNameGroup(), "Владелец"));
        return mapGroups;
    }

    private String getGroupName(String messageText, int symbCountOfCommand){
        messageText = messageText.substring(symbCountOfCommand);
        for(int i =0;i<messageText.length();i++){
            messageText= messageText.replaceAll(" ","");
        }
        return messageText;
    }
}
