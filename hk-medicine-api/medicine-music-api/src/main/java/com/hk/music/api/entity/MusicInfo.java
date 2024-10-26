package com.hk.music.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 音乐信息
 *
 * @author pengzhengfa
 */
@Data
@TableName(value = "music_info")
public class MusicInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "music_name")
    private String musicName;

    @TableField(value = "cover_image")
    private String coverImage;

    @TableField(value = "cover_large_image")
    private String coverLargeImage;

    @TableField(value = "author")
    private String author;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "play_count")
    private Integer playCount;

    @TableField(value = "like_count")
    private Integer likeCount;

    @TableField(value = "collect_count")
    private Integer collectCount;

    @TableField(value = "prompt_style_words")
    private String promptStyleWords;

    @TableField(value = "voice_type")
    private Integer voiceType;

    @TableField(value = "create_type")
    private Integer createType;

    @TableField(value = "lyric_content")
    private String lyricContent;

    @TableField(value = "lyric_url")
    private String lyricUrl;

    @TableField(value = "video_url")
    private String videoUrl;

    @TableField(value = "duration")
    private Integer duration;

    @TableField(value = "artist_id")
    private Integer artistId;

    @TableField(value = "music_desc")
    private String musicDesc;

    @TableField(value = "is_new")
    private Integer isNew;

    @TableField(value = "is_hot")
    private Integer isHot;

    @TableField(value = "is_recommend")
    private Integer isRecommend;

    @TableField(value = "is_share")
    private Integer isShare;

    @TableField(value = "share_time")
    private String shareTime;

    @TableField(value = "source_type")
    private Integer sourceType;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;

    @Transient
    @TableField(exist = false)
    private Boolean isFavorite = false;

    @Transient
    @TableField(exist = false)
    private Integer historyId;
}
