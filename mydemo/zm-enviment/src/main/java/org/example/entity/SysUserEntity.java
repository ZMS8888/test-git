package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: shuang
 * @time: 2020/4/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@TableName("sys_users")
public class SysUserEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @TableField(value = "user_name")
    private String username;

    /**
     * varchar(100) NOT NULL COMMENT '密码',
     */

    @TableField(value = "pass_word")
    private String password;

    /**
     * varchar(100) DEFAULT NULL COMMENT '用户头像地址',
     */
    @TableField(value = "header_url")
    private String headerUrl;

    /**
     * int(11) DEFAULT NULL COMMENT '手机号',
     */

    @TableField(value = "mobile")
    private String mobile;

    /**
     * varchar(128) DEFAULT NULL COMMENT '邮箱',
     */
    @TableField(value = "email")
    private String email;

    /**
     * varchar(32) NOT NULL COMMENT '用户昵称',
     */
    @TableField(value = "nick_name")
    private String nickname;
    /**
     * 工号
     */
    @TableField(value = "job_number")
    private String jobNumber;
    /**
     * 身份证号码
     */
    @TableField(value = "id_card")
    private String idCard;
    /**
     * 公司id
     */
    @TableField(value = "comp_id")
    private Integer compId;
    /**
     * 用户绑定范围
     * 比如网格员该字段初始为 公司id.区域id  绑定网格后为  公司id.区域id.网格id
     */
    @TableField(value = "bind_range")
    private String bindRange;
    /**
     * 所属上级单位名称：单位、区域、网格、场所
     */
    @TableField(value = "unit_name")
    private String unitName;





    @TableField(value = "update_by")
    private String updateBy;


    @TableField(value = "create_by")
    private String createBy;

    @TableField(exist = false)
    private String roleId;

    @TableField(exist = false)
    private String roleName;

    /**
     * 1、导入数据临时存储用户角色id，以英文逗号隔开
     */
    @TableField(exist = false)
    private String roleIds;

    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(exist = false)
    private String roleCode;

    /**
     * 角色等级，控制是否能够登录该软件
     */
    @TableField(exist = false)
    private Integer roleLevel;


    /**
     * 年龄
     */
    @TableField(value = "age")
    private Integer age;
    /**
     * 性别 1-女 2-男
     */
    @TableField(value = "sex")
    private Integer sex;
    /**
     * 技能等级 1-初级，2-中级，3-高级
     */
    @TableField(value = "skill_level")
    private Integer skillLevel;

    @TableField(exist = false)
    private String skillLevelName;
    /**
     * 微信号
     */

    @TableField(value = "wechat")
    private String wechat;

    /**
     * 所属部门
     */
    @TableField(value = "department_id")
    private String departmentId;
    /**
     * 入职日期
     */
    @TableField(value = "entry_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;
    /**
     * 职务
     */
    @TableField(value = "post")
    private String post;

    /**
     * 部门名称
     */
    @TableField(value = "department_name")
    private String departmentName;

    /**
     * im用户名
     */
    @TableField(value = "im_user_name")
    private String imUserName;

    /**
     * keycloak_id
     */
    @TableField(value = "keycloak_id")
    private String keycloakId;


    /**
     * 厂区Id
     */
    @TableField(exist = false)
    private Integer aid;

    /**
     * 厂区code
     */
    @TableField(exist = false)
    private String acode;

    /**
     * 厂区名称
     */
    @TableField(exist = false)
    private String aname;

    /**
     * 岗位
     */
    @TableField(exist = false)
    private String postIds;

    /**
     * 岗位名称
     */
    @TableField(exist = false)
    private String postNames;

    /**
     * for 禁用/启用日志
     */
    @TableField(exist = false)
    private String disEnableLog;

}
