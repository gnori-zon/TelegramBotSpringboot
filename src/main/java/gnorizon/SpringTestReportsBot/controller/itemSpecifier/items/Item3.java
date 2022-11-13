package gnorizon.SpringTestReportsBot.controller.itemSpecifier.items;

import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsName;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gnorizon.SpringTestReportsBot.TelegramBot.FINISH_TYPE;

public class Item3 implements Item{
    @Override
    public String execute(String message, Report report) {
        int regex = report.getName().indexOf('_');
        String typeReport = report.getName().substring(regex+1);
        message = message.substring(1);
        if (typeReport.equals(FINISH_TYPE)) {
            report.environment.setStandName(message.charAt(0)==' '? message.substring(1) : message);
        }else{
            String arrayBrowsers[] = message.split(",");
            Map<String, List<Integer>> browsersInformation = new HashMap<>();
            // количество элементов не должно превышат 6
            int x = Math.min(arrayBrowsers.length, 6);
            for (int i = 0; i < x; i++){
                String nameBrowser =arrayBrowsers[i].substring(1,arrayBrowsers[i].indexOf("-"));
                Integer countBug = Integer.parseInt(arrayBrowsers[i].substring(arrayBrowsers[i].indexOf("-")+1,arrayBrowsers[i].indexOf("/")));
                Integer countClosedBug = Integer.parseInt(arrayBrowsers[i].substring(arrayBrowsers[i].indexOf("/")+1,arrayBrowsers[i].lastIndexOf("-")));
                Integer countImprovement = Integer.parseInt(arrayBrowsers[i].substring(arrayBrowsers[i].lastIndexOf("-")+1,arrayBrowsers[i].lastIndexOf("/")));
                Integer countClosedImprovement = Integer.parseInt(arrayBrowsers[i].substring(arrayBrowsers[i].lastIndexOf("/")+1));

                browsersInformation.put(nameBrowser, List.of(countBug,
                        countClosedBug,
                        countImprovement,
                        countClosedImprovement));

            }
            report.environment.setBrowsersInformation(browsersInformation);
        }

        if (typeReport.equals(FINISH_TYPE)) {
            return ItemsName.ITEM_4.textForStep;
        } else {
            return ItemsName.ITEM_4.textForNotFinalRep;
        }
    }
}
