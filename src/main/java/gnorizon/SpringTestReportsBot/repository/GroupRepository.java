package gnorizon.SpringTestReportsBot.repository;

import gnorizon.SpringTestReportsBot.repository.Entity.Group;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;
import gnorizon.SpringTestReportsBot.repository.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * {@link Repository} for {@link Group} entity.
 */
@Repository
public interface GroupRepository extends CrudRepository <Group,String> {
    @Query(value = "SELECT * FROM groups_user WHERE owner = :owner",nativeQuery = true)
    Iterable<Group> findAllByChatId(Long owner);

}
