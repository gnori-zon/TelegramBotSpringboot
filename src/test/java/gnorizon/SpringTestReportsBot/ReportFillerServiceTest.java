package gnorizon.SpringTestReportsBot;

import gnorizon.SpringTestReportsBot.repository.Entity.Report;
import gnorizon.SpringTestReportsBot.service.ReportFillerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static gnorizon.SpringTestReportsBot.TelegramBot.FINISH_TYPE;

public class ReportFillerServiceTest {

        static String comment = "something bla bla bla, aga da" ;
    static String item0 = "0 "+comment;
        static String name = "NameReport";
        static String release = "release12";
        static String readiness ="ready";
    static String item1 = "1 "+name+"-"+release+"-"+readiness;
        static String startTime="15.12.2019";
        static String endTime="28.02.2022";
    static String item2 = "2 "+startTime+"/"+endTime;
        static String nameStand ="nameStand 1" ;
    static String item3 = "3 "+nameStand;
        static String namesOS = "Windows, Linux, Mac, Safari";
    static String item4 = "4 "+namesOS;
        static String[] nameFunc = "функция1-, функция2-, функция3-, функция4-".split(", ");
        static String[] countBugsFunc = "1, 2, 3, 4".split(", ");
    static String item5 = "5 "+ item5Func(nameFunc,countBugsFunc);
        static String countBug = "15/12";
        static String countImprove = "16/13";
    static String item6 = "6 "+countBug+"-"+countImprove+"";
        static String highBugs = "18/12";
        static String mediumBugs = "16/13";
        static String lowBugs = "15/2" ;
    static String item7 = "7 "+highBugs+","+ mediumBugs +","+lowBugs;
        static String blockerBugs = "17/16";
        static String criticalBugs = "16/15";
        static String majorBugs = "15/14";
        static String minorBugs = "14/13";
        static String trivialBugs = "13/12";
    static String item8 = "8 "+blockerBugs+","+criticalBugs+","+majorBugs+","+minorBugs+","+trivialBugs;
        static String module1Name = "Модуль1";
        static String module1Bugs = "(11/6)";
        static String module2Name = "Модуль2";
        static String module2Bugs = "(15/1)";
        static String module3Name = "Модуль3";
        static String module3Bugs = "(18/7)";
        static String module4Name = "Модуль4";
        static String module4Bugs = "(24/7)";
    static String item9 = "9 "+module1Name+module1Bugs+", "+module2Name+module2Bugs+", "+module3Name+module3Bugs+", "+module4Name+module4Bugs;

    @Test
    public void reportFillerPositiveTest(){
        Report report = new Report("123432_"+FINISH_TYPE);
        ReportFillerService RFService = new ReportFillerService(report,FINISH_TYPE);
        RFService.fillItem1(item1);
        RFService.fillItem2(item2);
        RFService.fillItem3(item3);
        RFService.fillItem4(item4);
        RFService.fillItem5(item5);
        RFService.fillItem6(item6);
        RFService.fillItem7(item7);
        RFService.fillItem8(item8);
        RFService.fillItem9(item9);
        RFService.fillItem0(item0);
        // Item 1
        Assertions.assertEquals(name,report.generalInformation.getName());
        Assertions.assertEquals(release,report.generalInformation.getNumberRelease());
        Assertions.assertEquals(readiness,report.generalInformation.getReadiness());
        // Item 2
        Assertions.assertEquals(startTime,report.generalInformation.getStartTime());
        Assertions.assertEquals(endTime,report.generalInformation.getEndTime());
        // Item 3
        Assertions.assertEquals(nameStand,report.environment.getStandName());
        // Item 4
        Assertions.assertEquals(Arrays.toString(namesOS.split(", ")),Arrays.toString(report.environment.getOSNames()));
        // Item 5
        Assertions.assertEquals(getMapFuncInf(nameFunc,countBugsFunc),report.aboutTestObjects.getFunctionalityInformation());
        // Item 6
        Assertions.assertEquals(Integer.parseInt(countBug.split("/")[0]),report.aboutBugs.getTotalBugs()[0]);
        Assertions.assertEquals(Integer.parseInt(countBug.split("/")[1]),report.aboutBugs.getTotalBugs()[1]);
        Assertions.assertEquals(Integer.parseInt(countImprove.split("/")[0]),report.aboutBugs.getTotalImprovements()[0]);
        Assertions.assertEquals(Integer.parseInt(countImprove.split("/")[1]),report.aboutBugs.getTotalImprovements()[1]);
        // Item 7
        Assertions.assertEquals(Integer.parseInt(highBugs.split("/")[0]),report.aboutBugs.getHigh()[0]);
        Assertions.assertEquals(Integer.parseInt(highBugs.split("/")[1]),report.aboutBugs.getHigh()[1]);
        Assertions.assertEquals(Integer.parseInt(mediumBugs.split("/")[0]),report.aboutBugs.getMedium()[0]);
        Assertions.assertEquals(Integer.parseInt(mediumBugs.split("/")[1]),report.aboutBugs.getMedium()[1]);
        Assertions.assertEquals(Integer.parseInt(lowBugs.split("/")[0]),report.aboutBugs.getLow()[0]);
        Assertions.assertEquals(Integer.parseInt(lowBugs.split("/")[1]),report.aboutBugs.getLow()[1]);
        // Item 8
        Assertions.assertEquals(Integer.parseInt(blockerBugs.split("/")[0]),report.aboutBugs.getBlockers()[0]);
        Assertions.assertEquals(Integer.parseInt(blockerBugs.split("/")[1]),report.aboutBugs.getBlockers()[1]);
        Assertions.assertEquals(Integer.parseInt(criticalBugs.split("/")[0]),report.aboutBugs.getCritical()[0]);
        Assertions.assertEquals(Integer.parseInt(criticalBugs.split("/")[1]),report.aboutBugs.getCritical()[1]);
        Assertions.assertEquals(Integer.parseInt(majorBugs.split("/")[0]),report.aboutBugs.getMajors()[0]);
        Assertions.assertEquals(Integer.parseInt(majorBugs.split("/")[1]),report.aboutBugs.getMajors()[1]);
        Assertions.assertEquals(Integer.parseInt(minorBugs.split("/")[0]),report.aboutBugs.getMinors()[0]);
        Assertions.assertEquals(Integer.parseInt(minorBugs.split("/")[1]),report.aboutBugs.getMinors()[1]);
        Assertions.assertEquals(Integer.parseInt(trivialBugs.split("/")[0]),report.aboutBugs.getTrivial()[0]);
        Assertions.assertEquals(Integer.parseInt(trivialBugs.split("/")[1]),report.aboutBugs.getTrivial()[1]);
        // Item 9
        Assertions.assertEquals(Integer.parseInt(module1Bugs.substring(1,module1Bugs.length()-1).split("/")[0])
                ,report.aboutTestCases.getInformationAboutModules().get(module1Name).get(0));
        Assertions.assertEquals(Integer.parseInt(module1Bugs.substring(1,module1Bugs.length()-1).split("/")[1])
                ,report.aboutTestCases.getInformationAboutModules().get(module1Name).get(1));
        Assertions.assertEquals(Integer.parseInt(module2Bugs.substring(1,module2Bugs.length()-1).split("/")[0])
                ,report.aboutTestCases.getInformationAboutModules().get(module2Name).get(0));
        Assertions.assertEquals(Integer.parseInt(module2Bugs.substring(1,module2Bugs.length()-1).split("/")[1])
                ,report.aboutTestCases.getInformationAboutModules().get(module2Name).get(1));
        Assertions.assertEquals(Integer.parseInt(module3Bugs.substring(1,module3Bugs.length()-1).split("/")[0])
                ,report.aboutTestCases.getInformationAboutModules().get(module3Name).get(0));
        Assertions.assertEquals(Integer.parseInt(module3Bugs.substring(1,module3Bugs.length()-1).split("/")[1])
                ,report.aboutTestCases.getInformationAboutModules().get(module3Name).get(1));
        Assertions.assertEquals(Integer.parseInt(module4Bugs.substring(1,module4Bugs.length()-1).split("/")[0])
                ,report.aboutTestCases.getInformationAboutModules().get(module4Name).get(0));
        Assertions.assertEquals(Integer.parseInt(module4Bugs.substring(1,module4Bugs.length()-1).split("/")[1])
                ,report.aboutTestCases.getInformationAboutModules().get(module4Name).get(1));

        // Item 0
        Assertions.assertEquals(comment,report.generalInformation.getNote());
    }
    private static String item5Func(String[] arr1,String[] arr2){
        String result = "";
        for(int i =0; i< Math.min(arr1.length,arr2.length);i++){
            result += arr1[i]+arr2[i] + ',';
        }
        return result.substring(0,result.length()-1);
    }
    private static Map<String,Integer> getMapFuncInf(String[] arr1, String[] arr2){
        Map<String,Integer> map = new HashMap<>();
        for(int i =0; i< Math.min(arr1.length,arr2.length);i++){
            map.put(arr1[i].substring(0,arr1[i].length()-1),Integer.parseInt(arr2[i]));
        }
        return map;
    }
}
