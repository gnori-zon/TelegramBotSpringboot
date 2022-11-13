package gnorizon.SpringTestReportsBot.controller.itemSpecifier.items;

import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsName;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;

import java.util.Arrays;

public class Item8 implements Item{
    @Override
    public String execute(String message, Report report) {
        String[] arrayBugS = message.substring(1).split(",");
        // количество жлементов не должно превышать 5 8 17/16 ,16/15 ,15/14 ,14/13 ,13/12*
        String [] divide = new String[5];
        for (int i = 0; i < 5; i++){
            if (arrayBugS[i].charAt(0)==' '){
                arrayBugS[i] = arrayBugS[i].substring(1);
            }
            String openBug =arrayBugS[i].substring(0,arrayBugS[i].indexOf("/"));
            String closeBug = arrayBugS[i].substring(arrayBugS[i].indexOf("/")+1);
            divide[i] = String.valueOf(Integer.parseInt((openBug)) - Integer.parseInt((closeBug)));
        }
        report.aboutBugs.setBlockers(arrayStringToInt((arrayBugS[0] + "/"+ divide[0]).split("/")));
        report.aboutBugs.setCritical(arrayStringToInt((arrayBugS[1] + "/"+ divide[1]).split("/")));
        report.aboutBugs.setMajors(arrayStringToInt((arrayBugS[2] + "/"+ divide[2]).split("/")));
        report.aboutBugs.setMinors(arrayStringToInt((arrayBugS[3] + "/"+ divide[3]).split("/")));
        report.aboutBugs.setTrivial(arrayStringToInt((arrayBugS[4] + "/"+ divide[4]).split("/")));

        return ItemsName.ITEM_9.textForStep;
    }

    private int[] arrayStringToInt(String[] arrStr){
        return Arrays.stream(arrStr).mapToInt(Integer::parseInt).toArray();
    }
}
