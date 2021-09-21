package com.cinema.minute.Configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
@Getter@Setter
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class ConfigurationForHeroku {
    @NotNull
    private String url;


}
