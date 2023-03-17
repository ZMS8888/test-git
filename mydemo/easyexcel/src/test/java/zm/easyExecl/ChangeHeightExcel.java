package zm.easyExecl;

import com.utils.ExcelManageUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;

public class ChangeHeightExcel {

    @Test
    public void test() throws IOException {
       // String excelUrl = "changeHigh.xlsx";
        String excelUrl = "change.xlsx";
        ExcelManageUtils.changeExcelHeight(excelUrl);
    }

    @Test
    public void test1() throws IOException {
        String excelUrl = "123.xlsx";
        ExcelManageUtils. deleteExcelFormula(excelUrl);
    }
}
