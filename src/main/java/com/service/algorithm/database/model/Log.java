package com.service.algorithm.database.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "log_type")
    private LogType logType;
    private String username;
    @Column(name = "convertation_type")
    private String convertationType;
    private String inner_value;
    private String outer_value;
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    public Log() {
    }

    public Log(String username, String convertationType, String inner_value, String outer_value) {
        this.username = username;
        this.convertationType = convertationType;
        this.inner_value = inner_value;
        this.outer_value = outer_value;
        this.createTime = Timestamp.valueOf(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConvertationType() {
        return convertationType;
    }

    public void setConvertationType(String convertationType) {
        this.convertationType = convertationType;
    }

    public String getInner_value() {
        return inner_value;
    }

    public void setInner_value(String inner_value) {
        this.inner_value = inner_value;
    }

    public String getOuter_value() {
        return outer_value;
    }

    public void setOuter_value(String outer_value) {
        this.outer_value = outer_value;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public enum LogType {
        INFO, ERROR, DEBUG
    }
}
