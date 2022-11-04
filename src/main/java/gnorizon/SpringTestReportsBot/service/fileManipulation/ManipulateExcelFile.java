package gnorizon.SpringTestReportsBot.service.fileManipulation;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ManipulateExcelFile {
    private File filePath;

    // констркутор с созданием файла
    public ManipulateExcelFile(String filePath) {
        this.filePath = new File(filePath+".xlsx");
    }

    // метод для копирования
    public void copy(String origin) {
        Workbook workbook = null;
        try (FileInputStream file = new FileInputStream(origin)) {
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            sheet.getRow(1).getCell(1).setCellValue(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (OutputStream fileOut = new FileOutputStream(filePath,false)) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // метод для записи
    public void setCell(int row, int column, int value) {
            Workbook workbook = null;
            try (FileInputStream file = new FileInputStream(filePath)) {
                workbook = new XSSFWorkbook(file);
                Sheet sheet = workbook.getSheetAt(0);
                sheet.getRow(row).getCell(column).setCellValue(value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (OutputStream fileOut = new FileOutputStream(filePath, false)) {
                workbook.write(fileOut);
            } catch (FileNotFoundException e) {
                System.out.println("File is not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    public void setCell(int row1, int column1, String valueStr) {
        if (valueStr != null) {
            Workbook workbook = null;
            try (FileInputStream file = new FileInputStream(filePath)) {
                workbook = new XSSFWorkbook(file);
                Sheet sheet = workbook.getSheetAt(0);
                sheet.getRow(row1).getCell(column1).setCellValue(valueStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (OutputStream fileOut = new FileOutputStream(filePath, false)) {
                workbook.write(fileOut);
            } catch (FileNotFoundException e) {
                System.out.println("File is not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
