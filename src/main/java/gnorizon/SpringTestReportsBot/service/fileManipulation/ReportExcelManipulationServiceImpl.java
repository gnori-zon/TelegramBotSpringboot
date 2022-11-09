package gnorizon.SpringTestReportsBot.service.fileManipulation;

import gnorizon.SpringTestReportsBot.model.ManipulateExcelFile;
import gnorizon.SpringTestReportsBot.model.Reports.Report;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static gnorizon.SpringTestReportsBot.TelegramBot.FINISH_TYPE;

@Component
public class ReportExcelManipulationServiceImpl implements ReportFileManipulationService {

    @Override
    public void create(Report report){
        String filepath = report.getName();

        ManipulateExcelFile excelFile = new ManipulateExcelFile(filepath);
        String origin;
        if(filepath.contains(FINISH_TYPE)) {
            origin = "src/main/resources/templates/PatternFinalReport.xlsx";
        }else{
            origin = "src/main/resources/templates/PatternInterReport.xlsx";
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
            if(report.environment.getOSNames()!=null){
                for (int i = 0; i < report.environment.getOSNames().length; i++) {
                    excelFile.setCell(6, 1 + i, report.environment.getOSNames()[i]);
                }
            }
            // заполнение объектов тестирования
            writeMap(report.aboutTestObjects.getFunctionalityInformation(),excelFile,1);
            // заполнение багов и улучшений
            if(report.aboutBugs.getTotalBugs()!=null) {
                excelFile.setCell(13, 1, report.aboutBugs.getTotalBugs()[0]);
                excelFile.setCell(13, 2, report.aboutBugs.getTotalBugs()[1]);
                excelFile.setCell(13, 3, report.aboutBugs.getTotalBugs()[2]);
            }
            if(report.aboutBugs.getTotalImprovements()!=null) {
                excelFile.setCell(14, 1, report.aboutBugs.getTotalImprovements()[0]);
                excelFile.setCell(14, 2, report.aboutBugs.getTotalImprovements()[1]);
                excelFile.setCell(14, 3, report.aboutBugs.getTotalImprovements()[2]);
            }
            // по приоритету
            if(report.aboutBugs.getHigh()!=null) {
                excelFile.setCell(17, 1, report.aboutBugs.getHigh()[0]);
                excelFile.setCell(17, 2, report.aboutBugs.getHigh()[1]);
                excelFile.setCell(17, 3, report.aboutBugs.getHigh()[2]);
            }
            if(report.aboutBugs.getMedium()!=null) {
                excelFile.setCell(18, 1, report.aboutBugs.getMedium()[0]);
                excelFile.setCell(18, 2, report.aboutBugs.getMedium()[1]);
                excelFile.setCell(18, 3, report.aboutBugs.getMedium()[2]);
            }
            if(report.aboutBugs.getLow()!=null) {
                excelFile.setCell(19, 1, report.aboutBugs.getLow()[0]);
                excelFile.setCell(19, 2, report.aboutBugs.getLow()[1]);
                excelFile.setCell(19, 3, report.aboutBugs.getLow()[2]);
            }
            // по серьезности
            if(report.aboutBugs.getBlockers()!=null) {
                excelFile.setCell(22, 1, report.aboutBugs.getBlockers()[0]);
                excelFile.setCell(22, 2, report.aboutBugs.getBlockers()[1]);
                excelFile.setCell(22, 3, report.aboutBugs.getBlockers()[2]);
            }
            if(report.aboutBugs.getCritical()!=null) {
                excelFile.setCell(23, 1, report.aboutBugs.getCritical()[0]);
                excelFile.setCell(23, 2, report.aboutBugs.getCritical()[1]);
                excelFile.setCell(23, 3, report.aboutBugs.getCritical()[2]);
            }
            if(report.aboutBugs.getMajors()!=null) {
                excelFile.setCell(24, 1, report.aboutBugs.getMajors()[0]);
                excelFile.setCell(24, 2, report.aboutBugs.getMajors()[1]);
                excelFile.setCell(24, 3, report.aboutBugs.getMajors()[2]);
            }
            if(report.aboutBugs.getMinors()!=null) {
                excelFile.setCell(25, 1, report.aboutBugs.getMinors()[0]);
                excelFile.setCell(25, 2, report.aboutBugs.getMinors()[1]);
                excelFile.setCell(25, 3, report.aboutBugs.getMinors()[2]);
            }
            if(report.aboutBugs.getTrivial()!=null) {
                excelFile.setCell(26, 1, report.aboutBugs.getTrivial()[0]);
                excelFile.setCell(26, 2, report.aboutBugs.getTrivial()[1]);
                excelFile.setCell(26, 3, report.aboutBugs.getTrivial()[2]);
            }
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
            excelFile.setCell(4, 1, report.generalInformation.getStartTime());
            excelFile.setCell(5, 1,report.generalInformation.getEndTime());
            if(report.generalInformation.getDaysLeft()!=null) {
                excelFile.setCell(6, 1, Integer.parseInt(report.generalInformation.getDaysLeft()));
            }
            //заполнение окружения
            excelFile.setCell(12, 1,report.environment.getStandName());
            writeMap(report.environment.getBrowsersInformation(),21,excelFile);
            writeMap(report.environment.getOSInformation(),15,excelFile);
            // заполнение объектов тестирования
            writeMap(report.aboutTestObjects.getFunctionalityInformation(),excelFile,1);
            // заполнение багов и улучшений
            if(report.aboutBugs.getTotalBugs()!=null) {
                excelFile.setCell(29, 4, report.aboutBugs.getTotalBugs()[0]);
                excelFile.setCell(29, 5, report.aboutBugs.getTotalBugs()[1]);
            }
            if(report.aboutBugs.getTotalImprovements()!=null) {
                excelFile.setCell(30, 4, report.aboutBugs.getTotalImprovements()[0]);
                excelFile.setCell(30, 5, report.aboutBugs.getTotalImprovements()[1]);
            }
            // по приоритету
            if(report.aboutBugs.getHigh()!=null) {
                excelFile.setCell(33, 4, report.aboutBugs.getHigh()[0]);
                excelFile.setCell(33, 5, report.aboutBugs.getHigh()[1]);
            }
            if(report.aboutBugs.getMedium()!=null) {
                excelFile.setCell(34, 4, report.aboutBugs.getMedium()[0]);
                excelFile.setCell(34, 5, report.aboutBugs.getMedium()[1]);
            }
            if(report.aboutBugs.getLow()!=null) {
                excelFile.setCell(35, 4, report.aboutBugs.getLow()[0]);
                excelFile.setCell(35, 5, report.aboutBugs.getLow()[1]);
            }
            // по серьезности
            if(report.aboutBugs.getBlockers()!=null) {
                excelFile.setCell(38, 4, report.aboutBugs.getBlockers()[0]);
                excelFile.setCell(38, 5, report.aboutBugs.getBlockers()[1]);
            }
            if(report.aboutBugs.getCritical()!=null) {
                excelFile.setCell(39, 4, report.aboutBugs.getCritical()[0]);
                excelFile.setCell(39, 5, report.aboutBugs.getCritical()[1]);
            }
            if(report.aboutBugs.getMajors()!=null) {
                excelFile.setCell(40, 4, report.aboutBugs.getMajors()[0]);
                excelFile.setCell(40, 5, report.aboutBugs.getMajors()[1]);
            }
            if(report.aboutBugs.getMinors()!=null) {
                excelFile.setCell(41, 4, report.aboutBugs.getMinors()[0]);
                excelFile.setCell(41, 5, report.aboutBugs.getMinors()[1]);
            }
            if(report.aboutBugs.getTrivial()!=null) {
                excelFile.setCell(42, 4, report.aboutBugs.getTrivial()[0]);
                excelFile.setCell(42, 5, report.aboutBugs.getTrivial()[1]);
            }
            // заполнение модулей
            writeMap(report.aboutTestCases.getInformationAboutModules(),excelFile,filepath);
            // заполнение примечание
            excelFile.setCell(37,0,report.generalInformation.getNote());
        }
    }

    @Override
    public void delete(Report report) {
        File file = new File(report.getName()+".xlsx");
        file.delete();
    }

    private void writeMap(Map<String, List<Integer>> map,int firstRow, ManipulateExcelFile excelFile) {
        if(map!=null) {
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
    }
    private void writeMap(Map<String,Integer> map, ManipulateExcelFile excelFile,int firstColumn){
        if(map!=null) {
            Set<String> names = map.keySet();
            int i = 0;
            for (String name : names) {
                Integer value = map.get(name);

                excelFile.setCell(9, firstColumn + i, name);
                excelFile.setCell(10, firstColumn + i, value);
                i++;
            }
        }
    }
    private void writeMap(Map<String, List<Integer>> map,ManipulateExcelFile excelFile,String filepath) {
        if (map!=null) {
            Set<String> names = map.keySet();
            int i = 0;
            int countAll = 0;
            int countClosed = 0;
            for (String name : names) {
                List<Integer> value = map.get(name);
                excelFile.setCell(29 + i, 0, name);
                excelFile.setCell(8, 1 + i, name);

                excelFile.setCell(29 + i, 1, value.get(0));
                excelFile.setCell(29 + i, 2, value.get(1));

                if (filepath.contains(FINISH_TYPE)) {
                    excelFile.setCell(29 + i, 3, value.get(2));
                }
                if(countAll < value.get(3)){
                    countAll = value.get(3);
                    countClosed =  value.get(4);
                }
                i++;
            }
            excelFile.setCell(35, 1,countAll);
            excelFile.setCell(35, 2,countClosed);
        }
    }

}

