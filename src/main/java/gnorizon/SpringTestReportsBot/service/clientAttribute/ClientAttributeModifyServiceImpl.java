package gnorizon.SpringTestReportsBot.service.clientAttribute;

import gnorizon.SpringTestReportsBot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientAttributeModifyServiceImpl implements ClientAttributeModifyService{
    TelegramBot telegramBot;
    @Autowired
    public ClientAttributeModifyServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public int getNameRep() {
        return telegramBot.getNameRep();
    }

    @Override
    public void setNameRep(int nameRep) {
        telegramBot.setNameRep(nameRep);
    }
}
