package com.utils;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

public class MyMergeStrategy extends AbstractMergeStrategy {

    //合并坐标集合
    private List<CellRangeAddress> cellRangeAddresss;

    public int order() {
        return super.order();
    }

    //构造
    public MyMergeStrategy(List<CellRangeAddress> cellRangeAddresss) {
        this.cellRangeAddresss = cellRangeAddresss;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer integer) {
        if (CollectionUtils.isEmpty(cellRangeAddresss)) {
        /*    if (cell.getRowIndex() == 7 ) {
                for (CellRangeAddress item : cellRangeAddresss) {
                    sheet.addMergedRegionUnsafe(item);
                }
            }*/
            if (cell.getRowIndex() == 1 && cell.getColumnIndex() == 0) {
                for (CellRangeAddress item : cellRangeAddresss) {
                    sheet.addMergedRegionUnsafe(item);
                }
            }
        }
    }
}
