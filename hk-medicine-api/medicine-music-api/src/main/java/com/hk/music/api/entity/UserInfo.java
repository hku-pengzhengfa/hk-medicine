package com.hk.music.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author pengzhengfa
 */
@Data
@TableName(value = "user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "email")
    private String email;

    @TableField(value = "country_code")
    private String countryCode;

    @TableField(value = "mobile")
    private String mobile;

    @TableField(value = "salt")
    private String salt;

    @TableField(value = "password")
    private String password;

    @TableField(value = "gender")
    private Integer gender;

    @TableField(value = "birthday")
    private String birthday;

    @TableField(value = "pay_password")
    private String payPassword;

//    //有修改字段
//    @TableField(value = "login_time")
//    private String loginTime;

    @TableField(value = "login_ip")
    private String loginIp;

    @TableField(value = "login_fail_count")
    private Integer loginFailCount;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "invitation")
    private String invitation;

    @TableField(value = "level")
    private Integer level;

    @TableField(value = "total_like")
    private Integer totalLike;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;
}
