package zm.easyExecl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.entiy.User;
import com.utils.ExcelUtils;
import com.utils.ReadExcelDataListener;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class TestSheetRead {

    @Test
    public void testReadAllSheet1() {
        String fileUrl = "user.xlsx";
        ReadExcelDataListener listener = new ReadExcelDataListener();
        //一个文件一个reader
        EasyExcel.read(fileUrl, User.class, listener).doReadAll();
        List<User> dataList = listener.getDataList();
        for (User user : dataList) {
            System.out.println(user);
        }
    }

    @Test
    public void testReadAllSheet() {
        String fileUrl = "user.xlsx";
        ReadExcelDataListener listener = new ReadExcelDataListener();
        //一个文件一个reader
        EasyExcel.read(fileUrl, User.class, listener).doReadAll();
        List<User> dataList = listener.getDataList();
        for (User user : dataList) {
            System.out.println(user);
        }
    }

    @Test
    public void testRead1() {
        String fileUrl = "user.xlsx";
        ReadExcelDataListener listener = new ReadExcelDataListener();
        //一个文件一个reader
        EasyExcel.read(fileUrl, User.class, listener).sheet().doRead();
        List<User> dataList = listener.getDataList();
        for (User user : dataList) {
            System.out.println(user);
        }
    }

    @Test
    public void testRead() {
        String fileUrl = "user.xlsx";
        ReadExcelDataListener listener = new ReadExcelDataListener();
        //一个文件一个reader
        ExcelReader build = EasyExcel.read(fileUrl, User.class, listener).build();
        // 构建一个sheet 这里可以指定名字或者no
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        // 读取一个sheet
        build.read(readSheet);
        List<User> dataList = listener.getDataList();
        for (User user : dataList) {
            System.out.println(user);
        }
    }
    @Test
    public void testReadSheet() {
        String fileUrl = "user.xlsx";
        ExcelReader excelReader = EasyExcel.read(fileUrl).build();
        ReadExcelDataListener listener = new ReadExcelDataListener();
        ReadSheet readSheet = EasyExcel.readSheet(0).head(User.class).registerReadListener(listener).build();
        excelReader.read(readSheet);
        List<User> dataList = listener.getDataList();
        excelReader.finish();
        for (User user : dataList) {
            System.out.println(user);
        }
    }


    @Test
    public void test1() {
        String fileUrl = "card.xlsx";
        File file = new File(fileUrl);
        try {
            Workbook xssfWorkbook = WorkbookFactory.create(file);
            int numberOfSheets = xssfWorkbook.getNumberOfSheets();
            List<Sheet> allSheet = ExcelUtils.getAllSheet(xssfWorkbook);
            for (Sheet sheetAt : allSheet) {
                int lastRowNum = sheetAt.getLastRowNum();
                for (int i = 0; i <= lastRowNum; i++) {
                    Row row = sheetAt.getRow(i);  //获得对应的行
                    short lastCellNum = row.getLastCellNum();
                    for (int j = 0; j < lastCellNum; j++) {
                        Cell cell = row.getCell(j); //获得对应行里面具体的列
                        String stringCellValue = cell.getStringCellValue();//获得具体的数据
                        CellType cellType = cell.getCellType();
                        System.out.println("第" + i + "行第" + j + "列读取的数据是" + stringCellValue);
                    }
                }
                String sheetName = sheetAt.getSheetName();
                Header header = sheetAt.getHeader();
                String left = header.getLeft();
                System.out.println("eeee");
            }
        } catch (Exception e) {
            System.out.println("EXCEL读取错误");
        }finally {
        }
    }

    @Test
    public void test() {
        String fileUrl = "card.xlsx";
        File file = new File(fileUrl);
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
            List<Sheet> allSheet = ExcelUtils.getAllSheet(xssfWorkbook);
            for (Sheet sheet : allSheet) {
                XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
                int lastRowNum = sheetAt.getLastRowNum();
                for (int i = 0; i <= lastRowNum; i++) {
                        XSSFRow row = sheetAt.getRow(i);
                        short lastCellNum = row.getLastCellNum();
                    for (int j = 0; j < lastCellNum; j++) {
                        XSSFCell cell = row.getCell(j);
                        String stringCellValue = cell.getStringCellValue();
                        CellType cellType = cell.getCellType();
                        System.out.println("第" + i + "行第" + j + "列读取的数据是" + stringCellValue);
                    }
                }
                String sheetName = sheetAt.getSheetName();
                Header header = sheetAt.getHeader();
                String left = header.getLeft();
            System.out.println("eeee");
            }
        } catch (Exception e) {
            System.out.println("EXCEL读取错误");
        }finally {
        }
    }
}
