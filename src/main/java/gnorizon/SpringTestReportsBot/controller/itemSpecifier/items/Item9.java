package gnorizon.SpringTestReportsBot.controller.itemSpecifier.items;

import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsName;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item9 implements Item{
    @Override
    public String execute(String message, Report report) {
        String[] arrayModules = message.substring(1).split(",");
        // количество элементов не должно превышать 6
        int x = Math.min(arrayModules.length, 6);
        Map<String, List<Integer>> infModules = new HashMap<>();
        int sumOpened = 0;
        int sumClosed = 0;
        for (int i = 0; i < x; i++){
            if (arrayModules[i].charAt(0)==' '){
                arrayModules[i] = arrayModules[i].substring(1);
            }
            String nameModule =arrayModules[i].substring(0,arrayModules[i].indexOf("("));
            Integer openBug = Integer.parseInt(arrayModules[i].substring(arrayModules[i].indexOf("(")+1,arrayModules[i].indexOf("/")));
            Integer closeBug = Integer.parseInt(arrayModules[i].substring(arrayModules[i].indexOf("/")+1,arrayModules[i].indexOf(")")));
            int divide = openBug - closeBug;

            infModules.put(nameModule,List.of(openBug,
                    closeBug,
                    divide,
                    sumOpened+=openBug,
                    sumClosed+=closeBug));
        }
        report.aboutTestCases.setInformationAboutModules(infModules);

        return ItemsName.ITEM_10.textForStep;
    }
}
