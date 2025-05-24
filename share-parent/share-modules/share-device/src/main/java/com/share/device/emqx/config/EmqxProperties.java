package com.share.device.emqx.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "emqx.client")
public class EmqxProperties {

    private String clientId;
    private String username;
    private String password;
    private String serverURI;
    private int keepAliveInterval;
    private int connectionTimeout;
}