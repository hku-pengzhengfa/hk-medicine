package com.hk.music.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户积分表
 *
 * @author pengzhengfa
 */
@Data
@TableName(value = "music_user_score")
public class MusicUserScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "score")
    private Integer score;

    @TableField(value = "total_score")
    private Integer totalScore;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;
}
