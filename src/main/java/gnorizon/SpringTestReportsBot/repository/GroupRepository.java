package gnorizon.SpringTestReportsBot.repository;

import gnorizon.SpringTestReportsBot.repository.Entity.Group;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * {@link Repository} for {@link Group} entity.
 */
@Repository
public interface GroupRepository extends CrudRepository <Group,String> {

}
