package zm.easyExecl.Poi;

import com.aspose.cells.Workbook;
import com.utils.ExcelUtils;
import com.utils.MergeMultipleExcelUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MergeExcel {

    @Test
    public void test() {

        List<String> fileLists = Arrays.asList(new String[] {
                "D:\\yl\\demo\\mydemo\\easyexcel\\111.xlsx",
                "D:\\yl\\demo\\mydemo\\easyexcel\\create.xlsx"
        });
        String path = "D:\\yl\\demo\\mydemo\\easyexcel";
        String fileName = "合并单元格的excel";
        MergeMultipleExcelUtils.mergeExcel(fileLists, path, fileName);
    }

    @Test
    public void test1() throws Exception {
        Workbook workbook1 = new Workbook();
        String fileLists =         "D:\\yl\\demo\\mydemo\\easyexcel\\12.xlsm";
        String fileList2 =         "D:\\yl\\demo\\mydemo\\easyexcel\\13.xlsm";
        Workbook workbook = new Workbook(fileLists);
        Workbook workbook2 = new Workbook(fileList2);
        workbook1.combine(workbook);
        workbook1.combine(workbook2);
        workbook1.save("D:\\yl\\demo\\mydemo\\easyexcel\\me1.xlsx");
        int count = workbook.getWorksheets().getCount();
        workbook1.dispose();
        workbook.dispose();
        System.out.println(count);
        ExcelUtils.deleteSheet("D:\\yl\\demo\\mydemo\\easyexcel\\me.xlsx", count-1);
    }
}
