package zm.easyExecl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.entiy.CodeTargetRecordExportVO;
import com.entiy.CraftProcedureExportVO;
import com.entiy.FillData;
import com.entiy.MaintainRecordExportVO;
import com.entiy.ProductFlowCodeExportVO;
import com.entiy.WorkOrderFeedRecordExportVO;
import com.spire.xls.Worksheet;
import com.utils.CustomCellWriteHandler;
import com.utils.MyMergeStrategy;
import com.utils.TestFileUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.springframework.util.ResourceUtils;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.utils.ExcelUtils.deleteExcelEmptyRows;
import static com.utils.ExcelUtils.deleteSheet;


public class TestFillSheet {

    private static final String fileUrl = "D:\\yl\\demo\\fillexcel\\";


    /**
     * 流程卡数据填充
     */
    @Test
    public void testCard() throws IOException {
        //获取文件的URL
        String file = ResourceUtils.getFile("classpath:template/card.xlsx").getPath();
        // 加载模板
        String templateFile = "kjrFlowCard.xlsx";
        // 写入文件
        String targetFileName = "ExcelCard.xlsx";

        // 准备数据
        ProductFlowCodeExportVO productFlowCodeExportVO = init();
        List<WorkOrderFeedRecordExportVO> workOrderFeedRecordExportVOS = init1();
        List<CraftProcedureExportVO> craftProcedureExportVOS = craftProcedureExportVOS();
        List<CodeTargetRecordExportVO> codeTargetRecordExportVOS = codeTargetRecordExportVOS();
        List<MaintainRecordExportVO> maintainRecordExportVOS = maintainRecordExportVOS();
        // 创建一个工作簿对象
        ExcelWriter workBook = EasyExcel.write(targetFileName).withTemplate(templateFile).build();
        Workbook sheets = workBook.writeContext().writeWorkbookHolder().getWorkbook();
        sheets.setSheetName(0,"0-"+workOrderFeedRecordExportVOS.size() + "-" + craftProcedureExportVOS.size());
        sheets.setSheetName(1,"1-"+workOrderFeedRecordExportVOS.size() + "-" + craftProcedureExportVOS.size());
        // 创建工作表对象
        WriteSheet sheet = EasyExcel.writerSheet(0).build();
        WriteSheet sheet2 = EasyExcel.writerSheet(1).build();
        // 单组数据填充
        workBook.fill(productFlowCodeExportVO,sheet)
                .fill(new FillWrapper("workOrderFeedRecordExportVOS",workOrderFeedRecordExportVOS),sheet)
                .fill(new FillWrapper("craftProcedureExportVOS",craftProcedureExportVOS),sheet)
                .fill(new FillWrapper("codeTargetRecordExportVOS",codeTargetRecordExportVOS),sheet)
                .fill(new FillWrapper("maintainRecordExportVOS",maintainRecordExportVOS),sheet);
        workBook.fill(productFlowCodeExportVO,sheet2)
                .fill(new FillWrapper("workOrderFeedRecordExportVOS",workOrderFeedRecordExportVOS),sheet2)
                .fill(new FillWrapper("craftProcedureExportVOS",craftProcedureExportVOS),sheet2)
                .fill(new FillWrapper("codeTargetRecordExportVOS",codeTargetRecordExportVOS),sheet2)
                .fill(new FillWrapper("maintainRecordExportVOS",maintainRecordExportVOS),sheet2);
        // 关闭流 ！！！
        workBook.finish();
        //删除空行excel
        deleteExcelEmptyRows(new File(targetFileName));
        //调整工序信息行高
        changeHigh(targetFileName,workOrderFeedRecordExportVOS.size(),craftProcedureExportVOS.size());
        //删除指定sheet页
        deleteSheet(targetFileName,2);
    }

    private void changeHigh(String targetFileName, int size, int num) {
        // 原始excel路径
        try {
            com.aspose.cells.Workbook wb = new com.aspose.cells.Workbook(targetFileName);
            Cells cells = wb.getWorksheets().get(0).getCells();
            setHigh(cells,size,num);
            wb.save(targetFileName);
            wb.dispose();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void setHigh(Cells cells, int size, int num) {
        for (int i = 7+size; i < 7+size+num; i++) {
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
    @Test
    public void autoFits() {
        String targetFileName = "ExcelCard1.xlsx";
        com.spire.xls.Workbook workbook = new com.spire.xls.Workbook();
        workbook.loadFromFile(targetFileName);
        final Worksheet worksheet = workbook.getWorksheets().get(0);
        worksheet.getRange().get(7,1,100,100).autoFitRows();
        workbook.save();
        worksheet.dispose();
        workbook.dispose();
    }

    @Test
    public void test6(){
        // 准备数据
        List<FillData> fillData = initFillData();
        Map<String, Object> dateAndTotal = new HashMap<String, Object>();
        dateAndTotal.put("date","2021-11-24");
        dateAndTotal.put("total","1223");
        // 加载模板
        String templateFile = "complex.xlsx";
        // 写入文件
        String targetFileName = "Excel填充—组合数据.xlsx";
        //TODO 单元格样式
        Set<Integer> rowsBorderSet= new HashSet<Integer>();
        CustomCellWriteHandler customCellWriteHandler = null;

        //TODO 单元格合并
        List<CellRangeAddress> cellRangeAddresss = new ArrayList<CellRangeAddress>();
        int firstRow = 17; //excel中表示第八行，即模板中默认的一条
        int lastRow = 17;
        if (CollectionUtils.isEmpty(fillData)){
            if (fillData.size() > 1){
                //合并每条报销单的第3-10列
                for (int i = 1; i < fillData.size(); i++) {
                    firstRow++;
                    lastRow++;

                    cellRangeAddresss.add(new CellRangeAddress(firstRow, lastRow, 1, 2));
                    rowsBorderSet.add(firstRow);
                }
            }
        }
        customCellWriteHandler = new CustomCellWriteHandler(rowsBorderSet);
        MyMergeStrategy myMergeStrategy = new MyMergeStrategy(cellRangeAddresss);

        ExcelWriter workBook = EasyExcel.write(targetFileName)
                //注册单元格式
                .registerWriteHandler(customCellWriteHandler)
                //注册合并策略
                .registerWriteHandler(myMergeStrategy)
                .withTemplate(templateFile).build();

        // 创建一个工作簿对象
       // ExcelWriter workBook = EasyExcel.write(targetFileName).withTemplate(templateFile).build();
        // 创建工作表对象
        WriteSheet sheet = EasyExcel.writerSheet().build();
        //FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        FillConfig fillConfig = FillConfig.builder().forceNewRow(true).build();

        // 多组数据填充
        workBook.fill(new FillWrapper("data",fillData),fillConfig,sheet);
        workBook.fill(new FillWrapper("data1",fillData),fillConfig,sheet);
        // 单组数据填充
        workBook.fill(dateAndTotal,sheet);
        // 关闭流 ！！！
        workBook.finish();
    }


    /**
     * 向下填充数据
     */
    @Test
    public void test5(){
        // 加载模板
        String templateFile = "complex.xlsx";
        // 写入文件
        String targetFileName = "Excel填充—组合数据.xlsx";
        // 创建一个工作簿对象
        ExcelWriter workBook = EasyExcel.write(targetFileName).withTemplate(templateFile).build();
        // 创建工作表对象
        WriteSheet sheet = EasyExcel.writerSheet().build();
        //FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        FillConfig fillConfig = FillConfig.builder().forceNewRow(true).build();
        // 准备数据
        List<FillData> fillDatas = initFillData();
        Map<String, Object> dateAndTotal = new HashMap<String, Object>();
        dateAndTotal.put("date","2021-11-24");
        dateAndTotal.put("total","1223");
        // 多组数据填充
        workBook.fill(new FillWrapper("data",fillDatas),fillConfig,sheet);
        workBook.fill(new FillWrapper("data1",fillDatas),fillConfig,sheet);
        // 单组数据填充
        workBook.fill(dateAndTotal,sheet);
        // 关闭流 ！！！
        workBook.finish();
    }



    /**
     * 复杂的填充
     *
     * @since 2.1.1
     */
    @Test
    public  void complexFill() throws IOException {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        String templateFileName =
                TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "complex.xlsx";
        String name = "complexFill" + System.currentTimeMillis();
        String fileName = TestFillSheet.fileUrl + name + ".xlsx";
        // 方案1
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
        // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
        // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
        // 如果数据量大 list不是最后一行 参照下一个
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(data(), fillConfig, writeSheet);
        excelWriter.fill(data(), fillConfig, writeSheet);
       /* Map<String, Object> map = MapUtils.newHashMap();
        map.put("date", "2019年10月9日13:28:28");
        map.put("total", 1000);
        excelWriter.fill(map, writeSheet);*/
    }

    /**
     * 多列表组合填充填充
     *
     * @since 2.2.0-beta1
     */
    @Test
    public  void compositeFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量 {前缀.} 前缀可以区分不同的list
        String templateFileName =
                TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "composite.xlsx";

        String fileName = TestFillSheet.fileUrl + "compositeFill" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        // 方案1

        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
        excelWriter.fill(new FillWrapper("data1", data()), fillConfig, writeSheet);
        excelWriter.fill(new FillWrapper("data2", data()), writeSheet);
        excelWriter.fill(new FillWrapper("data3", data()), writeSheet);

        Map<String, Object> map = new HashMap<String, Object>();
        //map.put("date", "2019年10月9日13:28:28");
        map.put("date", new Date());

        excelWriter.fill(map, writeSheet);

    }

    @Test
    public  void listFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // 填充list 的时候还要注意 模板中{.} 多了个点 表示list
        String templateFileName =
                TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "list.xlsx";

        // 方案1 一下子全部放到内存里面 并填充
        String fileName = TestFillSheet.fileUrl + "listFill" + System.currentTimeMillis() + ".xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(data());

     /*   // 方案2 分多次 填充 会使用文件缓存（省内存） jdk8
        // since: 3.0.0-beta1
        fileName = TestFileUtil.getPath() + "listFill" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName)
                .withTemplate(templateFileName)
                .sheet()
                .doFill(() -> {
                    // 分页查询数据
                    return data();
                });

        // 方案3 分多次 填充 会使用文件缓存（省内存）
        fileName = TestFileUtil.getPath() + "listFill" + System.currentTimeMillis() + ".xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(data(), writeSheet);
            excelWriter.fill(data(), writeSheet);
        }*/
    }

    /**
     * 简单填充
     */
    @Test
    public   void simpleTest() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        String templateFileName = "simple.xls";

        // 方案1 根据对象填充
        String fileName =  "simpleFill.xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        FillData fillData = new FillData();
        fillData.setName("张三");
        fillData.setNumber(5.2);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet("Sheet1").doFill(fillData);

      /*  // 方案2 根据Map填充
        fileName = TestFileUtil.getPath() + "simpleFill" + System.currentTimeMillis() + ".xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        Map<String, Object> map = MapUtils.newHashMap();
        map.put("name", "张三");
        map.put("number", 5.2);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(map);*/
    }


    /**
     * 生成多组数据代码
     * @return
     */
    private static List<FillData> initFillData() {
        ArrayList<FillData> fillDatas = new ArrayList<FillData>();
        for (int i = 0; i < 10; i++) {
            FillData fillData = new FillData();
            fillData.setName("李四0" + i);
            fillData.setAge(10 + i);
            fillDatas.add(fillData);
        }
        return fillDatas;
    }
    private static List<FillData> data() {
        List<FillData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            FillData fillData = new FillData();
            list.add(fillData);
            fillData.setName("张三");
            fillData.setNumber(5.2);
            fillData.setDate(new Date());
        }
        return list;
    }

    public static ProductFlowCodeExportVO init() {
        ProductFlowCodeExportVO productFlowCodeExportVO = new ProductFlowCodeExportVO();
        productFlowCodeExportVO.setProductFlowCode("20192528");
        productFlowCodeExportVO.setMaterialCode("CT0101");
        productFlowCodeExportVO.setMaterialName("吸氧机");
        return productFlowCodeExportVO;
    }
    public static List<WorkOrderFeedRecordExportVO> init1() {
        List<WorkOrderFeedRecordExportVO> workOrderFeedRecordExportVOS = new ArrayList<WorkOrderFeedRecordExportVO>();
        for (int i = 0; i < 4; i++) {
            WorkOrderFeedRecordExportVO workOrderFeedRecordExportVO = new WorkOrderFeedRecordExportVO();
            workOrderFeedRecordExportVO.setMaterialCode("CT0101");
            workOrderFeedRecordExportVO.setMaterialName("吸氧机吸氧机吸氧机吸氧机吸氧机吸氧机吸氧机吸氧机吸氧机吸氧机吸氧机");
            workOrderFeedRecordExportVOS.add(workOrderFeedRecordExportVO);
        }
        return workOrderFeedRecordExportVOS;
    }
    public static List<CraftProcedureExportVO> craftProcedureExportVOS() {
        List<CraftProcedureExportVO> craftProcedureExportVOS = new ArrayList<CraftProcedureExportVO>();
        for (int i = 0; i < 4; i++) {
            CraftProcedureExportVO craftProcedureExportVO = new CraftProcedureExportVO();
            craftProcedureExportVO.setProcedureCode(String.valueOf(i));
            craftProcedureExportVO.setProcedureName("吸氧机"+i);
            craftProcedureExportVO.setRemark("workOrderFeedRecordExportVOworkOrderFeedRecordExportVOworkOrderFeedRecordExportVOworkOrderFeedRecordExportVOworkOrderFeedRecordExportVO"+i);
            craftProcedureExportVO.setOperator("029227"+i);
            craftProcedureExportVOS.add(craftProcedureExportVO);
        }
        return craftProcedureExportVOS;
    }
    public static List<CodeTargetRecordExportVO> codeTargetRecordExportVOS() {
        List<CodeTargetRecordExportVO> codeTargetRecordExportVOS = new ArrayList<CodeTargetRecordExportVO>();
        for (int i = 0; i < 4; i++) {
            CodeTargetRecordExportVO codeTargetRecordExportVO = new CodeTargetRecordExportVO();
            codeTargetRecordExportVO.setTargetName("焊锡不良"+i);
            codeTargetRecordExportVO.setStandardValue("标准值"+i);
            codeTargetRecordExportVO.setValue("检测值"+i);
            codeTargetRecordExportVO.setInspectionInstrument("检测机");
            codeTargetRecordExportVO.setReportBy("029227"+i);
            codeTargetRecordExportVOS.add(codeTargetRecordExportVO);
        }
        return codeTargetRecordExportVOS;
    }

    public static List<MaintainRecordExportVO> maintainRecordExportVOS() {
        List<MaintainRecordExportVO> maintainRecordExportVOS = new ArrayList<MaintainRecordExportVO>();
        for (int i = 0; i < 4; i++) {
            MaintainRecordExportVO maintainRecordExportVO = new MaintainRecordExportVO();
            maintainRecordExportVO.setMaintainName("返工返修"+i);
            maintainRecordExportVO.setMaintainType("基本返修"+i);
            maintainRecordExportVO.setFaultType("一般异常"+i);
            maintainRecordExportVO.setCreateBy("029227"+i);
            maintainRecordExportVO.setRemark("维修不良");
            maintainRecordExportVO.setResult("合格");
            maintainRecordExportVOS.add(maintainRecordExportVO);
        }
        return maintainRecordExportVOS;
    }

}
