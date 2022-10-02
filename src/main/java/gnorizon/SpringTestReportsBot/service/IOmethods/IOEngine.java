package gnorizon.SpringTestReportsBot.service.IOmethods;

import java.io.File;

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
    public static void setCell2F(String startDate,String finishDate,long chatID) {
        IOCell ioCell= new IOCell("PatternFinalReport.xlsx");
        ioCell.setCell(3, 3, startDate,chatID);
        ioCell.setCell(3, 5, finishDate,chatID);
    }
    public static void setCell2I(String startDate,String finishDate, String countDay, String nameStand,long chatID) {
        IOCell ioCell= new IOCell("PatternInterReport.xlsx");
        ioCell.setCell(4, 1, startDate,chatID);
        ioCell.setCell(5, 1, finishDate,chatID);
        ioCell.setCell(6,1,Integer.parseInt(String.valueOf(countDay)),chatID);
        ioCell.setCell(12,1,nameStand,chatID);
    }
    //для 3 случая в боте
    public static void setCell3F(String nameStand, long chatID) {
        IOCell ioCell= new IOCell("PatternFinalReport.xlsx");
        ioCell.setCell(5, 1, nameStand,chatID);
    }
    public static void setCell3I(String arrayBrowsers[],long chatID) {

        IOCell ioCell = new IOCell("PatternInterReport.xlsx");
        int x;

        if(arrayBrowsers.length<6){
            x =arrayBrowsers.length;
        }else{
            x =6;
        }

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
    //для 4 случая в боте
    public static void setCell4F(String arrayOS[],long chatID) {
        IOCell ioCell = new IOCell("PatternFinalReport.xlsx");
        int x;

        if(arrayOS.length<9){
            x =arrayOS.length;
        }else{
            x =9;
        }

        for (int i = 0; i < x; i++){
            ioCell.setCell(6, 1+i , arrayOS[i],chatID);
        }

    }
    public static void setCell4I(String arrayOS[],long chatID) {

        IOCell ioCell = new IOCell("PatternInterReport.xlsx");
        int x;

        if(arrayOS.length<5){
            x =arrayOS.length;
        }else{
            x =5;
        }

        for (int i = 0; i < x; i++){
            String nameOS =arrayOS[i].substring(1,arrayOS[i].indexOf("-"));
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
    //для 5 случая в боте
    public static void setCell5(String typeReport, String arrayFuncs[],long chatID) {
        int x;
        IOCell ioCell = validation(typeReport);

        if(arrayFuncs.length<6){
            x =arrayFuncs.length;
        }else{
            x =6;
        }

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
    public static void setCell6(String typeReport, String countBug,String countClosedBug,String countImprovement,String countClosedImprovement,long chatID) {

        IOCell ioCell;
        if (countBug.charAt(0) == ' '){
            countBug = countBug.substring(1);
        }
        if(typeReport.equals("Finish")) {
            ioCell = new IOCell("PatternFinalReport.xlsx");

            ioCell.setCell(13, 1,Integer.parseInt(String.valueOf( countBug)),chatID);
            ioCell.setCell(13, 2,Integer.parseInt(String.valueOf( countClosedBug)),chatID);
            ioCell.setCell(13, 3,Integer.parseInt(String.valueOf( countClosedBug))-Integer.parseInt(String.valueOf( countClosedBug)),chatID);
            ioCell.setCell(14, 1,Integer.parseInt(String.valueOf( countImprovement)),chatID);
            ioCell.setCell(14, 2,Integer.parseInt(String.valueOf( countClosedImprovement)),chatID);
            ioCell.setCell(14, 3,Integer.parseInt(String.valueOf( countImprovement))-Integer.parseInt(String.valueOf( countClosedImprovement)),chatID);
        }else{
            ioCell = new IOCell("PatternInterReport.xlsx");
            ioCell.setCell(29, 4,Integer.parseInt(String.valueOf( countBug)),chatID);
            ioCell.setCell(29, 5,Integer.parseInt(String.valueOf( countClosedBug)),chatID);
            ioCell.setCell(30, 4,Integer.parseInt(String.valueOf( countImprovement)),chatID);
            ioCell.setCell(30, 5,Integer.parseInt(String.valueOf( countClosedImprovement)),chatID);
        }

    }
    //для 7 случая в боте
    public static void setCell7(String typeReport, String arrayBugP[],long chatID) {
        int x;
        IOCell ioCell = validation(typeReport);

        if(arrayBugP.length<3){
            x =arrayBugP.length;
        }else{
            x =3;
        }

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

        if(arrayBugS.length<5){
            x =arrayBugS.length;
        }else{
            x =5;
        }

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

    public static void setCell9(String typeReport, String arrayModules[],long chatID) {

        int x;
        IOCell ioCell = validation(typeReport);

        if(arrayModules.length<6){
            x =arrayModules.length;
        }else{
            x =6;
        }

        for (int i = 0; i < x; i++){
            if (arrayModules[i].charAt(0)==' '){
                arrayModules[i] = arrayModules[i].substring(1);
            }
            String nameModule =arrayModules[i].substring(0,arrayModules[i].indexOf("("));
            String openBug = arrayModules[i].substring(arrayModules[i].indexOf("(")+1,arrayModules[i].indexOf("/"));
            String closeBug = arrayModules[i].substring(arrayModules[i].indexOf("/")+1,arrayModules[i].indexOf(")"));

            ioCell.setCell(29+i, 0,nameModule,chatID);
            ioCell.setCell(8, 1+i,nameModule,chatID);

            ioCell.setCell(29+i, 1, Integer.parseInt(String.valueOf(openBug)),chatID);
            ioCell.setCell(29+i, 2, Integer.parseInt(String.valueOf(closeBug)),chatID);
            if (typeReport.equals("Finish")) {
                ioCell.setCell(29+i, 3, Integer.parseInt(String.valueOf(openBug))-Integer.parseInt(String.valueOf(closeBug)),chatID);
            }

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

}



