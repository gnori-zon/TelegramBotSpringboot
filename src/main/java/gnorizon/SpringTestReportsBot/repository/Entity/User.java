package gnorizon.SpringTestReportsBot.repository.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "users")
public class User {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private Long chatId;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    private String nameGroup;

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getChatId().equals(user.getChatId()) && getNameGroup().equals(user.getNameGroup());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChatId(), getNameGroup());
    }
}
