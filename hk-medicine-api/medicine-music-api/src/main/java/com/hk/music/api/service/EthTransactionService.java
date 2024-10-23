package com.hk.music.api.service;

import com.hk.music.api.entity.EthTransaction;

import java.math.BigDecimal;

/**
 * @author pengzhengfa
 */
public interface EthTransactionService {

    EthTransaction selectEthTransaction(Long raffleId);

    boolean transferEth(Long raffleId, String toAddress, BigDecimal amount);
}
