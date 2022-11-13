package gnorizon.SpringTestReportsBot.controller.itemSpecifier.items;

import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsName;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;

import java.util.Arrays;

public class Item6 implements Item{
    @Override
    public String execute(String message, Report report) {
        String[] contBugAndImprove = message.substring(1).split("-");

        contBugAndImprove[0] = contBugAndImprove[0].replaceAll(" ","");
        String[] countBugs = contBugAndImprove[0].split("/");
        String[] countImprovements = contBugAndImprove[1].split("/");
        String[] infoBugs = new String[]{countBugs[0],countBugs[1], String.valueOf(
                Integer.parseInt(countBugs[0])-Integer.parseInt(countBugs[1]))};
        String[] infoImprove = new String[]{countImprovements[0],countImprovements[1], String.valueOf(
                Integer.parseInt(countImprovements[0])-Integer.parseInt(countImprovements[1]))};
        report.aboutBugs.setTotalBugs(arrayStringToInt(infoBugs));
        report.aboutBugs.setTotalImprovements(arrayStringToInt(infoImprove));

        return ItemsName.ITEM_7.textForStep;
    }

    private int[] arrayStringToInt(String[] arrStr){
        return Arrays.stream(arrStr).mapToInt(Integer::parseInt).toArray();
    }
}
