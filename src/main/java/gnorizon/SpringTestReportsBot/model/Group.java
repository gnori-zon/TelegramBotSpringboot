package gnorizon.SpringTestReportsBot.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "groups_user")
public class Group {

    @Id
    private String nameGroup;
    private Long owner;

    public String getNameGroup() {
        return nameGroup;
    }


    public Long getOwner() {
        return owner;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }


    public void setOwner(Long owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Group{" +
                "nameGroup='" + nameGroup + '\'' +
                ", owner=" + owner +
                '}';
    }
}
