package gnorizon.SpringTestReportsBot.controller.itemSpecifier.items;

import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsName;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;

import java.util.Arrays;

public class Item7 implements Item{
    @Override
    public String execute(String message, Report report) {
        String[] arrayBugP = message.substring(1).split(",");

        String[] divide = new String[3];
        for (int i = 0; i < 3; i++) {
            if (arrayBugP[i].charAt(0) == ' ') {
                arrayBugP[i] = arrayBugP[i].substring(1);
            }
            String openBug = arrayBugP[i].substring(0, arrayBugP[i].indexOf("/"));
            String closeBug = arrayBugP[i].substring(arrayBugP[i].indexOf("/") + 1);
            divide[i] = String.valueOf(Integer.parseInt((openBug)) - Integer.parseInt((closeBug)));
        }
        ;
        report.aboutBugs.setHigh(arrayStringToInt((arrayBugP[0] + "/"+ divide[0]).split("/")));
        report.aboutBugs.setMedium(arrayStringToInt((arrayBugP[1] + "/"+ divide[1]).split("/")));
        report.aboutBugs.setLow(arrayStringToInt((arrayBugP[2] + "/"+ divide[2]).split("/")));

        return ItemsName.ITEM_8.textForStep;
    }

    private int[] arrayStringToInt(String[] arrStr){
        return Arrays.stream(arrStr).mapToInt(Integer::parseInt).toArray();
    }
}
