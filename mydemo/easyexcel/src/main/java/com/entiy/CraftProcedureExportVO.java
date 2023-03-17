package com.entiy;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @description:  shenzm 工艺工序导出
 * @date 2022/10/13 14:32
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CraftProcedureExportVO {

    /**
     * 工序id
     */
    private Integer procedureId;

    /**
     * 工序编号
     */
    private String procedureCode;

    /**
     * 工序名称
     */
    private String procedureName;

    /**
     * 工序备注
     */
    private String remark;

    /**
     * 操作人
     */
    private String operator;

}
