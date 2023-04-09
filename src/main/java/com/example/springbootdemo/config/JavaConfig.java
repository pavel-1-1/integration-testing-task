package com.example.springbootdemo.config;

import com.example.springbootdemo.systemProfile.DevProfile;
import com.example.springbootdemo.systemProfile.ProductionProfile;
import com.example.springbootdemo.systemProfile.SystemProfile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties(prefix = "mail")
public class JavaConfig {

    @Bean
    @ConditionalOnProperty(value = "mail.dev", havingValue = "true")
    public SystemProfile devProfile() {
        return new DevProfile();
    }

    @Bean
    @ConditionalOnProperty(value = "mail.dev", havingValue = "false")
    public SystemProfile prodProfile() {
        return new ProductionProfile();
    }
}
