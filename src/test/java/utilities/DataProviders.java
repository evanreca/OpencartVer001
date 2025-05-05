package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    // Data Provider 1

    @DataProvider(name="LoginData")
    public Object[][] getData() throws IOException {

        String path = ".\\testData\\Opencart_LoginData.xlsx"; // taking the xl file from testData
        ExcelUtil excelUtil = new ExcelUtil(path); // creating excel utility object

        int totalRows = excelUtil.getRowCount("Sheet1");
        int totalColumns = excelUtil.getCellCount("Sheet1", 1);

        String loginData[][] = new String[totalRows][totalColumns]; // creates a 2D array to store the excel data in

        //reads the data from the 2D array
        for (int i = 1; i <= totalRows; i++) { // i = row data
            for (int j = 0; j < totalColumns; j++) { // j = column/cell data
                loginData[i - 1][j] = excelUtil.getCellData("Sheet1", i, j); // starting at row 1 column 0
            }
        }

        return loginData; // returns the 2D array
    }

    // Data Provider 2

    // Data Provider 3

    // Data Provider 4

}
