package com.zm.entity.DTO;

import com.zm.util.excel.ExcelImport;
import lombok.Data;

@Data
public class ImportDTO {

    /**
     * 获取行数
     */
    private Integer rowNum;

    /**
     * 获取原始数据
     */
    private String rowData;

    /**
     * 获取错误信息
     */
    private String rowTips;

    /**
     * required = true  必填不能为空
     * unique = true  唯一性校验
     */
    @ExcelImport(value = "姓名",required = true,unique = true)
    private  String  name;

    @ExcelImport("年龄")
    private Integer age;

    @ExcelImport(value = "性别",kv = "1-男;2-女")
    private String sex;

    @ExcelImport(value = "电话", maxLength = 11)
    private String phone;


}
