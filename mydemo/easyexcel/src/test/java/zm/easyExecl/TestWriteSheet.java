package zm.easyExecl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.entiy.User;
import com.spire.xls.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestWriteSheet {

    /**
     * 通过模板写数据
     */
    @Test
    public void testTemplate() {
        List<User> users = initData();
        String fileUrl = "user.xlsx";
        String fileName =  "write.xlsx";
        ExcelWriter excelWriter =  EasyExcel.write(fileName).withTemplate(fileUrl).build();
        //EasyExcel.write(fileUrl,User.class).withTemplate(fileUrl).excelType(ExcelTypeEnum.XLSX).build();
        org.apache.poi.ss.usermodel.Workbook workbook = excelWriter.writeContext().writeWorkbookHolder().getWorkbook();
        Sheet sheetAt = workbook.getSheetAt(0);
        WriteSheet sheet = EasyExcel.writerSheet(sheetAt.getSheetName()).relativeHeadRowIndex(5).build();
        excelWriter.write(users, sheet);
        excelWriter.finish();
    }

    @Test
    public void test() throws IOException {
        long l = System.currentTimeMillis();
        List<User> users = initData();
        String fileUrl = "create.xlsx";
        EasyExcel.write(fileUrl,User.class).sheet(0).relativeHeadRowIndex(0).doWrite(users);
        //EasyExcel.write(fileUrl).sheet(0).head(User.class).doWrite(users);
        Workbook workbook = new Workbook();
        workbook.loadFromFile(fileUrl);
       // Worksheet worksheet = workbook.getWorksheets().get(0);
      //  worksheet.getRange().get(1,3,100,100).getStyle().setWrapText(true);  //自动换行
        //worksheet.getRange().get(1,1,100,100).getStyle().setWrapText(true);
        //worksheet.getAllocatedRange().autoFitRows();
        workbook.save();
        workbook.dispose();
        long l1 = System.currentTimeMillis();
        System.out.println((l1 - l)/1000);


    }

    private static List<User>  initData() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User user = User.builder()
                    .sex("测试测试测试测试测试测试测试测试测试测试测试测女" + "\r\n")
                    .age(i)
                    .name("测试测试测试测试测试测试测试测试测试测试测试测试"  + "\r\n")
                    .build();
            users.add(user);
        }
        return users;
    }


}
