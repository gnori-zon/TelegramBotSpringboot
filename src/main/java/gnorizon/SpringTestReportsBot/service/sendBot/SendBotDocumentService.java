package gnorizon.SpringTestReportsBot.service.sendBot;

public interface SendBotDocumentService {
    void sendReport(long chatId, String filePath);
    void sendPhoto(long chatId, String imageCaption, String imagePath);
}
