package com.entiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author shenzm
 * @Description 康居人流程卡：追溯报告表
 * @Date 2022/10/11 14:30
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductFlowCodeExportVO {


    /**
     * 生产流水码id
     */
    private Integer id;

    /**
     * 生产流水码
     */
    private String productFlowCode;


    /**
     * 关联单号
     */
    private String relationNumber;


    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 物料编码
     */
    private String materialCode;




}
