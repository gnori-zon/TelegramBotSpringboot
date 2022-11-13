package gnorizon.SpringTestReportsBot.controller.itemSpecifier.items;

import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsName;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;

import java.util.HashMap;
import java.util.Map;

public class Item5 implements Item{
    @Override
    public String execute(String message, Report report) {
        String[] arrayFuncs = message.substring(1).split(",");
        // количество элементов не должно превышать 6
        int x = Math.min(arrayFuncs.length, 6);
        Map<String,Integer> funcInformation = new HashMap<>();

        for (int i = 0; i < x; i++){
            if (arrayFuncs[i].charAt(0) == ' '){
                arrayFuncs[i] = arrayFuncs[i].substring(1);
            }
            String nameFunc =arrayFuncs[i].substring(0,arrayFuncs[i].indexOf("-"));
            Integer countBug = Integer.parseInt(arrayFuncs[i].substring(arrayFuncs[i].indexOf("-")+1));
            funcInformation.put(nameFunc,countBug);
        }
        report.aboutTestObjects.setFunctionalityInformation(funcInformation);

        return ItemsName.ITEM_6.textForStep;
    }
}
