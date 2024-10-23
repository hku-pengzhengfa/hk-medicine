package com.hk.music.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengzhengfa
 */
@Slf4j
@Configuration
public class TaskConfig {

    @Value("${task.admin}")
    private String adminAddresses;

    @Value("${task.accessToken}")
    private String accessToken;

    @Value("${task.executor.appName}")
    private String appName;

    @Value("${task.executor.port}")
    private int port;

    @Value("${task.executor.logPath}")
    private String logPath;

    @Value("${task.executor.logRetentionDays}")
    private int logRetentionDays;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info("分布式定时任务初始化中......");
        XxlJobSpringExecutor task = new XxlJobSpringExecutor();
        task.setAdminAddresses(adminAddresses);
        task.setAppname(appName);
        task.setPort(port);
        task.setAccessToken(accessToken);
        task.setLogPath(logPath);
        task.setLogRetentionDays(logRetentionDays);
        return task;
    }
}