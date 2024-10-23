package com.hk.music.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户抽奖信息表
 *
 * @author pengzhengfa
 */
@Data
@TableName(value = "user_raffle")
public class UserRaffle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;
}