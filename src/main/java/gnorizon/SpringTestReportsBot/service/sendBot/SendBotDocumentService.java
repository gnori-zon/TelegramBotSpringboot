package gnorizon.SpringTestReportsBot.service.sendBot;

/**
 * Service for send documents to client
 */
public interface SendBotDocumentService {
    void sendReport(long chatId, String filePath);
    void sendPhoto(long chatId, String imageCaption, String imagePath);
}
