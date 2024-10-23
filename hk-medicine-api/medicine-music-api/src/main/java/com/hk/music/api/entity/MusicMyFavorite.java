package com.hk.music.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 我喜好的音乐
 *
 * @author pengzhengfa
 */
@Data
@TableName(value = "music_my_favorite")
public class MusicMyFavorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "music_id")
    private Integer musicId;

    @TableLogic
    @TableField(value = "del_flag")
    private Integer delFlag = 0;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;
}
