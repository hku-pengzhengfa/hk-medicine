package com.hk.music.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * @author pengzhengfa
 */
@Configuration
public class BlockchainConfig {

    @Value("${chain.infra.url}")
    private String infraUrl;

    @Value("${chain.privateKey}")
    private String privateKey;

    @Bean
    public Web3j chainConfig() {
        return Web3j.build(new HttpService(infraUrl));
    }

    @Bean
    public Credentials credentials() {
        return Credentials.create(privateKey);
    }
}
