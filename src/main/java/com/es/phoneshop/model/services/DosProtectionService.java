package com.es.phoneshop.model.services;

import java.time.LocalDateTime;

public interface DosProtectionService {
    boolean isAllowed(String ip, LocalDateTime requestTimestamp);
}

