package com.nur.scheduler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;


@Configuration
@ConfigurationProperties("data-retention")
public class DataRetentionConfig {
    private String retentionPeriod;
    private String adm100;
    private String adm150;
    private String isy100;

    public Duration getRetentionPeriod() {
        return Duration.parse(retentionPeriod);
    }

    public void setRetentionPeriod(String retentionPeriod) {
        this.retentionPeriod = retentionPeriod;
    }

    public Duration getAdm100() {
        return Duration.parse(adm100);
    }

    public void setAdm100(String adm100) {
        this.adm100 = adm100;
    }

    public Duration getAdm150() {
        return Duration.parse(adm150);
    }

    public void setAdm150(String adm150) {
        this.adm150 = adm150;
    }

    public Duration getIsy100() {
        return Duration.parse(isy100);
    }

    public void setIsy100(String isy100) {
        this.isy100 = isy100;
    }
}
