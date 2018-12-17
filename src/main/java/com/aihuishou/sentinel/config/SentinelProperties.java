package com.aihuishou.sentinel.config;

import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Sentinel Config {@link java.util.Properties}
 *
 * @author jiashuai.xie
 * @since 2018/12/13 18:02
 */
@Configuration
@Data
public class SentinelProperties {

    /**
     * earlier initialize heart-beat when the spring container starts <note> when the
     * transport dependency is on classpath ,the configuration is effective </note>
     */
    @Value("${spring.sentinel.eager:true}")
    private boolean eager = true;

    /**
     * enable sentinel auto configure, the default value is true
     */
    @Value("${spring.sentinel.enabled:true}")
    private boolean enabled = true;

    /**
     * sentinel api port,default value is 8721 {@link TransportConfig#SERVER_PORT}
     */
    @Value("${spring.sentinel.transport.port:8721}")
    private String port = "8721";

    /**
     * sentinel dashboard address, won't try to connect dashboard when address is
     * empty {@link TransportConfig#CONSOLE_SERVER}
     */
    @Value(value = "${spring.sentinel.transport.dashboard:\"\"}")
    private String dashboard = "";

    /**
     * send heartbeat interval millisecond
     * {@link TransportConfig#HEARTBEAT_INTERVAL_MS}
     */
    @Value(value = "${spring.sentinel.transport.heartbeatIntervalMs:\"\"}")
    private String heartbeatIntervalMs;

    /**
     * The process page when the flow control is triggered
     */
    @Value(value = "${spring.sentinel.servlet.page:\"\"}")
    private String blockPage;

    /**
     * log dir
     */
    @Value(value = "${spring.sentinel.log.dir:\"\"}")
    private String dir;

    /**
     * distinguish the log file by pid number
     */
    @Value(value = "${spring.sentinel.log.switch-pid:false}")
    private boolean switchPid = false;

}
