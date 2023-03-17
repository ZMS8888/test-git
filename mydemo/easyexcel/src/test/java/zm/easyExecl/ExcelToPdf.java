package zm.easyExecl;


import com.aspose.cells.*;

import com.aspose.pdf.Document;
import com.itextpdf.text.pdf.PdfReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.junit.Test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.utils.ExcelToPdfUtils.replaceStr;

@Slf4j
public class ExcelToPdf {


    @Test
    public void testPDF() throws Exception {
        Document document = new Document("aa.pdf");
        List evenPage = new ArrayList();
        List oldPage = new ArrayList();
        //PageCollection pages = document.getPages();
        //PdfReader reader = new PdfReader("aa.pdf");
        //总页数
       document.save("11o.docx", SaveFormat.ODS);
        //int n = reader.getNumberOfPages();
       // reader.getPage
      /*  for (Page page : document.getPages()) {
            if (page.getNumber() <20) {
                evenPage.add(page);
            } else {
                oldPage.add(page);
            }
        }
        Document newDocument = new Document();
        newDocument.getPages().add(evenPage);
        newDocument.save("on1e.pdf");

        newDocument = new Document();
        newDocument.getPages().add(oldPage);
        newDocument.save("tu1o.pdf");
        document.close();
        reader.close();*/
    }

    @Test
    public void test() {
/*        if (!getLicense()) {
            // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        String file = "111.xlsx";
        Workbook workbook = new Workbook();
        workbook.loadFromFile(file);
        workbook.getConverterSetting().setSheetFitToPage(true);
        workbook.saveToFile("pdf.pdf", FileFormat.PDF);
        replaceStr("pdf.pdf", "toPDF.pdf", "Evaluation Warning : The document was created with Spire.XLS for Java");*/
     }

    public void testPdf() throws IOException {
        PdfReader pdfReader = new PdfReader("ce.pdf");
    }

    @Test
    public void test1() throws Exception {
        FileOutputStream fileOutputStream = null;
        String targetAddress= "ce.pdf";
        try {
            // 输出路径
            File pdfFile = new File(targetAddress);
            // 原始excel路径
            Workbook wb = new Workbook("ExcelCard.xlsx");
            wb.calculateFormula();
            /*wb.getWorksheets().get(0).autoFitRows(true);
            wb.getWorksheets().get(1).autoFitRows(true);*/
            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
            //设置合规性类型
            //pdfSaveOptions.setCompliance(PdfCompliance.NONE);
            //pdfSaveOptions.setCreatedTime(com.aspose.cells.DateTime.getNow());
            //将excel中所有的列展示在同一列
            pdfSaveOptions.setAllColumnsInOnePagePerSheet(true);
            //将Excel内容展示在同一页
            pdfSaveOptions.setOnePagePerSheet(true);
            fileOutputStream = new FileOutputStream(pdfFile);
            wb.save(fileOutputStream,pdfSaveOptions);
            wb.dispose();
            replaceStr(targetAddress,"ce1.pdf","Evaluation Only. Created with Aspose.Cells for Java.Copyright 2003 - 2020 Aspose Pty Ltd.");
        } catch (Exception e) {
            log.error("excel转pdf文件失败",e);
        }finally {
            IOUtils.closeQuietly(fileOutputStream);
        }
    }

    @Test
    public void changeHigh() throws Exception {
        String fileUrl = "changeHigh.xlsx";
        Workbook workbook = new Workbook(fileUrl);
        WorksheetCollection worksheets = workbook.getWorksheets();
        //获取Excel中有几个sheet
        int count = worksheets.getCount();
        System.out.println(count);
        for (int i = 0; i < 1; i++) {
            Worksheet worksheet = worksheets.get(i);
            Cells cells = worksheet.getCells();
            //获取合并单元格的集合
            ArrayList mergedCells = cells.getMergedCells();
            mergedCells.forEach(mergedCell->{
                System.out.println(mergedCell);
            });
            int size = mergedCells.size();
            System.out.println(mergedCells);
            System.out.println(size);
            //获取sheet有多少列，多少行从0开始
            int maxColumn = cells.getMaxColumn();
            int maxRow = cells.getMaxRow();
            System.out.println(maxColumn+"列"+maxRow+"行");
            //获取列宽(字符)、行高（磅）
            double columnWidth = cells.getColumnWidth(0);
            double rowHeight = cells.getRowHeight(0);
            System.out.println("列宽：" + columnWidth + "行高：" + rowHeight);
            double columnWidth1 = cells.getColumnWidthPixel(0);
            double rowHeight1 = cells.getColumnWidthInch(0);
            System.out.println("列宽：" + columnWidth1 + "行高：" + rowHeight1);
            //获取该单元格样式
            Style cellStyle = cells.getCellStyle(1, 6);
            //获取单元格字体样式
            Font font = cellStyle.getFont();
            //获取字体大小
            String name = font.getName();
            int size1 = font.getSize();
            System.out.println(name+size1);
            Cell cell = cells.get(1, 6);
            Object value = cell.getValue();
            Range mergedRange = cell.getMergedRange();

            double endColumn = mergedRange.getFirstColumn();
            System.out.println(endColumn);
            System.out.println(value);

        }

    }

    private void setHight(Cells cells) {
        for (int i = 7; i < 18; i++) {
            Cell cell = cells.get(i, 6);
            Cell cell1 = cells.get(i, 2);
            String value = (String) cell.getValue();
            String value1 = (String)cell1.getValue();
            double ceil = Math.ceil(value.length() / 26.0);
            double ceil1 = Math.ceil(value1.length() / 5.0);
            double max = Math.max(ceil, ceil1);
            cells.setRowHeight(i,max*13);
        }
    }
}
