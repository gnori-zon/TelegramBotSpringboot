package gnorizon.SpringTestReportsBot.controller.itemSpecifier.items;

import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsName;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gnorizon.SpringTestReportsBot.TelegramBot.FINISH_TYPE;

public class Item4 implements Item{
    @Override
    public String execute(String message, Report report) {
        int regex = report.getName().indexOf('_');
        String typeReport = report.getName().substring(regex+1);
        String[] arrayOS = message.substring(1).split(",");
        int index = 0;
        for(String item : arrayOS){
            arrayOS[index]= item.replaceAll(" ","");
            index++;
        }
        if (typeReport.equals(FINISH_TYPE)) {
            // количесво элементов не должно привышать 9
            int count = Math.min(arrayOS.length, 9);
            report.environment.setOSNames(Arrays.copyOf(arrayOS,count));
        } else {
            // количество элементов не должно превышать 5
            int count = Math.min(arrayOS.length, 5);
            Map<String, List<Integer>> osInformation = new HashMap<>();

            for (int i = 0; i < count; i++){
                String nameOS =arrayOS[i].substring(0,arrayOS[i].indexOf("-"));
                Integer countBug = Integer.parseInt(arrayOS[i].substring(arrayOS[i].indexOf("-")+1,arrayOS[i].indexOf("/")));
                Integer countClosedBug = Integer.parseInt(arrayOS[i].substring(arrayOS[i].indexOf("/")+1,arrayOS[i].lastIndexOf("-")));
                Integer countImprovement = Integer.parseInt(arrayOS[i].substring(arrayOS[i].lastIndexOf("-")+1,arrayOS[i].lastIndexOf("/")));
                Integer countClosedImprovement = Integer.parseInt(arrayOS[i].substring(arrayOS[i].lastIndexOf("/")+1));
                osInformation.put(nameOS, List.of(countBug,
                        countClosedBug,
                        countImprovement,
                        countClosedImprovement));
            }
            report.environment.setOSInformation(osInformation);
        }
        return ItemsName.ITEM_5.textForStep;
    }
}
