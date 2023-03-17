package com.entiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author shenzm
 * @Description 工单上料表
 * @Date 2022/3/29 17:56
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrderFeedRecordExportVO {


    /**
     * 工单号
     */
    private String workOrderNum;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 物料数量
     */
    private Double materialNum;



    /**
     * 扫码记录id
     */
    private Integer codeRecordId;


}
