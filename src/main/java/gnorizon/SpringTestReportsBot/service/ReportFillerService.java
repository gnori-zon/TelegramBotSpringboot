package gnorizon.SpringTestReportsBot.service;

import gnorizon.SpringTestReportsBot.model.Reports.Report;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.*;

import static gnorizon.SpringTestReportsBot.TelegramBot.FINISH_TYPE;

public class ReportFillerService {
    private final Report report;
    private final String typeReport;

    public ReportFillerService(Report report, String typeReport) {
        this.report = report;
        this.typeReport = typeReport;
    }

    public void fillItem1(String message) {
        String[] data = message.substring(1).split("-");
        report.generalInformation.setName(data[0]);
        report.generalInformation.setNumberRelease(data[1]);
        report.generalInformation.setReadiness(data[2]);
        }
    //для 2 случая в боте
    @SneakyThrows
    public void fillItem2(String message) {
        String[] date = message.substring(1).split("/");
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
    }

    //для 3 случая в боте
    public void fillItem3(String message) {
        message = message.substring(1);
        if (typeReport.equals(FINISH_TYPE)) {
            report.environment.setStandName(message);
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
    }
    //для 4 случая в боте
    public void fillItem4(String message) {

        String[] arrayOS = message.substring(1).split(",");
        arrayOS[0] = arrayOS[0].replaceAll(" ","");
        if (typeReport.equals(FINISH_TYPE)) {
            // количесво элементов не должно привышать 9
            int count = Math.min(arrayOS.length, 9);
            report.environment.setOSNames(Arrays.copyOf(arrayOS,count));
        } else {
            // количество элементов не должно превышать 5
            int count = Math.min(arrayOS.length, 5);
            Map<String,List<Integer>> osInformation = new HashMap<>();

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
    }

    //для 5 случая в боте
    public void fillItem5(String message) {
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
    }
    //для 6 случая в боте
    public void fillItem6(String message){
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
    }

    //для 7 случая в боте
    public void fillItem7(String message) {
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
    }
    public void fillItem8(String message) {
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
        }

    public void fillItem9(String message){
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
    }
    public void fillItem0(String message) {
        String note = message.substring(1);

        if (note.charAt(0)==' '){
            note = note.substring(1);
        }
        report.generalInformation.setNote(note);
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

    private int[] arrayStringToInt(String[] arrStr){
        return Arrays.stream(arrStr).mapToInt(Integer::parseInt).toArray();
    }
}
