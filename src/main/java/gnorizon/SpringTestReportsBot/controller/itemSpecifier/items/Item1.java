package gnorizon.SpringTestReportsBot.controller.itemSpecifier.items;

import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsName;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;

import static gnorizon.SpringTestReportsBot.TelegramBot.FINISH_TYPE;

public class Item1 implements Item{

    @Override
    public String execute(String message,Report report) {
        String[] data = message.substring(1).split("-");
        int regex = report.getName().indexOf('_');
        String typeReport = report.getName().substring(regex+1);

        report.generalInformation.setName(data[0].charAt(0)==' '?data[0].substring(1) : data[0]);
        report.generalInformation.setNumberRelease(data[1]);
        report.generalInformation.setReadiness(data[2]);

        if (typeReport.equals(FINISH_TYPE)) {
            return ItemsName.ITEM_2.textForStep;
        } else {
            return ItemsName.ITEM_2.textForNotFinalRep;
        }
    }
}
