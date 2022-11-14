package gnorizon.SpringTestReportsBot.repository;

import gnorizon.SpringTestReportsBot.repository.Entity.Group;
import gnorizon.SpringTestReportsBot.repository.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * {@link Repository} for {@link User} entity.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
    @Query(value = "SELECT * FROM users WHERE name_group = :nameGroup",nativeQuery = true)
    Iterable<User> findAllBy(String nameGroup);
    @Query(value = "SELECT * FROM users WHERE name_group = :nameGroup and chat_id = :chatId",nativeQuery = true)
    Iterable<User> findAllBy(String nameGroup,Long chatId);
    @Query(value = "SELECT * FROM users WHERE chat_id = :chatId",nativeQuery = true)
    Iterable<User> findAllBy(Long chatId);

}
