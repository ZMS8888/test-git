package com.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSONObject;
import com.entiy.User;
import com.entiy.dto.EasyExcelDTO;
import jdk.nashorn.internal.runtime.JSONFunctions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;


/**
 * 读取Excel监听器
 */
@Slf4j
@Data
public class ReadExcelDataListener<T extends EasyExcelDTO> implements ReadListener<T> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    public List<T> dataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);


    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        //表名
        ReadSheetHolder sheetHolder = analysisContext.readSheetHolder();
        String sheetName = sheetHolder.getSheetName();
        //行号
        ReadRowHolder rowHolder = analysisContext.readRowHolder();
        Integer rowIndex = rowHolder.getRowIndex();
        data.setSheetName(sheetName);
        data.setRowIndex(rowIndex + 1);
        dataList.add(data);
        //达到BATCH_COUNT后,可存储一次数据库,防止数据几万条数据在内存中,容易OOM
        //if (cacheList.size() >= BATCH_SIZE) {
        //    log.info("完成一批Excel记录的导入,条数为：{}", cacheList.size());
        //    cacheList = new ArrayList<>(BATCH_SIZE);
        //}
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        ReadSheetHolder sheetHolder = analysisContext.readSheetHolder();
        String sheetName = sheetHolder.getSheetName();
        log.info("{}Excel表完成记录的导入，条数为：{}", sheetName, dataList.size());
    }
}
