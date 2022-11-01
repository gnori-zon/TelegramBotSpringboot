package gnorizon.SpringTestReportsBot.model.DB;

import gnorizon.SpringTestReportsBot.model.DB.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}
