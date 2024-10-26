package com.hk.music.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.common.core.util.ThreadPoolUtil;
import com.hk.common.core.util.TimeUtils;
import com.hk.music.api.entity.EthTransaction;
import com.hk.music.api.entity.UserRaffleDetails;
import com.hk.music.api.enums.EthTransStatusEnum;
import com.hk.music.api.mapper.EthTransactionMapper;
import com.hk.music.api.mapper.UserRaffleDetailsMapper;
import com.hk.music.api.service.EthTransactionService;
import com.hk.music.exception.HkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import java.math.BigDecimal;

/**
 * eth交易
 *
 * @author pengzhengfa
 */
@Slf4j
@Service
public class EthTransactionServiceImpl extends ServiceImpl<EthTransactionMapper, EthTransaction>
        implements EthTransactionService {

    @Autowired
    private Web3j web3j;

    @Autowired
    private Credentials credentials;

    @Autowired
    private UserRaffleDetailsMapper userRaffleDetailsMapper;

    @Override
    public EthTransaction selectEthTransaction(Long raffleId) {
        LambdaQueryWrapper<EthTransaction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EthTransaction::getRaffleId, raffleId)
                .ne(EthTransaction::getStatus, EthTransStatusEnum.FAIL.getCode());
        return getOne(queryWrapper, false);
    }

    @Override
    public boolean transferEth(Long raffleId, String toAddress, BigDecimal amount) {
        EthTransaction ethTransaction = selectEthTransaction(raffleId);
        if (ethTransaction != null) {
            Integer status = ethTransaction.getStatus();
            if (EthTransStatusEnum.SUCCESS.getCode().equals(status)) {
                throw new HkException(500, "The reward has been distributed.");
            }
            if (EthTransStatusEnum.PROGRESS.getCode().equals(status)) {
                throw new HkException(500, "The reward is currently being distributed, please check again later.");
            }
        }
        ethTransaction = new EthTransaction();
        ethTransaction.setRaffleId(raffleId);
        ethTransaction.setStatus(EthTransStatusEnum.PROGRESS.getCode());
        ethTransaction.setToAddress(toAddress);
        ethTransaction.setFromAddress("");
        ethTransaction.setCreateTime(TimeUtils.currentTime());
        ethTransaction.setUpdateTime(TimeUtils.currentTime());
        this.save(ethTransaction);
        //转账
        final EthTransaction finalEthTransaction = ethTransaction;
        ThreadPoolUtil.execute(() -> {
            TransactionReceipt receipt;
            try {
                log.info("发送转账请求sendFunds：toAddress={}，amount={}", toAddress, amount);
                receipt = Transfer.sendFunds(web3j, credentials, toAddress, amount, Convert.Unit.ETHER).send();
                log.info("响应receipt：{}", JSONObject.toJSONString(receipt));
            } catch (Exception e) {
                log.error("钱包转账异常:", e);
                return;
            }
            //判断是否交易成功
            if (receipt.isStatusOK()) {
                finalEthTransaction.setStatus(EthTransStatusEnum.SUCCESS.getCode());
                finalEthTransaction.setFromAddress(receipt.getFrom());
                finalEthTransaction.setBlockHash(receipt.getBlockHash());
                finalEthTransaction.setBlockNum(receipt.getBlockNumber().longValue());
                finalEthTransaction.setBlockNumRaw(receipt.getBlockNumberRaw());
                finalEthTransaction.setCumulativeGasUsed(receipt.getCumulativeGasUsed().longValue());
                finalEthTransaction.setCumulativeGasUsedRaw(receipt.getCumulativeGasUsedRaw());
                finalEthTransaction.setGasUsed(receipt.getGasUsed().longValue());
                finalEthTransaction.setGasUsedRaw(receipt.getGasUsedRaw());
                finalEthTransaction.setTransactionHash(receipt.getTransactionHash());
                finalEthTransaction.setTransactionIndex(receipt.getTransactionIndex().longValue());
                finalEthTransaction.setTransactionIndexRaw(receipt.getTransactionIndexRaw());
                finalEthTransaction.setUpdateTime(TimeUtils.currentTime());
            } else {
                finalEthTransaction.setStatus(EthTransStatusEnum.FAIL.getCode());
                finalEthTransaction.setUpdateTime(TimeUtils.currentTime());
            }
            this.updateById(finalEthTransaction);
            if (receipt.isStatusOK()) {
                UserRaffleDetails userRaffleDetails = userRaffleDetailsMapper.selectById(raffleId);
                userRaffleDetails.setPayStatus(1);
                userRaffleDetailsMapper.updateById(userRaffleDetails);
            }
        });
        return true;
    }
}
