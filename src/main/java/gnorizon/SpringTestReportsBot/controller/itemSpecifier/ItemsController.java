package gnorizon.SpringTestReportsBot.controller.itemSpecifier;

import gnorizon.SpringTestReportsBot.repository.Entity.Report;
import org.springframework.stereotype.Controller;
/**
 * Implementation {@link ItemSpecifier}
 */
@Controller
public class ItemsController implements ItemSpecifier {
    private final String EXCEPTION_TEXT = "Кажется вы что-то забыли ввести, попробуйте снова";
    ItemsContainer itemsContainer = new ItemsContainer();
    @Override
    public String checkingAndWrite(String message, Report report) {
        try {
            char stepNumber = message.charAt(0);
            return itemsContainer.retrieveItem(stepNumber).execute(message, report);
        }catch (Exception e){
            return EXCEPTION_TEXT ;
        }
    }
}
