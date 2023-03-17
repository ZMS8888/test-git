package com.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class ExcelUtils {
    public static boolean isEmptyRow(Row row) {
        //行不存在
        if (row == null) {
            return true;
        }
        //第一个列位置
        int firstCellNum = row.getFirstCellNum();
        //最后一列位置
        int lastCellNum = row.getLastCellNum();
        //空列数量
        int nullCellNum = 0;
        for (int c = firstCellNum; c < lastCellNum; c++) {
            Cell cell = row.getCell(c);
            if (null == cell || CellType.BLANK == cell.getCellType()) {
                nullCellNum++;
                continue;
            }
            cell.setCellType(CellType.STRING);
            String cellValue = cell.getStringCellValue().trim();
            if (StringUtils.isEmpty(cellValue)) {
                nullCellNum++;
            }
        }
        //所有列都为空
        if (nullCellNum == (lastCellNum - firstCellNum)) {
            return true;
        }
        return false;
    }

    public static void removeEmptyRows(Sheet sheet) {
        int maxRow = sheet.getLastRowNum();
        for (int r = 0; r < maxRow; r++) {
            Row row = sheet.getRow(r);
            if (isEmptyRow(row)) {
                removeMergedRegionInEmptyRow(sheet, r);
            }
        }
        // 刪除空行
        for (int r = 1; r < maxRow; r++) {
            Row row = sheet.getRow(r);
            if (isEmptyRow(row)) {
                removeRow(sheet, r, maxRow);
            }
        }
    }


    /**
     * 移除空行
     *
     * @param sheet
     * @param rowIndex
     * @param maxRow   此参数不能动态从sheet获取
     */
    public static void removeRow(Sheet sheet, int rowIndex, int maxRow) {
//        int startLine = rowIndex;
        int moveLines = -1;
        // 对下一行判空 寻找移动起点行
        while (rowIndex < maxRow && isEmptyRow(sheet.getRow(rowIndex + 1))) {
            rowIndex++;
            moveLines--;
        }
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            //将行号为rowIndex+1到最后一个单元格全部上移moveLines行，以便删除空行
            sheet.shiftRows(rowIndex + 1, lastRowNum, moveLines);
        }
        // 最后判断最后一行是否为空 删除无用数据
        if (rowIndex == lastRowNum) {
            Row removingRow = sheet.getRow(rowIndex);
            if (removingRow != null) {
                sheet.removeRow(removingRow);
            }
        }
    }


    /**
     * @param sheet
     * @param row   开始行
     */
    public static void removeMergedRegionInEmptyRow(Sheet sheet, int row) {
        int sheetMergeCount = sheet.getNumMergedRegions();//获取所有的单元格
        //用于保存要移除的那个单元格序号
        List<Integer> indexs = new ArrayList<>();
//        int index = 0;
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i); //获取第i个单元格
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if (row == firstRow || row == lastRow) {
                indexs.add(i);
            }
        }
        // 删除该行所有合并单元格
        sheet.removeMergedRegions(indexs);
//        sheet.removeMergedRegion(index);//移除合并单元格
    }

    /**
     * 刪除excel文件中的空行
     *
     * @param targetFileName 必須是excel文件
     */
    public static void deleteExcelEmptyRows(File targetFileName) {
        InputStream inp = null;
        Workbook workbook = null;
        OutputStream fileOut = null;
        try {
            inp = Files.newInputStream(targetFileName.toPath());
            workbook = WorkbookFactory.create(inp);
            //获取需要删除的Sheet
            List<Sheet> allSheet = getAllSheet(workbook);
            for (Sheet sheet : allSheet) {
                removeEmptyRows(sheet);
            }
            fileOut = Files.newOutputStream(targetFileName.toPath());
            workbook.write(fileOut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(inp);
            closeWorkBook(workbook);
            IOUtils.closeQuietly(fileOut);
        }
    }

    /**
     * 删除指定sheet页
     *
     * @param targetFileName 路径
     * @param num            sheet页
     */
    public static void deleteSheet(String targetFileName, int num) {
        try {
            FileInputStream fis = new FileInputStream(targetFileName);
            Workbook wb = new XSSFWorkbook(fis);
            wb.removeSheetAt(num);
            FileOutputStream fileOut = new FileOutputStream(targetFileName);
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭表格
     *
     * @param workbook
     */
    public static void closeWorkBook(Workbook workbook) {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

    public static List<Sheet> getAllSheet(Workbook workbook) {
        Iterator<Sheet> iterator = workbook.sheetIterator();
        List<Sheet> sheets = new ArrayList<>();
        while (iterator.hasNext()) {
            sheets.add(iterator.next());
        }
        return sheets;
    }


}
