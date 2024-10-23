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
@TableName(value = "user_invitation")
public class UserInvitation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "invitee_user_id")
    private Long inviteeUserId;

    @TableField(value = "invitation_code")
    private String invitationCode;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;
}
