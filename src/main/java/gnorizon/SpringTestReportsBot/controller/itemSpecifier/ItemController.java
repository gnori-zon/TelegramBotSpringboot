package gnorizon.SpringTestReportsBot.controller.itemSpecifier;

import gnorizon.SpringTestReportsBot.config.Items;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;
import gnorizon.SpringTestReportsBot.service.ReportFillerService;
import org.springframework.stereotype.Controller;

import static gnorizon.SpringTestReportsBot.TelegramBot.FINISH_TYPE;

@Controller
public class ItemController implements ItemSpecifier{
    private final String LAST_TEXT = "src/main/resources/static/SuccessBot.jpg" + "XSXГотово!";
    private final String EXCEPTION_TEXT = "Кажется вы что-то забыли ввести, попробуйте снова";
    @Override
    public String checkingAndWrite(String message, Report report){
        int regex = report.getName().indexOf('_');
        String typeReport = report.getName().substring(regex+1) ;

        ReportFillerService filler = new ReportFillerService(report,typeReport);
        char stepNumber = message.charAt(0);
        try {
                if(stepNumber== Items.ITEM_1.step) {
                    filler.fillItem1(message);

                    if (typeReport.equals(FINISH_TYPE)) {
                        return Items.ITEM_2.textForStep;
                    } else {
                        return Items.ITEM_2.textForStep2;
                    }
                }else if(stepNumber== Items.ITEM_2.step) {
                    filler.fillItem2(message);

                    if (typeReport.equals(FINISH_TYPE)) {
                        return Items.ITEM_3.textForStep;
                    } else {
                        return Items.ITEM_3.textForStep2;
                    }
                }else if(stepNumber== Items.ITEM_3.step) {
                    filler.fillItem3(message);

                    if (typeReport.equals(FINISH_TYPE)) {
                        return Items.ITEM_4.textForStep;
                    } else {
                        return Items.ITEM_4.textForStep2;
                    }
                }else if(stepNumber== Items.ITEM_4.step) {
                    filler.fillItem4(message);
                    return Items.ITEM_5.textForStep;
                }else if(stepNumber== Items.ITEM_5.step) {
                    filler.fillItem5(message);

                    return Items.ITEM_6.textForStep;
                }else if(stepNumber== Items.ITEM_6.step) {
                    filler.fillItem6(message);

                    return Items.ITEM_7.textForStep;
                }else if(stepNumber== Items.ITEM_7.step) {
                    filler.fillItem7(message);

                    return Items.ITEM_8.textForStep;
                }else if(stepNumber== Items.ITEM_8.step) {
                    filler.fillItem8(message);

                    return Items.ITEM_9.textForStep;
                }else if(stepNumber== Items.ITEM_9.step) {
                        filler.fillItem9(message);

                        return Items.ITEM_10.textForStep;
                }else if(stepNumber== Items.ITEM_10.step) {
                    filler.fillItem0(message);

                    return LAST_TEXT ;
            }
        }catch (Exception e){
            return EXCEPTION_TEXT ;
        }
        return "oups!";
    }
}
