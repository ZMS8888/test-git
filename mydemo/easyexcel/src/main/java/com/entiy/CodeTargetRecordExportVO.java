package com.entiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author shenzm  工艺工序检验配置表
 * @Date 2022-10-13 10:43
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CodeTargetRecordExportVO {




    /**
     * id
     */
	private Integer id;

    /**
     * 扫码记录id
     */
	private Integer codeRecordId;

	/**
	 * 工序检验配置id
	 */
	private Integer procedureInspectionConfigId;

    /**
     * 指标名
     */
	private String targetName;

    /**
     * 指标英文名
     */
	private String targetEname;

    /**
     * 检测值
     */
	private String value;


	/**
	 * 标准值
	 */
	private String standardValue;


	/**
	 * 检验结果
	 */
	private Boolean result;

	/**
	 * 检测时间
	 */
	private Date reportTime;

	/**
	 * 检验员
	 */
	private String reportBy;

	/**
	 * 检验仪器
	 */
	private String inspectionInstrument;

	/**
	 * 检验标准
	 */
	private String inspectionStandard;

	/**
	 * 工序编号
	 */
	private String procedureCode ;
}
