package com.entiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author shenzm  质检返工表
 * @Date 2022-10-11 10:35
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MaintainRecordExportVO {

    /**
     * 流水号
     */
	private String serialNumber;

	/**
	 * 流水码
	 */
	private String barCode;
    /**
     * 维修项目
     */
	private String maintainName;

    /**
     * 维修定义类型
     */
	private String maintainType;

    /**
     * 故障类型（问题类型）
     */
	private String faultType;

    /**
     * 工单号
     */
	private String workOrder;

    /**
     * 备注说明
     */
	private String remark;

	/**
	 * 扫码记录id
	 */
	private Integer codeRecordId;

    /**
     * 创建人
     */
	private String createBy;

	/**
	 * 修改人
	 */
	private String updateByName;

	/**
	 * 维修结果
	 */
	private String result;
	






}
