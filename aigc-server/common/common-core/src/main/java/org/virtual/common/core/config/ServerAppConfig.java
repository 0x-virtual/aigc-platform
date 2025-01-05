package org.virtual.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "app")
public class ServerAppConfig {

    private String name;


    private String version;

    private String copyrightYear;

}
