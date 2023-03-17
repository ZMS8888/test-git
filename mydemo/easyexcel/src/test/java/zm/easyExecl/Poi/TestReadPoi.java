package zm.easyExecl.Poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

public class TestReadPoi {

    @Test
    public void testRead03() throws Exception{
        // 获取输入流
        InputStream is = new FileInputStream("111.xlsx");
        // 创建一个工作簿
        Workbook workbook = new XSSFWorkbook(is);
        // 得到表   这个是使用下标拿到的
        // 还可以使用表名拿到  workbook.getSheet("表名");
        Sheet sheet = workbook.getSheetAt(0);
        // 读取第一行第一列
        Row row = sheet.getRow(8);
        Cell cell = row.getCell(6);
        // 输出单元内容
        // 这个地方获取到的是String类型的，但是excel中有很多不同的类型，需要判断
        String stringCellValue = cell.getStringCellValue();
        final int length = stringCellValue.length();
        System.out.println(cell.getStringCellValue());
        System.out.println(length);
        // 操作结束，关闭文件
        is.close();
    }
}
