package gnorizon.SpringTestReportsBot.controller.itemSpecifier.items;

import gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsName;
import gnorizon.SpringTestReportsBot.repository.Entity.Report;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static gnorizon.SpringTestReportsBot.TelegramBot.FINISH_TYPE;

public class Item2 implements Item{

    @Override
    @SneakyThrows
    public String execute(String message, Report report) {
        String[] date = message.substring(1).split("/");
        int regex = report.getName().indexOf('_');
        String typeReport = report.getName().substring(regex+1);

        date[0] = date[0].replaceAll(" ","");
        validDate(date[0]);
        validDate(date[1]);
        report.generalInformation.setStartTime(date[0]);
        report.generalInformation.setEndTime(date[1]);
        if (!typeReport.equals(FINISH_TYPE)) {
            String[] dayAndStand = date[2].split("-");
            report.generalInformation.setDaysLeft(dayAndStand[0]);
            report.environment.setStandName(dayAndStand[1]);
        }

        if (typeReport.equals(FINISH_TYPE)) {
            return ItemsName.ITEM_3.textForStep;
        } else {
            return ItemsName.ITEM_3.textForNotFinalRep;
        }
    }
    private static Object validDate (String date) throws Exception {

        Map<Integer,String> validDate = new HashMap<>();
        validDate.put(1,"Jan");
        validDate.put(2,"Feb");
        validDate.put(3,"Mar");
        validDate.put(4,"Apr");
        validDate.put(5,"May");
        validDate.put(6,"Jun");
        validDate.put(7,"Jul");
        validDate.put(8,"Aug");
        validDate.put(9,"Sep");
        validDate.put(10,"Oct");
        validDate.put(11,"Nov");
        validDate.put(12,"Dec");

        String[] arrDate;
        arrDate = date.split("\\.",3);

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");

        if(Integer.parseInt(arrDate[1])>31 || Integer.parseInt(arrDate[1])< 1 ||
                Integer.parseInt(arrDate[2])<2000 || Integer.parseInt(arrDate[2])>2100){
            throw new Exception();
        }
        Date docDate;
        try {
            docDate = format.parse(date);
            if (docDate.toString().contains(validDate.get(Integer.parseInt(arrDate[1])))) {
                return docDate;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
