package com.utils;


import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.*;

/**
 * 自适应调整Excel行高
 */
public class ExcelManageUtils {

    /**
     * 去除Excel公式
     * @param excelUrl
     * @throws IOException
     */
    public static void deleteExcelFormula(String excelUrl) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(excelUrl);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Iterator<Sheet> iterator = workbook.iterator();
        //遍历sheet
        while (iterator.hasNext()) {
            XSSFSheet sheet = (XSSFSheet) iterator.next();
            int lastRowNum = sheet.getLastRowNum();
            for(int x=0;x<lastRowNum;x++){
                XSSFRow row = sheet.getRow(x);
                if(row!=null){
                    int physicalNumberOfCells = row.getLastCellNum();
                    for (int cellIndex = row.getFirstCellNum(); cellIndex < physicalNumberOfCells; cellIndex++) {
                        XSSFCell cell = row.getCell(cellIndex);
                        CellType cellType = cell.getCellType();
                        if (cellType.equals(CellType.FORMULA)) {
                            String cellFormula = cell.getCellFormula();
                            if (!cellFormula.isEmpty()) {
                                cell.removeFormula();
                            }
                        }
                    }
                }
            }
        }
        OutputStream os = new BufferedOutputStream(new FileOutputStream(excelUrl));
        workbook.write(os);
        fileInputStream.close();
        os.flush();
        os.close();
    }



    /**
     * 自适应调整Excel行高
     * @param excelUrl
     * @throws IOException
     */
    public static void changeExcelHeight(String excelUrl) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(excelUrl);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Iterator<Sheet> iterator = workbook.iterator();
        while (iterator.hasNext()) {
            XSSFSheet sheet = (XSSFSheet) iterator.next();
            int lastRowNum = sheet.getLastRowNum();
            for(int x=0;x<lastRowNum;x++){
                XSSFRow row = sheet.getRow(x);
                if(row!=null &&  row.getPhysicalNumberOfCells()!=0){
                    ExcelManageUtils.calcAndSetRowHeigt(sheet.getRow(x));
                }
            }
        }
        OutputStream os = new BufferedOutputStream(new FileOutputStream(excelUrl));
        workbook.write(os);
        fileInputStream.close();
        os.flush();
        os.close();
    }

    public static void calcAndSetRowHeigt(XSSFRow sourceRow) throws UnsupportedEncodingException {
        for (int cellIndex = sourceRow.getFirstCellNum();
             cellIndex < sourceRow.getPhysicalNumberOfCells(); cellIndex++) {
            //行高
            double maxHeight = sourceRow.getHeight();
            XSSFCell sourceCell = sourceRow.getCell(cellIndex);
            //单元格的内容
            String cellContent = getCellContentAsString(sourceCell);
            if (null == cellContent || "".equals(cellContent)) {
                continue;
            }
            //单元格的宽高及单元格信息
            Map<String, Object> cellInfoMap = getCellInfo(sourceCell);
            Integer cellWidth = (Integer) cellInfoMap.get("width");
            Integer cellHeight = (Integer) cellInfoMap.get("height");
            if (cellHeight > maxHeight) {
                maxHeight = cellHeight;
            }
            System.out.println("单元格的宽度 : " + cellWidth + "    单元格的高度 : " + maxHeight + ",   " +
                    " 单元格的内容 : " + cellContent);
            XSSFCellStyle cellStyle = sourceCell.getCellStyle();
            XSSFFont font = cellStyle.getFont();
            //字体的高度
            short fontHeight = font.getFontHeight();
            // cell内容字符串总宽度     256为单个字符所占的宽度       200 上下留的间隔自定义
            double cellContentWidth =( cellContent.getBytes().length  ) * (fontHeight);
            double zifu =( cellContent.length()  ) * (fontHeight);
            List<String> contentList = Arrays.asList(cellContent.split("\n"));
            //重新计算 宽度
            if (contentList.size() > 1) {
                cellContentWidth = 0.0;
                for (String content : contentList) {
                    double tempCountWidth = 0.0;
                    double tempWidth =( content.getBytes().length  ) * 256;
                    double num =  tempWidth / cellWidth;
                    if (num > 1.0) {
                        tempCountWidth =( (int)num +1) * cellWidth;
                    }else {
                        tempCountWidth =  cellWidth;
                    }
                    cellContentWidth += tempCountWidth;
                }
            }
            //85.333 倍
            System.out.println("字符："+zifu +"    字节："+cellContent.getBytes().length );
            System.out.println("cellContentWidth："+cellContentWidth );
            //字符串需要的行数 不做四舍五入之类的操作
            double stringNeedsRows = (double) cellContentWidth / cellWidth;
            //小于一行补足一行
            if (stringNeedsRows < 1.0) {
                stringNeedsRows = 1.0;
            }
            //需要的高度          (Math.floor(stringNeedsRows) - 1) * 40 为两行之间空白高度
            double stringNeedsHeight = (double) fontHeight * stringNeedsRows+256;
            //需要重设行高
            if (stringNeedsHeight > maxHeight ) {
                maxHeight = stringNeedsHeight;
                //超过原行高三倍 则为5倍 实际应用中可做参数配置
             /*   if (maxHeight / cellHeight > 5) {
                    maxHeight = 5 * cellHeight;
                }*/
                //最后取天花板防止高度不够
                maxHeight = Math.ceil(maxHeight);
                //重新设置行高 同时处理多行合并单元格的情况
                Boolean isPartOfRowsRegion = (Boolean) cellInfoMap.get("isPartOfRowsRegion");
                if (isPartOfRowsRegion) {
                    Integer firstRow = (Integer) cellInfoMap.get("firstRow");

                    Integer lastRow = (Integer) cellInfoMap.get("lastRow");
                    //平均每行需要增加的行高
                    double addHeight = (maxHeight - cellHeight) / (lastRow - firstRow + 1);

                    for (int i = firstRow; i <= lastRow; i++) {
                        double rowsRegionHeight = sourceRow.getSheet().getRow(i).getHeight() + addHeight;
                        sourceRow.getSheet().getRow(i).setHeight((short) rowsRegionHeight);
                    }

                } else {
                    sourceRow.setHeight((short) maxHeight);
                }
            }
            System.out.println("字体高度 : " + fontHeight + ",    字符串宽度 : " + cellContentWidth + ",    字符串需要的行数 : " + stringNeedsRows + ",   需要的高度 : " + stringNeedsHeight + ",   现在的行高 : " + maxHeight);
            System.out.println();
        }
    }

    /**
     * 解析一个单元格得到数据
     * @param cell
     * @return
     */
    private static String getCellContentAsString(XSSFCell cell) {
        if (null == cell) {
            return "";
        }
        String result = "";
        switch (cell.getCellType()) {
            case NUMERIC:
                String s = String.valueOf(cell.getNumericCellValue());
                if (s != null) {
                    if (s.endsWith(".0")) {
                        s = s.substring(0, s.length() - 2);
                    }
                }
                result = s;
                break;
            case STRING:
                result = cell.getStringCellValue() == null ? "" : cell.getStringCellValue().trim();
                break;
            case BLANK:
                break;
            case BOOLEAN:
                result = String.valueOf(cell.getBooleanCellValue());
                break;
            case ERROR:
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 获取单元格及合并单元格的宽度     * @param cell     * @return
     */
    private static Map<String, Object> getCellInfo(XSSFCell cell) {
        XSSFSheet sheet = cell.getSheet();
        int rowIndex = cell.getRowIndex();
        int columnIndex = cell.getColumnIndex();
        boolean isPartOfRegion = false;
        int firstColumn = 0;
        int lastColumn = 0;
        int firstRow = 0;
        int lastRow = 0;
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            firstColumn = ca.getFirstColumn();
            lastColumn = ca.getLastColumn();
            firstRow = ca.getFirstRow();
            lastRow = ca.getLastRow();
            if (rowIndex >= firstRow && rowIndex <= lastRow) {
                if (columnIndex >= firstColumn && columnIndex <= lastColumn) {
                    isPartOfRegion = true;
                    break;
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Integer width = 0;
        Integer height = 0;
        boolean isPartOfRowsRegion = false;
        if (isPartOfRegion) {
            for (int i = firstColumn; i <= lastColumn; i++) {
                width += sheet.getColumnWidth(i);
            }
            for (int i = firstRow; i <= lastRow; i++) {
                height += sheet.getRow(i).getHeight();
            }
            if (lastRow > firstRow) {
                isPartOfRowsRegion = true;
            }
        } else {
            width = sheet.getColumnWidth(columnIndex);
            height += cell.getRow().getHeight();
        }
        map.put("isPartOfRowsRegion", isPartOfRowsRegion);
        map.put("firstRow", firstRow);
        map.put("lastRow", lastRow);
        map.put("width", width);
        map.put("height", height);
        return map;
    }


}
