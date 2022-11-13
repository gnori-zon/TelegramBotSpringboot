package gnorizon.SpringTestReportsBot.model;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * Excel file template model and methods for working with it
 */
public class ExcelFile {
    private File filePath;

    // constructor with file creation | констркутор с созданием файла
    public ExcelFile(String filePath) {
        this.filePath = new File(filePath+".xlsx");
    }

    // method to copy style and data from original | метод для копирования стиля и данных из оригинала
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

    // method for write integer | метод для записи числа
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
    // method for write string | метод для записи строки
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
