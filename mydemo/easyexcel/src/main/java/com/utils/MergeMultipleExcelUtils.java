package com.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * 合并多个Excel
 */
public class MergeMultipleExcelUtils {
    /**
     * #合并多个excel文件
     * @param fileLists excel文件路径
     * @param path 目标文件保存目录
     * @param fileName 目标文件名称
     */
    public static void mergeExcel(List<String> fileLists, String path, String fileName) {
        // 创建新的excel工作簿
        XSSFWorkbook newExcelWorkBook = new XSSFWorkbook();
        // 遍历需要合并的excel文件
        for (String excelName : fileLists) {
            try (InputStream in = new FileInputStream(excelName)) {
                // 创建工作簿
                XSSFWorkbook tmpWorkBook = new XSSFWorkbook(in);
                // 获取工作簿中的Sheet个数
                int len = tmpWorkBook.getNumberOfSheets();
                if (len <= 1) {
                    XSSFSheet tmpSheet = tmpWorkBook.getSheetAt(0);
                    XSSFSheet newExcelSheet = newExcelWorkBook.createSheet(tmpSheet.getSheetName());
                    // 复制sheet内容
                    copyExcelSheet(newExcelWorkBook, tmpSheet, newExcelSheet);
                } else {
                    for (int i = 0; i < len; i++) {
                        XSSFSheet tmpSheet = tmpWorkBook.getSheetAt(i);
                        XSSFSheet newExcelSheet = newExcelWorkBook.createSheet(tmpSheet.getSheetName());
                        // 复制sheet内容
                        copyExcelSheet(newExcelWorkBook, tmpSheet, newExcelSheet);
                    }
                }
                // 关闭tmpWorkBook工作簿
                tmpWorkBook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 新生成的excel文件
        if (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls")) {
            fileName += ".xlsx";
        }
        String excelFileName = path + File.separator + fileName;
        // 判断文件是否存在
        File excelFile = new File(excelFileName);
        if (excelFile.exists()) {
            // 存在则删除
            excelFile.delete();
        }
        // 使用输出流写出
        try (FileOutputStream fos = new FileOutputStream(excelFileName)) {
            newExcelWorkBook.write(fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                newExcelWorkBook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("excel文件合并成功，合并后文件路径：" + excelFileName);
    }

    /**
     * #复制sheet到新的excel文件中
     * @param workbook excel工作簿
     * @param tmpSheet 来源sheet
     * @param newExcelSheet 新生成的sheet
     */
    public static void copyExcelSheet(XSSFWorkbook workbook, XSSFSheet tmpSheet, XSSFSheet newExcelSheet) {
        // 合并单元格
        mergeSheetAllRegion(tmpSheet, newExcelSheet);
        // 设置单元格列宽度
        // 获取最后一个单元格位置
        int len = tmpSheet.getRow(tmpSheet.getFirstRowNum()).getLastCellNum();
        for (int i = 0; i < len; i++) {
            newExcelSheet.setColumnWidth(i, tmpSheet.getColumnWidth(i));
        }
        // 复制每行内容
        Iterator<Row> it = tmpSheet.iterator();
        while (it.hasNext()) {
            XSSFRow tmpRow = (XSSFRow) it.next();
            // 创建新行
            XSSFRow newExcelRow = newExcelSheet.createRow(tmpRow.getRowNum());
            // 复制行
            copyExcelRow(workbook, tmpRow, newExcelRow);
        }
    }

    /**
     * #合并单元格
     * @param tmpSheet 来源sheet
     * @param newExcelSheet 目标sheet
     */
    private static void mergeSheetAllRegion(XSSFSheet tmpSheet, XSSFSheet newExcelSheet) {
        int num = tmpSheet.getNumMergedRegions();
        CellRangeAddress cellRange = null;
        for (int i = 0; i < num; i++) {
            cellRange = tmpSheet.getMergedRegion(i);
            newExcelSheet.addMergedRegion(cellRange);
        }
    }

    /**
     * #复制excel中的行到新的sheet中
     * @param workbook 目标工作簿
     * @param tmpRow 来源excel行
     * @param newExcelRow 目标excel行
     */
    public static void copyExcelRow(XSSFWorkbook workbook, XSSFRow tmpRow, XSSFRow newExcelRow) {
        // 设置行高
        newExcelRow.setHeight(tmpRow.getHeight());
        // 获取所有列
        Iterator<Cell> it = tmpRow.cellIterator();
        while (it.hasNext()) {
            XSSFCell tmpCell = (XSSFCell) it.next();
            // 创建单元格
            XSSFCell newExcelCell = newExcelRow.createCell(tmpCell.getColumnIndex());
            // 复制单元格
            copyExcelCell(workbook, tmpCell, newExcelCell);
        }
    }

    /**
     * #复制单元格
     * @param workbook 目标工作簿
     * @param tmpCell 来源excel单元格
     * @param newExcelCell 目标excel单元格
     */
    public static void copyExcelCell(XSSFWorkbook workbook, XSSFCell tmpCell, XSSFCell newExcelCell) {
        XSSFCellStyle newExcelStyle = workbook.createCellStyle();
        // 复制单元格样式
        newExcelStyle.cloneStyleFrom(tmpCell.getCellStyle());
        // 单元格样式
        newExcelCell.setCellStyle(newExcelStyle);
        if (tmpCell.getCellComment() != null) {
            newExcelCell.setCellComment(tmpCell.getCellComment());
        }
        // 不同数据类型处理
        CellType tmpCellType = tmpCell.getCellType();
        newExcelCell.setCellType(tmpCellType);
        if (tmpCellType == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(tmpCell)) {
                newExcelCell.setCellValue(tmpCell.getDateCellValue());
            } else {
                newExcelCell.setCellValue(tmpCell.getNumericCellValue());
            }
        } else if (tmpCellType == CellType.STRING) {
            newExcelCell.setCellValue(tmpCell.getRichStringCellValue());
        } else if (tmpCellType == CellType.BLANK) {
        } else if (tmpCellType == CellType.BOOLEAN) {
            newExcelCell.setCellValue(tmpCell.getBooleanCellValue());
        } else if (tmpCellType == CellType.ERROR) {
            newExcelCell.setCellErrorValue(tmpCell.getErrorCellValue());
        } else if (tmpCellType == CellType.FORMULA) {
            newExcelCell.setCellFormula(tmpCell.getCellFormula());
        } else {
        }
    }

}
