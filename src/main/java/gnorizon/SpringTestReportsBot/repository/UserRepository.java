package gnorizon.SpringTestReportsBot.repository;

import gnorizon.SpringTestReportsBot.repository.Entity.Group;
import gnorizon.SpringTestReportsBot.repository.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * {@link Repository} for {@link User} entity.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
