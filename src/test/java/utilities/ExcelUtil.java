package utilities;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtil {

    public static FileInputStream fi;
    public static FileOutputStream fo;
    public static XSSFWorkbook wb;
    public static XSSFSheet ws;
    public static XSSFRow row;
    public static XSSFCell cell;
    public static CellStyle style;
    String path;

    public ExcelUtil(String path) {
        this.path = path;
    }

    public int getRowCount(String xlsheet) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);
        int rowCount = ws.getLastRowNum();
        wb.close();
        fi.close();
        return rowCount;
    }

    public int getCellCount(String xlsheet, int rowNumber) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);
        row = ws.getRow(rowNumber);
        int cellCount = row.getLastCellNum();
        wb.close();
        fi.close();
        return cellCount;
    }

    public String getCellData(String xlsheet, int rowNumber, int columnNumber) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);
        row = ws.getRow(rowNumber);
        cell = row.getCell(columnNumber);

        String data;
        try {
//            data = cell.toString();
            DataFormatter formatter = new DataFormatter();
            data = formatter.formatCellValue(cell);
        }
        catch(Exception e) {
            data = "";
        }

        wb.close();
        fi.close();

        return data;
    }

    public void setCellData (String xlsheet, int rowNumber, int columnNumber, String data) throws IOException {

        File xlfile = new File(path);

        // if no such file exists, create one
        if (!xlfile.exists()) {
            wb = new XSSFWorkbook();
            fo = new FileOutputStream(path);
            wb.write(fo);
        }

        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);

        // if no such sheet exists, create one
        if (wb.getSheetIndex(xlsheet) == -1) {
            wb.createSheet(xlsheet);
        }
        ws = wb.getSheet(xlsheet);

        // if no such row exists, create one
        if (ws.getRow(rowNumber) == null) {
            ws.createRow(rowNumber);
        }
        row = ws.getRow(rowNumber);

        cell = row.createCell(columnNumber);
        cell.setCellValue(data);

        fo = new FileOutputStream(path);
        wb.write(fo);

        wb.close();
        fi.close();
        fo.close();
    }

    public void fillGreenColor(String xlsheet, int rowNumber, int columnNumber) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);
        row = ws.getRow(rowNumber);
        cell = row.getCell(columnNumber);

        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);
        fo = new FileOutputStream(path);
        wb.write(fo);

        wb.close();
        fi.close();
        fo.close();
    }

    public void fillRedColor (String xlsheet, int rowNumber, int columnNumber) throws IOException {
        fi = new FileInputStream(path);
        wb = new XSSFWorkbook(fi);
        ws = wb.getSheet(xlsheet);
        row = ws.getRow(rowNumber);
        cell = row.getCell(columnNumber);

        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);
        fo = new FileOutputStream(path);
        wb.write(fo);

        wb.close();
        fi.close();
        fo.close();
    }
}