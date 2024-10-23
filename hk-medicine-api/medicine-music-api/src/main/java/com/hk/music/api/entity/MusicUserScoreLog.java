package com.hk.music.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户积分日志表
 *
 * @author pengzhengfa
 */
@Data
@TableName(value = "music_user_score_log")
public class MusicUserScoreLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "from_type")
    private Integer fromType;

    @TableField(value = "sub_type")
    private Integer subType;

    @TableField(value = "relation_id")
    private String relationId;

    @TableField(value = "score")
    private Integer score;

    @TableField(value = "before")
    private Integer before;

    @TableField(value = "after")
    private Integer after;

    @TableField(value = "memo")
    private String memo;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;
}
