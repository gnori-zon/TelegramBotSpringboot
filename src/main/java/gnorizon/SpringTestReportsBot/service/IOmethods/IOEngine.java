package gnorizon.SpringTestReportsBot.service.IOmethods;

import lombok.SneakyThrows;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IOEngine {
    public static void createReport(long chatID,String typeReport){
        IOCell ioCell;
        if(typeReport.equals("Finish")) {
            ioCell = new IOCell("PatternFinalReport.xlsx");
        }else{
            ioCell = new IOCell("PatternInterReport.xlsx");
        }
        ioCell.setCell1(1, 1, 0,chatID);
    }
//для 1 случая в боте
    public static void setCell1(String typeReport, String[] data, long chatID) {
        IOCell ioCell;
        if (typeReport.equals("Finish")) {
            ioCell = new IOCell("PatternFinalReport.xlsx");
            ioCell.setCell(1, 1, data[0],chatID);
            ioCell.setCell(2, 1, data[1],chatID);
            ioCell.setCell(3,1,data[2],chatID);
        }else {
            ioCell = new IOCell("PatternInterReport.xlsx");
            ioCell.setCell(1, 1, data[0],chatID);
            ioCell.setCell(2, 1, data[1],chatID);
            ioCell.setCell(2,2,data[2],chatID);
        }
    }
    public static void setCell1F(String nameReport,String release, String readiness, long chatID) {
        IOCell ioCell= new IOCell("PatternFinalReport.xlsx");
        ioCell.setCell(1, 1, nameReport,chatID);
        ioCell.setCell(2, 1, release,chatID);
        ioCell.setCell(3,1,readiness,chatID);
    }

    public static void setCell1I(String nameReport,String release, String readiness,long chatID) {
        IOCell ioCell= new IOCell("PatternInterReport.xlsx");
        ioCell.setCell(1, 1, nameReport,chatID);
        ioCell.setCell(2, 1, release,chatID);
        ioCell.setCell(2,2,readiness,chatID);
    }
//для 2 случая в боте
    @SneakyThrows
    public static void setCell2(String typeReport, String[] date, long chatID) {
        IOCell ioCell;
        date[0] = date[0].replaceAll(" ","");
        validDate(date[0]);
        validDate(date[1]);
        if (typeReport.equals("Finish")) {
            ioCell = new IOCell("PatternFinalReport.xlsx");
            ioCell.setCell(3, 3, date[0],chatID);
            ioCell.setCell(3, 5, date[1],chatID);
        }else{
            ioCell= new IOCell("PatternInterReport.xlsx");
            String[] dayAndStand = date[2].split("-");
            ioCell.setCell(4, 1, date[0],chatID);
            ioCell.setCell(5, 1, date[1],chatID);
            ioCell.setCell(6,1,Integer.parseInt(String.valueOf(dayAndStand[0])),chatID);
            ioCell.setCell(12,1,dayAndStand[1],chatID);
        }
    }

    //для 3 случая в боте
    public static void setCell3(String typeReport, String message, long chatID) {
        message = message.substring(1);
        IOCell ioCell;
        if (typeReport.equals("Finish")) {
            ioCell = new IOCell("PatternFinalReport.xlsx");
            ioCell.setCell(5, 1, message,chatID);
        }else{
            String arrayBrowsers[] = message.split(",");
            ioCell = new IOCell("PatternInterReport.xlsx");
            // количество элементов не должно превышат 6
            int x = Math.min(arrayBrowsers.length, 6);
            for (int i = 0; i < x; i++){
                String nameBrowser =arrayBrowsers[i].substring(1,arrayBrowsers[i].indexOf("-"));
                String countBug = arrayBrowsers[i].substring(arrayBrowsers[i].indexOf("-")+1,arrayBrowsers[i].indexOf("/"));
                String countClosedBug = arrayBrowsers[i].substring(arrayBrowsers[i].indexOf("/")+1,arrayBrowsers[i].lastIndexOf("-"));
                String countImprovement = arrayBrowsers[i].substring(arrayBrowsers[i].lastIndexOf("-")+1,arrayBrowsers[i].lastIndexOf("/"));
                String countClosedImprovement = arrayBrowsers[i].substring(arrayBrowsers[i].lastIndexOf("/")+1);

                ioCell.setCell(21 + i, 0 , nameBrowser,chatID);
                ioCell.setCell(21 + i, 1,Integer.parseInt(String.valueOf( countBug)),chatID);
                ioCell.setCell(21 + i, 2,Integer.parseInt(String.valueOf( countClosedBug)),chatID);
                ioCell.setCell(21 + i, 3,Integer.parseInt(String.valueOf( countImprovement)),chatID);
                ioCell.setCell(21 + i, 4,Integer.parseInt(String.valueOf( countClosedImprovement)),chatID);
            }

        }
    }
    //для 4 случая в боте
    public static void setCell4(String typeReport, String arrayOS[],long chatID) {
        IOCell ioCell;
        arrayOS[0] = arrayOS[0].replaceAll(" ","");
        if (typeReport.equals("Finish")) {
            ioCell = new IOCell("PatternFinalReport.xlsx");
            // количесво элементов не должно привышать 9
            int x = Math.min(arrayOS.length, 9);

            for (int i = 0; i < x; i++){
                ioCell.setCell(6, 1+i , arrayOS[i],chatID);
            }
        } else {
            ioCell = new IOCell("PatternInterReport.xlsx");
            // количество элементов не должно превышать 5
            int x = Math.min(arrayOS.length, 5);

            for (int i = 0; i < x; i++){
                String nameOS =arrayOS[i].substring(0,arrayOS[i].indexOf("-"));
                String countBug = arrayOS[i].substring(arrayOS[i].indexOf("-")+1,arrayOS[i].indexOf("/"));
                String countClosedBug = arrayOS[i].substring(arrayOS[i].indexOf("/")+1,arrayOS[i].lastIndexOf("-"));
                String countImprovement = arrayOS[i].substring(arrayOS[i].lastIndexOf("-")+1,arrayOS[i].lastIndexOf("/"));
                String countClosedImprovement = arrayOS[i].substring(arrayOS[i].lastIndexOf("/")+1);

                ioCell.setCell(15 + i, 0 , nameOS,chatID);
                ioCell.setCell(15 + i, 1,Integer.parseInt(String.valueOf( countBug)),chatID);
                ioCell.setCell(15 + i, 2,Integer.parseInt(String.valueOf( countClosedBug)),chatID);
                ioCell.setCell(15 + i, 3,Integer.parseInt(String.valueOf( countImprovement)),chatID);
                ioCell.setCell(15 + i, 4,Integer.parseInt(String.valueOf( countClosedImprovement)),chatID);
            }
        }
    }

    //для 5 случая в боте
    public static void setCell5(String typeReport, String arrayFuncs[],long chatID) {
        int x;
        IOCell ioCell = validation(typeReport);
        // количество элементов не должно превышать 6
        x = Math.min(arrayFuncs.length, 6);

        for (int i = 0; i < x; i++){
            if (arrayFuncs[i].charAt(0) == ' '){
                arrayFuncs[i] = arrayFuncs[i].substring(1);
            }
            String nameFunc =arrayFuncs[i].substring(0,arrayFuncs[i].indexOf("-"));
            String countBug = arrayFuncs[i].substring(arrayFuncs[i].indexOf("-")+1);


            ioCell.setCell(9, 1+ i , nameFunc,chatID);
            ioCell.setCell(10, 1+ i,Integer.parseInt(String.valueOf( countBug)),chatID);

        }
    }
    //для 6 случая в боте
    public static void setCell6(String typeReport, String[] contBugAndImprove, long chatID){
        IOCell ioCell;
        contBugAndImprove[0] = contBugAndImprove[0].replaceAll(" ","");
        String[] countBugs = contBugAndImprove[0].split("/");
        String[] countImprovements = contBugAndImprove[1].split("/");

        if(typeReport.equals("Finish")) {
            ioCell = new IOCell("PatternFinalReport.xlsx");
            ioCell.setCell(13, 1,Integer.parseInt(String.valueOf(countBugs[0])),chatID);
            ioCell.setCell(13, 2,Integer.parseInt(String.valueOf(countBugs[1])),chatID);
            ioCell.setCell(13, 3,Integer.parseInt(String.valueOf(countBugs[0]))-Integer.parseInt(String.valueOf(countBugs[1])),chatID);
            ioCell.setCell(14, 1,Integer.parseInt(String.valueOf(countImprovements[0])),chatID);
            ioCell.setCell(14, 2,Integer.parseInt(String.valueOf(countImprovements[1])),chatID);
            ioCell.setCell(14, 3,Integer.parseInt(String.valueOf(countImprovements[0]))-Integer.parseInt(String.valueOf( countImprovements[1])),chatID);
        }else{
            ioCell = new IOCell("PatternInterReport.xlsx");
            ioCell.setCell(29, 4,Integer.parseInt(String.valueOf(countBugs[0])),chatID);
            ioCell.setCell(29, 5,Integer.parseInt(String.valueOf(countBugs[1])),chatID);
            ioCell.setCell(30, 4,Integer.parseInt(String.valueOf(countImprovements[0])),chatID);
            ioCell.setCell(30, 5,Integer.parseInt(String.valueOf(countImprovements[1])),chatID);
        }
    }

    //для 7 случая в боте
    public static void setCell7(String typeReport, String arrayBugP[],long chatID) {
        int x;
        IOCell ioCell = validation(typeReport);
        // количество жлементов не должно превышать 3
        x = Math.min(arrayBugP.length, 3);

        for (int i = 0; i < x; i++){
            if (arrayBugP[i].charAt(0)==' '){
                arrayBugP[i] = arrayBugP[i].substring(1);
            }
            String openBug =arrayBugP[i].substring(0,arrayBugP[i].indexOf("/"));
            String closeBug = arrayBugP[i].substring(arrayBugP[i].indexOf("/")+1);
            if (typeReport.equals("Finish")) {
                ioCell.setCell(17+i, 1, Integer.parseInt(String.valueOf(openBug)),chatID);
                ioCell.setCell(17+i, 2, Integer.parseInt(String.valueOf(closeBug)),chatID);
                ioCell.setCell(17+i, 3, Integer.parseInt(String.valueOf(openBug))-Integer.parseInt(String.valueOf(closeBug)),chatID);
            } else {
                ioCell.setCell(33+i, 4, Integer.parseInt(String.valueOf(openBug)),chatID);
                ioCell.setCell(33+i, 5, Integer.parseInt(String.valueOf(closeBug)),chatID);
            }
        }
    }
    public static void setCell8(String typeReport, String arrayBugS[],long chatID) {
        int x;
        IOCell ioCell = validation(typeReport);
        // количество жлементов не должно превышать 5
        x = Math.min(arrayBugS.length, 5);

        for (int i = 0; i < x; i++){
            if (arrayBugS[i].charAt(0)==' '){
                arrayBugS[i] = arrayBugS[i].substring(1);
            }
            String openBug =arrayBugS[i].substring(0,arrayBugS[i].indexOf("/"));
            String closeBug = arrayBugS[i].substring(arrayBugS[i].indexOf("/")+1);
            if (typeReport.equals("Finish")) {
                ioCell.setCell(22+i, 1, Integer.parseInt(String.valueOf(openBug)),chatID);
                ioCell.setCell(22+i, 2, Integer.parseInt(String.valueOf(closeBug)),chatID);
                ioCell.setCell(22+i, 3, Integer.parseInt(String.valueOf(openBug))-Integer.parseInt(String.valueOf(closeBug)),chatID);
            } else {
                ioCell.setCell(38+i, 4, Integer.parseInt(String.valueOf(openBug)),chatID);
                ioCell.setCell(38+i, 5, Integer.parseInt(String.valueOf(closeBug)),chatID);
            }
        }
    }

    public static void setCell9(String typeReport, String arrayModules[],long chatID){

        int x;
        int sumOpenBug=0;
        int sumCloseBug=0;
        IOCell ioCell = validation(typeReport);
        // количество элементов не должно превышать 6
        x = Math.min(arrayModules.length, 6);

        for (int i = 0; i < x; i++){
            if (arrayModules[i].charAt(0)==' '){
                arrayModules[i] = arrayModules[i].substring(1);
            }
            String nameModule =arrayModules[i].substring(0,arrayModules[i].indexOf("("));
            String openBug = arrayModules[i].substring(arrayModules[i].indexOf("(")+1,arrayModules[i].indexOf("/"));
            String closeBug = arrayModules[i].substring(arrayModules[i].indexOf("/")+1,arrayModules[i].indexOf(")"));

            sumOpenBug+=Integer.parseInt(String.valueOf(openBug));
            sumCloseBug+=Integer.parseInt(String.valueOf(closeBug));

            ioCell.setCell(29+i, 0,nameModule,chatID);
            ioCell.setCell(8, 1+i,nameModule,chatID);

            ioCell.setCell(29+i, 1, Integer.parseInt(String.valueOf(openBug)),chatID);
            ioCell.setCell(29+i, 2, Integer.parseInt(String.valueOf(closeBug)),chatID);

            if (typeReport.equals("Finish")) {
                ioCell.setCell(29+i, 3, Integer.parseInt(String.valueOf(openBug))-Integer.parseInt(String.valueOf(closeBug)),chatID);
            }
            ioCell.setCell(35, 1, sumOpenBug,chatID);
            ioCell.setCell(35, 2, sumCloseBug,chatID);

        }
    }
    public static void setCell0(String typeReport, String note,long chatID) {
        IOCell ioCell = validation(typeReport);
        if (note.charAt(0)==' '){
            note = note.substring(1);
        }
        ioCell.setCell(37, 0,note,chatID);
    }

    public static void ClearAll(long chatID){
        IOCell ioCellFinal = new IOCell("PatternFinalReport.xlsx");
        IOCell ioCellInter = new IOCell("PatternInterReport.xlsx");

        ioCellFinal.clearData(1,3,1,1,chatID);
        ioCellFinal.clearData(3,3,3,3,chatID);
        ioCellFinal.clearData(3,3,5,5,chatID);
        ioCellFinal.clearData(5,5,1,1,chatID);
        ioCellFinal.clearData(6,6,1,9,chatID);
        ioCellFinal.clearData(8,10,1,6,chatID);
        ioCellFinal.clearData(13,14,1,2,chatID);
        ioCellFinal.clearData(17,19,1,2,chatID);
        ioCellFinal.clearData(22,26,1,2,chatID);
        ioCellFinal.clearData(29,34,0,2,chatID);
        ioCellFinal.clearData(37,37,0,0,chatID);

        ioCellInter.clearData(1,1,1,1,chatID);
        ioCellInter.clearData(2,2,1,2,chatID);
        ioCellInter.clearData(4,6,1,1,chatID);
        ioCellInter.clearData(8,10,1,6,chatID);
        ioCellInter.clearData(12,12,1,1,chatID);
        ioCellInter.clearData(15,19,0,4,chatID);
        ioCellInter.clearData(21,26,0,4,chatID);
        ioCellInter.clearData(29,34,0,2,chatID);
        ioCellInter.clearData(29,30,4,5,chatID);
        ioCellInter.clearData(33,35,4,5,chatID);
        ioCellInter.clearData(38,42,4,5,chatID);
        ioCellInter.clearData(37,37,0,0,chatID);

    }

    public static void delete(String filePath,long chatID){
        File file = new File(chatID+filePath);
        file.delete();
    }
    public static void delete(String filePath){
        File file = new File(filePath);
        file.delete();
    }
    //проверка типа отчета
    private static IOCell validation (String typeReport){

        IOCell ioCell;
        if (typeReport.equals("Finish")) {
            ioCell = new IOCell("PatternFinalReport.xlsx");
        }else{
            ioCell = new IOCell("PatternInterReport.xlsx");
        }
        return ioCell;
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

        String[] arrDate = new String[3];
        arrDate = date.split("\\.");

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



