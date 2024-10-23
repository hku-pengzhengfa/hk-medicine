package com.hk.music.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author pengzhengfa
 */
@Data
@TableName(value = "user_medal")
public class UserMedal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Integer id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "medal_id")
    private Integer medalId;

    @TableField(value = "expiration_time")
    private Integer expirationTime;

    @TableField(value = "relation_code")
    private String relationCode;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;

    @TableField(value = "wear")
    private Integer wear;
}
