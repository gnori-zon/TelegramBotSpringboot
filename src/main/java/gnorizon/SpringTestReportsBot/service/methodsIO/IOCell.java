package gnorizon.SpringTestReportsBot.service.methodsIO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class IOCell {
    private File filePath;

    // констркутор с созданием файла
    public IOCell(String filePath) {
        this.filePath = new File(filePath);
    }

    // метод для чтения
    public Cell getCell(int sheet, int row, int column) {
        Workbook workbook = null;
        try (FileInputStream file = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(file);
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook.getSheetAt(sheet).getRow(row).getCell(column);
    }


    // метод для записи
    public void setCell1(int row, int column, double value, long chatID) {
        Workbook workbook = null;
        String id = ""+chatID;
        try (FileInputStream file = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            sheet.getRow(row).getCell(column).setCellValue(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (OutputStream fileOut = new FileOutputStream(id+filePath,false)) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public void setCell1(int row1, int column1, String valueStr, long chatID){
            Workbook workbook = null;
            String id = ""+chatID;
            try (FileInputStream file = new FileInputStream(filePath)) {
                workbook = new XSSFWorkbook(file);
                Sheet sheet = workbook.getSheetAt(0);
                sheet.getRow(row1).getCell(column1).setCellValue(valueStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (OutputStream fileOut = new FileOutputStream(id+filePath,false)) {
                workbook.write(fileOut);
            } catch (FileNotFoundException e) {
                System.out.println("File is not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void clearData ( int startRow, int finishRow, int startColumn, int finishColumn,long chatID){
            for (int rows = startRow; rows <= finishRow; rows++) {
                for (int columns = startColumn; columns <= finishColumn; columns++) {
                    setCell1(rows, columns, 0,chatID);
                }
            }
        }
    public static void delete(String filePath, long chatID){
        File file = new File(chatID+filePath);
        file.delete();
    }


    public void setCell(int row, int column, double value, long chatID) {
        Workbook workbook = null;
        String id = ""+chatID;
        try (FileInputStream file = new FileInputStream(id+filePath)) {
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            sheet.getRow(row).getCell(column).setCellValue(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (OutputStream fileOut = new FileOutputStream(id+filePath,false)) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setCell(int row1, int column1, String valueStr, long chatID){
        Workbook workbook = null;
        String id = ""+chatID;
        try (FileInputStream file = new FileInputStream(id+filePath)) {
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            sheet.getRow(row1).getCell(column1).setCellValue(valueStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (OutputStream fileOut = new FileOutputStream(id+filePath,false)) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
