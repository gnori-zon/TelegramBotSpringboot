package gnorizon.SpringTestReportsBot.repository;

import gnorizon.SpringTestReportsBot.repository.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
