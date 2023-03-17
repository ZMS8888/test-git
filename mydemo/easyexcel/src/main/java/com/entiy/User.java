package com.entiy;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;
import com.entiy.dto.EasyExcelDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends EasyExcelDTO {

    //@ColumnWidth(100)
    @ExcelProperty(value = "姓名")
    private String name;
    @ExcelProperty(value = "年龄")
    private Integer age;
   // @ContentLoopMerge(eachRow = 2,columnExtend = 2)
    @ExcelProperty(value = "性别")
    private String sex;
}
