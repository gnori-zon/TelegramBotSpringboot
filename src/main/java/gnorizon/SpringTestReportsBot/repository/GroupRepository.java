package gnorizon.SpringTestReportsBot.repository;

import gnorizon.SpringTestReportsBot.repository.Entity.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository <Group,String> {

}
