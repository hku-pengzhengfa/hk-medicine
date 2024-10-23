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
@TableName(value = "social_user")
public class SocialUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "uuid")
    private String uuid;

    @TableField(value = "source")
    private String source;

    @TableField(value = "access_token")
    private String accessToken;

    @TableField(value = "expire_in")
    private Integer expireIn;

    @TableField(value = "refresh_token")
    private String refreshToken;

    @TableField(value = "open_id")
    private String openId;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "union_id")
    private String unionId;

    @TableField(value = "email")
    private String email;

    @TableField(value = "raw_user_info")
    private String rawUserInfo;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;

    @TableField(value = "old_user_id")
    private Long oldUserId;
}
