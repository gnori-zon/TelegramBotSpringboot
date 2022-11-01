package gnorizon.SpringTestReportsBot.service.fileManipulation;

import gnorizon.SpringTestReportsBot.model.Reports.Report;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static gnorizon.SpringTestReportsBot.service.TelegramBot.FINISH_TYPE;

@Component
public class ReportExcelManipulation implements ReportFileManipulation {

    @Override
    public void create(Report report){
        String filepath = report.getName();

        ManipulateExcelFile excelFile = new ManipulateExcelFile(filepath);
        String origin;
        if(filepath.contains(FINISH_TYPE)) {
            origin = "PatternFinalReport.xlsx";
        }else{
            origin = "PatternInterReport.xlsx";
        }
        excelFile.copy(origin);
    }

    @Override
    public void write(Report report) {
        String filepath = report.getName();
        ManipulateExcelFile excelFile = new ManipulateExcelFile(filepath);
        if(filepath.contains(FINISH_TYPE)) {
            // заполнение имени, релиза и готовность
            excelFile.setCell(1, 1,report.generalInformation.getName());
            excelFile.setCell(2, 1,report.generalInformation.getNumberRelease());
            excelFile.setCell(3, 1,report.generalInformation.getReadiness());
            // заполнение сроков
            excelFile.setCell(3, 3,report.generalInformation.getStartTime());
            excelFile.setCell(3, 5,report.generalInformation.getEndTime());
            //заполнение окружения
            excelFile.setCell(5, 1,report.environment.getStandName());
            for (int i = 0; i < report.environment.getOSNames().length; i++) {
                excelFile.setCell(6, 1 + i, report.environment.getOSNames()[i]);
            }
            // заполнение объектов тестирования
            writeMap(report.aboutTestObjects.getFunctionalityInformation(),excelFile,1);
            // заполнение багов и улучшений
            excelFile.setCell(13,1,report.aboutBugs.getTotalBugs()[0]);
            excelFile.setCell(13,2,report.aboutBugs.getTotalBugs()[1]);
            excelFile.setCell(13,3,report.aboutBugs.getTotalBugs()[2]);
            excelFile.setCell(14,1,report.aboutBugs.getTotalImprovements()[0]);
            excelFile.setCell(14,2,report.aboutBugs.getTotalImprovements()[1]);
            excelFile.setCell(14,3,report.aboutBugs.getTotalImprovements()[2]);
            // по приоритету
            excelFile.setCell(17,1,report.aboutBugs.getHigh()[0]);
            excelFile.setCell(17,2,report.aboutBugs.getHigh()[1]);
            excelFile.setCell(17,3,report.aboutBugs.getHigh()[2]);
            excelFile.setCell(18,1,report.aboutBugs.getMedium()[0]);
            excelFile.setCell(18,2,report.aboutBugs.getMedium()[1]);
            excelFile.setCell(18,3,report.aboutBugs.getMedium()[2]);
            excelFile.setCell(19,1,report.aboutBugs.getLow()[0]);
            excelFile.setCell(19,2,report.aboutBugs.getLow()[1]);
            excelFile.setCell(19,3,report.aboutBugs.getLow()[2]);
            // по серьезности
            excelFile.setCell(22,1,report.aboutBugs.getBlockers()[0]);
            excelFile.setCell(22,2,report.aboutBugs.getBlockers()[1]);
            excelFile.setCell(22,3,report.aboutBugs.getBlockers()[2]);
            excelFile.setCell(23,1,report.aboutBugs.getCritical()[0]);
            excelFile.setCell(23,2,report.aboutBugs.getCritical()[1]);
            excelFile.setCell(23,3,report.aboutBugs.getCritical()[2]);
            excelFile.setCell(24,1,report.aboutBugs.getMajors()[0]);
            excelFile.setCell(24,2,report.aboutBugs.getMajors()[1]);
            excelFile.setCell(24,3,report.aboutBugs.getMajors()[2]);
            excelFile.setCell(25,1,report.aboutBugs.getMinors()[0]);
            excelFile.setCell(25,2,report.aboutBugs.getMinors()[1]);
            excelFile.setCell(25,3,report.aboutBugs.getMinors()[2]);
            excelFile.setCell(26,1,report.aboutBugs.getTrivial()[0]);
            excelFile.setCell(26,2,report.aboutBugs.getTrivial()[1]);
            excelFile.setCell(26,3,report.aboutBugs.getTrivial()[2]);
            // заполнение модулей
            writeMap(report.aboutTestCases.getInformationAboutModules(),excelFile,filepath);
            // заполнение примечание
            excelFile.setCell(37,0,report.generalInformation.getNote());


        }else{
            // заполнение имени, релиза и готовность
            excelFile.setCell(1, 1,report.generalInformation.getName());
            excelFile.setCell(2, 1,report.generalInformation.getNumberRelease());
            excelFile.setCell(2, 2,report.generalInformation.getReadiness());
            // заполнение сроков
            excelFile.setCell(4, 1,report.generalInformation.getStartTime());
            excelFile.setCell(5, 1,report.generalInformation.getEndTime());
            excelFile.setCell(6, 1,report.generalInformation.getDaysLeft());
            //заполнение окружения
            excelFile.setCell(12, 1,report.environment.getStandName());
            writeMap(report.environment.getBrowsersInformation(),21,excelFile);
            writeMap(report.environment.getOSInformation(),15,excelFile);
            // заполнение объектов тестирования
            writeMap(report.aboutTestObjects.getFunctionalityInformation(),excelFile,1);
            // заполнение багов и улучшений
            excelFile.setCell(29,4,report.aboutBugs.getTotalBugs()[0]);
            excelFile.setCell(29,5,report.aboutBugs.getTotalBugs()[1]);
            excelFile.setCell(30,4,report.aboutBugs.getTotalImprovements()[0]);
            excelFile.setCell(30,5,report.aboutBugs.getTotalImprovements()[1]);
            // по приоритету
            excelFile.setCell(33,4,report.aboutBugs.getHigh()[0]);
            excelFile.setCell(33,5,report.aboutBugs.getHigh()[1]);
            excelFile.setCell(34,4,report.aboutBugs.getMedium()[0]);
            excelFile.setCell(34,5,report.aboutBugs.getMedium()[1]);
            excelFile.setCell(35,4,report.aboutBugs.getLow()[0]);
            excelFile.setCell(35,5,report.aboutBugs.getLow()[1]);
            // по серьезности
            excelFile.setCell(38,4,report.aboutBugs.getBlockers()[0]);
            excelFile.setCell(38,5,report.aboutBugs.getBlockers()[1]);
            excelFile.setCell(39,4,report.aboutBugs.getCritical()[0]);
            excelFile.setCell(39,5,report.aboutBugs.getCritical()[1]);
            excelFile.setCell(40,4,report.aboutBugs.getMajors()[0]);
            excelFile.setCell(40,5,report.aboutBugs.getMajors()[1]);
            excelFile.setCell(41,4,report.aboutBugs.getMinors()[0]);
            excelFile.setCell(41,5,report.aboutBugs.getMinors()[1]);
            excelFile.setCell(42,4,report.aboutBugs.getTrivial()[0]);
            excelFile.setCell(42,5,report.aboutBugs.getTrivial()[1]);
            // заполнение модулей
            writeMap(report.aboutTestCases.getInformationAboutModules(),excelFile,filepath);
            // заполнение примечание
            excelFile.setCell(37,0,report.generalInformation.getNote());
        }
    }

    @Override
    public void delete(Report report) {
        File file = new File(report.getName());
        file.delete();
    }

    private void writeMap(Map<String, List<Integer>> map,int firstRow, ManipulateExcelFile excelFile) {
        Set<String> names = map.keySet();
        int i = 0;
        for (String name : names) {
            List<Integer> value = map.get(name);

            excelFile.setCell(firstRow + i, 0, name);
            excelFile.setCell(firstRow + i, 1, value.get(0));
            excelFile.setCell(firstRow + i, 2, value.get(1));
            excelFile.setCell(firstRow + i, 3, value.get(2));
            excelFile.setCell(firstRow + i, 4, value.get(3));
            i++;
        }
    }
    private void writeMap(Map<String,Integer> map, ManipulateExcelFile excelFile,int firstColumn){
        Set<String> names = map.keySet();
        int i = 0;
        for (String name : names) {
            Integer value = map.get(name);

            excelFile.setCell(9, firstColumn + i, name);
            excelFile.setCell(10, firstColumn + i, value);
            i++;
        }
    }
    private void writeMap(Map<String, List<Integer>> map,ManipulateExcelFile excelFile,String filepath) {
        Set<String> names = map.keySet();
        int i = 0;
        for (String name : names) {
            List<Integer> value = map.get(name);
            excelFile.setCell(29 + i, 0, name);
            excelFile.setCell(8, 1+i, name);

            excelFile.setCell(29 + i, 1, value.get(0));
            excelFile.setCell(29 + i, 2, value.get(1));

            if(filepath.contains(FINISH_TYPE)) {
                excelFile.setCell( 29 + i, 3, value.get(2));
            }
            if(i==0) {
                excelFile.setCell(35, 1, value.get(3));
                excelFile.setCell(35, 2, value.get(4));
            }
            i++;
        }
    }

}

