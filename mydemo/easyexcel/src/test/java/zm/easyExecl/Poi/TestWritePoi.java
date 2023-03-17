package zm.easyExecl.Poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;


public class TestWritePoi {

    /**
     * 测试能够处理的最大数据
     * @throws IOException
     */
    @Test
    public void testWrite03BigData() throws IOException {
        //记录开始时间
        long begin = System.currentTimeMillis();
        //创建一个SXSSFWorkbook
        //Workbook workbook = new HSSFWorkbook(); //最多处理65536
        Workbook workbook = new SXSSFWorkbook(); //最多处理1048575
        //创建一个sheet
        Sheet sheet = workbook.createSheet();
        //xls文件最大支持65536行
        for (int rowNum = 0; rowNum < 1048576; rowNum++) {
            //创建一个行
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < 10; cellNum++) {//创建单元格
                Cell cell = row.createCell(cellNum);
                cell.setCellValue(cellNum);
            }
        }
        System.out.println("done");
        FileOutputStream out = new FileOutputStream("bigdata03.xls");
        workbook.write(out);
        // 操作结束，关闭文件
        out.close();
        // 清理临时文件
        ((SXSSFWorkbook)workbook).dispose();
        //记录结束时间
        long end = System.currentTimeMillis();
        System.out.println((double)(end - begin)/1000);
    }


    /**
     * 写EXCEL
     * @throws Exception
     */
    @Test
    public void testExcel() throws Exception {
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        //创建工作表,工作表在工作簿中，所以使用工作簿对象创建
        Sheet sheet = workbook.createSheet("blackCat");
        // 创建行   excel就是一张二维表，只需要弄清楚坐标就可以了
        // 跟二维数组差不多
        // 代表第一行
        Row row = sheet.createRow(0);
        // 创建单元格 单元格中的数字代表第几列（是下标，从0开始）
        // 行数和哪一行调用的有关
        Cell cell = row.createCell(0);
        // 写入
        cell.setCellValue("现在的时间是：");
        // 括号中的是时间的格式
        String s = "new DateTime()";
        Cell cell1 = row.createCell(1);
        cell1.setCellValue(s);

        // 创建一个文件输出流（需要先创建文件夹）
        FileOutputStream out = new FileOutputStream("黑猫demo.xlsx");
        // 把对应的excel工作簿存盘
        workbook.write(out);
        // 关闭流
        out.close();
        System.out.println("文件生成成功！");
    }
}
