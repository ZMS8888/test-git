package com.utils;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class CustomCellWriteHandler  implements CellWriteHandler {
    private static final Logger LOGGER =  LoggerFactory.getLogger(CustomCellWriteHandler.class);

    //标黄行宽集合
    private Set<Integer> rowIndexs;

    //构造
    public CustomCellWriteHandler(Set<Integer> rowIndexs) {
        this.rowIndexs = rowIndexs;
    }

    public CustomCellWriteHandler() {
    }

    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        LOGGER.info("beforeCellCreate~~~~");
    }

    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        LOGGER.info("afterCellCreate~~~~");
    }

    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

        // 这里可以对cell进行任何操作
        LOGGER.info("第{}行，第{}列写入完成。", cell.getRowIndex(), cell.getColumnIndex());
        if (CollectionUtils.isNotEmpty(rowIndexs)) {
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            CellStyle cellStyle = workbook.createCellStyle();
            //字体
            Font cellFont = workbook.createFont();
            cellFont.setBold(true);
            cellStyle.setFont(cellFont);
            //标黄,要一起设置
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //设置前景填充样式
            cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());//前景填充色

            if (rowIndexs.contains(cell.getRowIndex())) {
                cell.setCellStyle(cellStyle);
            }
        }

        //获取工作簿
//        HSSFWorkbook wb = new HSSFWorkbook();
//        //获取sheet
//        HSSFSheet sheet = wb.createSheet();
//        HSSFRow row = sheet.createRow();
//        HSSFCellStyle style = wb.createCellStyle();

        // 这里可以对cell进行任何操作
      /*  if (CollectionUtils.isNotEmpty(rowIndexs)) {
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            CellStyle cellStyle = workbook.createCellStyle();

            Sheet sheet = writeSheetHolder.getSheet();
            cellStyle.setAlignment(new HSSFWorkbook().createCellStyle().getAlignment());
            cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
            cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
            cellStyle.setBorderTop(BorderStyle.THIN);//上边框
            cellStyle.setBorderRight(BorderStyle.THIN);//右边框
            cellStyle.setWrapText(true);//自动换行

            //字体
//            Font cellFont = workbook.createFont();
//            cellFont.setBold(true);
//            cellStyle.setFont(cellFont);
//            //标黄,要一起设置
//            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); //设置前景填充样式
//            cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());//前景填充色

            if (rowIndexs.contains(cell.getRowIndex())) {
                Row row = null;
                //循环创建空白单元格
                for (int i = 0; i < rowIndexs.size(); i++) {
                    for (Integer rowIndex : rowIndexs){
                        //创建4-10列的空白格
                        row = sheet.getRow(rowIndex.intValue());
                        if (row == null){
                            row = sheet.createRow(rowIndex.intValue());
                        }
                        for (int j = 3; j <= 9; j++) {
                            //获取8行的cell列
                            cell = row.createCell(j);
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue(" ");
                            LOGGER.info("第{}行，第{}列创建空白格。", cell.getRowIndex(), j);
                        }
                        //创建12列的红白格
                        cell = row.createCell(11);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(" ");
                        LOGGER.info("第{}行，第11列创建空白格。", cell.getRowIndex());
                        //创建21列的空白格
                        cell = row.createCell(21);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(" ");
                        LOGGER.info("第{}行，第21列创建空白格。", cell.getRowIndex());
                    }
                }
            }
        }*/
    }
}

