package com.hk.music.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author pengzhengfa
 */
@Data
@TableName(value = "user_login_log")
public class UserLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Integer id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "login_ip")
    private String loginIp;

    @TableField(value = "login_time")
    private LocalDateTime loginTime;

    @TableField(value = "login_src")
    private Integer loginSrc;

    @TableField(value = "login_user_source")
    private String loginUserSource;

    @TableField(value = "login_status")
    private Integer loginStatus;

    @TableField(value = "ua")
    private String ua;

    @TableField(value = "device_id")
    private String deviceId;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "mode")
    private Integer mode;
}
