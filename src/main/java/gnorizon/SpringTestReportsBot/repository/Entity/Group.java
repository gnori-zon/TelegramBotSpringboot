package gnorizon.SpringTestReportsBot.repository.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return getNameGroup().equals(group.getNameGroup()) && getOwner().equals(group.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNameGroup(), getOwner());
    }
}
