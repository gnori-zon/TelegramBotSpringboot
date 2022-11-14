package gnorizon.SpringTestReportsBot;

import gnorizon.SpringTestReportsBot.repository.Entity.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static gnorizon.SpringTestReportsBot.ModifyDataBaseServiceImplTest.getChatId;
import static gnorizon.SpringTestReportsBot.ModifyDataBaseServiceImplTest.getNameGroup;

@SpringBootTest(classes = SpringTestReportsBotApplicationTests.class)
class SpringTestReportsBotApplicationTests {

//	@Test
//	void contextLoads() {
//	}
//	@Test
//	@DisplayName("Test for correctly modify DB.")
//	void modifyDBServicePositiveTest(){
//		String result = new ModifyDataBaseServiceImplTest().
//				checkAddUserInGroup(Optional.empty(),Optional.of(new Group()));
//		Assertions.assertEquals("Вы вошли в группу",result);
//
//		result = new ModifyDataBaseServiceImplTest().
//				checkCreateGroup(Optional.empty());
//		Assertions.assertEquals("Готово! \n Имя группы: "+ getNameGroup(),result);
//
//		result = new ModifyDataBaseServiceImplTest().
//				checkDeleteFromGroup(Optional.of(new Group()),getChatId());
//		Assertions.assertEquals("Вы вышли из группы!",result);
//
//		Group group = new Group();
//		group.setOwner(getChatId());
//		result = new ModifyDataBaseServiceImplTest().
//				checkDropGroup(Optional.of(group),getChatId());
//		Assertions.assertEquals("Группа распущена и удалена!",result);
//
//		group.setOwner(getChatId());
//		result = new ModifyDataBaseServiceImplTest().
//				checkRequestReports(Optional.of(group));
//		Assertions.assertEquals("{123=Сообщения отправлены!}",result);
//
//		group.setNameGroup("Bla-bla");
//		result = new ModifyDataBaseServiceImplTest().
//				checkGetAllGroup(group,group.getOwner());
//		Assertions.assertEquals("{"+group.getNameGroup()+"=Владелец, "+getNameGroup()+"=Участник}",result);
//	}
//	@Test
//	void modifyDBServiceAllTest(){
//
//
//	}
}
