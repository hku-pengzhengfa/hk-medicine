package com.hk.music.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户勋章与资产关联信息
 */
@Data
@TableName("nft_asset_medal")
public class NftAssetMedal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "wallet_address")
    private String walletAddress;
    @TableField(value = "status")
    private Integer status;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;
}