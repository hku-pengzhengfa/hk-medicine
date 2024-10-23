package com.hk.music.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pengzhengfa
 */
@Data
@TableName("music_song_collect")
public class MusicSongCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "del_flag")
    private Integer delFlag;

    @TableField(value = "cover")
    private String cover;

    @TableField(value = "introduce")
    private String introduce;

    @TableField(value = "lang_category_ids")
    private String langCategoryIds;

    @TableField(value = "school_category_ids")
    private String schoolCategoryIds;

    @TableField(value = "play_volume")
    private Integer playVolume;

    @TableField(value = "collect_count")
    private Integer collectCount;

    @TableField(value = "music_count")
    private Integer musicCount;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;
}
