package com.hk.music.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 艺术家表
 *
 * @author pengzhengfa
 */
@Data
@TableName(value = "music_artist")
public class MusicArtist implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "artist_name")
    private String artistName;

    @TableField(value = "cover_image")
    private String coverImage;

    @TableField(value = "head_cover_image")
    private String headCoverImage;

    @TableField(value = "media")
    private String media;

    @TableField(value = "memo")
    private String memo;

    @TableField(value = "desc_content")
    private String descContent;

    @TableField(value = "recommend")
    private String recommend;

    @TableField(value = "sort")
    private Integer sort;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;
}
