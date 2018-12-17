package com.aihuishou.sentinel.config;

import com.aihuishou.sentinel.servlet.callback.CustomizerRequestOriginParser;
import com.aihuishou.sentinel.servlet.callback.CustomizerUrlBlockHandler;
import com.aihuishou.sentinel.servlet.callback.CustomizerUrlCleaner;
import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.adapter.servlet.config.WebServletConfig;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.init.InitExecutor;
import com.alibaba.csp.sentinel.log.LogBase;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * Sentinel Configuration
 *
 * @author jiashuai.xie
 * @since 2018/12/13 17:25
 */
@Configuration
@Slf4j
public class SentinelConfig {

    @Value("${project.name:${spring.application.name}}")
    private String projectName;

    @Autowired
    private SentinelProperties properties;

    @Autowired(required = false)
    private UrlBlockHandler urlBlockHandler;

    @Autowired(required = false)
    private UrlCleaner urlCleaner;

    @Autowired(required = false)
    private RequestOriginParser requestOriginParser;

    static {
        WebCallbackManager.setUrlBlockHandler(new CustomizerUrlBlockHandler());
        WebCallbackManager.setUrlCleaner(new CustomizerUrlCleaner());
        WebCallbackManager.setRequestOriginParser(new CustomizerRequestOriginParser());
    }

    @PostConstruct
    private void init() {
        if (StringUtils.isEmpty(System.getProperty(AppNameUtil.APP_NAME))
                && StringUtils.hasText(projectName)) {
            System.setProperty(AppNameUtil.APP_NAME, projectName);
        }
        if (StringUtils.isEmpty(System.getProperty(TransportConfig.SERVER_PORT))
                && StringUtils.hasText(properties.getPort())) {
            System.setProperty(TransportConfig.SERVER_PORT,
                    properties.getPort());
        }
        if (StringUtils.isEmpty(System.getProperty(TransportConfig.CONSOLE_SERVER))
                && StringUtils.hasText(properties.getDashboard())) {
            System.setProperty(TransportConfig.CONSOLE_SERVER,
                    properties.getDashboard());
        }
        if (StringUtils.isEmpty(System.getProperty(TransportConfig.HEARTBEAT_INTERVAL_MS))
                && StringUtils
                .hasText(properties.getHeartbeatIntervalMs())) {
            System.setProperty(TransportConfig.HEARTBEAT_INTERVAL_MS,
                    properties.getHeartbeatIntervalMs());
        }

        if (StringUtils.hasText(properties.getBlockPage())) {
            WebServletConfig.setBlockPage(properties.getBlockPage());
        }
        if (StringUtils.isEmpty(System.getProperty(LogBase.LOG_DIR))
                && StringUtils.hasText(properties.getDir())) {
            System.setProperty(LogBase.LOG_DIR, properties.getDir());
        }
        if (StringUtils.isEmpty(System.getProperty(LogBase.LOG_NAME_USE_PID))
                && properties.isSwitchPid()) {
            System.setProperty(LogBase.LOG_NAME_USE_PID,
                    String.valueOf(properties.isSwitchPid()));
        }

        if (null != urlBlockHandler) {
            WebCallbackManager.setUrlBlockHandler(urlBlockHandler);
        }


        if (null != urlCleaner) {
            WebCallbackManager.setUrlCleaner(urlCleaner);
        }

        if (null != requestOriginParser) {
            WebCallbackManager.setRequestOriginParser(requestOriginParser);
        }

        // earlier initialize
        if (properties.isEager()) {
            InitExecutor.doInit();
        }

    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }


}
