package com.nur.scheduler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;


@Configuration
@ConfigurationProperties("data-retention")
@EnableScheduling
public class DataRetentionConfig {
    private long retentionPeriod;
    private long adm100;
    private long adm150;
    private long isy100;

    public long getRetentionPeriod() {
        return retentionPeriod;
    }

    public void setRetentionPeriod(long retentionPeriod) {
        this.retentionPeriod = retentionPeriod;
    }

    public long getAdm100() {
        return adm100;
    }

    public void setAdm100(long adm100) {
        this.adm100 = adm100;
    }

    public long getAdm150() {
        return adm150;
    }

    public void setAdm150(long adm150) {
        this.adm150 = adm150;
    }

    public long getIsy100() {
        return isy100;
    }

    public void setIsy100(long isy100) {
        this.isy100 = isy100;
    }
}
