package com.es.phoneshop.model.model;

import java.time.LocalDateTime;

public class RequestCount {
    private Long count;
    private LocalDateTime firstRequestTimestamp;

    public RequestCount(Long count, LocalDateTime firstRequestTimestamp) {
        this.count = count;
        this.firstRequestTimestamp = firstRequestTimestamp;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public LocalDateTime getFirstRequestTimestamp() {
        return firstRequestTimestamp;
    }

    public void setFirstRequestTimestamp(LocalDateTime firstRequestTimestamp) {
        this.firstRequestTimestamp = firstRequestTimestamp;
    }
}
