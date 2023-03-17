package com.utils;

import com.aspose.cells.Workbook;
import com.aspose.pdf.License;
import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelToPdfUtils {
    /**
     * 设置打印的sheet 自动拉伸比例
     * @param wb
     * @param page 自动拉伸的页的sheet数组
     */
    public static void autoDraw(Workbook wb, int[] page){
        if(null!=page&&page.length>0){
            for (int i = 0; i < page.length; i++) {
                wb.getWorksheets().get(i).getHorizontalPageBreaks().clear();
                wb.getWorksheets().get(i).getVerticalPageBreaks().clear();
            }
        }
    }


    public static boolean getLicense() {
        boolean result = false;
        InputStream is= null;
        try {
            is = ExcelToPdfUtils.class.getClassLoader().getResourceAsStream("excel-license.xml");
            // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);

            result = true;
        } catch (Exception e) {
            log.error("获取excel-license.xml文件失败",e);
        }finally {
            IOUtils.closeQuietly(is);
        }
        return result;
    }

    /**
     * 替换指定文字为白色的遮罩层
     * @param src 需要被转换的带全路径文件名
     * @param dest 转换之后pdf的带全路径文件名
     * @param replaceStr 指定转换的文字(水印的文字)
     */
    public static void replaceStr(String src, String dest, String replaceStr) {
        try {
            List<String> resultList = new ArrayList<>();
            PdfReader reader = new PdfReader(src);
            //获取指定文字的坐标(就是上一步生成的pdf水印)
            getKeyWords(reader,replaceStr,resultList);
            PdfStamper stamper = new PdfStamper(reader, Files.newOutputStream(Paths.get(dest)));
            for (int i = 1; i <= resultList.size(); i++) {
                String xy = resultList.get(i - 1);
                PdfContentByte canvas = stamper.getOverContent(i);
                canvas.saveState();
                //设置颜色
                canvas.setColorFill(BaseColor.WHITE);
                //解析坐标
                double x = Double.parseDouble(xy.split("--")[0]);
                double y = Double.parseDouble(xy.split("--")[1]) - 10;
                //后面2个参数分别是宽高
                canvas.rectangle(x, y, 450, 100);
                canvas.fill();
                canvas.restoreState();
            }
            stamper.close();
            reader.close();
        } catch (Exception e) {
            log.error("替换指定文字为白色的遮罩层错误",e);
        }

    }


    /**
     * 获取指定文字的坐标
     * @param pdfReader
     * @param replaceStr
     * @param resultList
     */
    private static void getKeyWords(PdfReader pdfReader, String replaceStr,List<String> resultList ) {
        try {
            int pageNum = pdfReader.getNumberOfPages();
            PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(
                    pdfReader);
            for (int i = 1; i <= pageNum; i++) {
                pdfReaderContentParser.processContent(i, new RenderListener() {
                    @Override
                    public void renderText(TextRenderInfo textRenderInfo) {
                        String text = textRenderInfo.getText(); // 整页内容
                        if (null != text && text.contains(replaceStr)) {
                            Rectangle2D.Float boundingRectange = textRenderInfo
                                    .getBaseline().getBoundingRectange();
                            String xy = boundingRectange.x + "--" + boundingRectange.y;
                            resultList.add(xy);
                        }
                    }

                    @Override
                    public void renderImage(ImageRenderInfo arg0) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void endTextBlock() {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void beginTextBlock() {
                        // TODO Auto-generated method stub
                    }
                });
            }
        } catch (IOException e) {
            log.error("获取指定文字的坐标错误",e);
        }
    }
}
