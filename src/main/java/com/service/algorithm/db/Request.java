package com.service.algorithm.db;

public class Request {
    private Long id;
    private Long userId;
    private String type;
    private String value;

    public Request(Long id, Long userId, String type, String value) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

}