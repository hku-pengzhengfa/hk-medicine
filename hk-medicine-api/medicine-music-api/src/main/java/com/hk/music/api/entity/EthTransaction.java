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
@TableName("eth_transaction")
public class EthTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "raffle_id")
    private Long raffleId;

    @TableField(value = "block_hash")
    private String blockHash;

    @TableField(value = "block_num")
    private Long blockNum;

    @TableField(value = "block_num_raw")
    private String blockNumRaw;

    @TableField(value = "cumulative_gas_used")
    private Long cumulativeGasUsed;

    @TableField(value = "cumulative_gas_used_raw")
    private String cumulativeGasUsedRaw;

    @TableField(value = "from_address")
    private String fromAddress;

    @TableField(value = "gas_used")
    private Long gasUsed;

    @TableField(value = "gas_used_raw")
    private String gasUsedRaw;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "to_address")
    private String toAddress;

    @TableField(value = "transaction_hash")
    private String transactionHash;

    @TableField(value = "transaction_index")
    private Long transactionIndex;

    @TableField(value = "transaction_index_raw")
    private String transactionIndexRaw;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;
}
