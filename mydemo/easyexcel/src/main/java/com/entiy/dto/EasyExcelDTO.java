package com.entiy.dto;

import lombok.Data;

/**
 * @author yejiarun
 * @description:
 * @date 2022/9/21 14:58
 */
@Data
public class EasyExcelDTO {
    
    private String sheetName;
    
    
    private Integer rowIndex;

    private String reason;
}
