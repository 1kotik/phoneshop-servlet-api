package com.es.phoneshop.model.services;

import com.es.phoneshop.model.model.RequestCount;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDosProtectionService implements DosProtectionService {
    private static DefaultDosProtectionService dosProtectionService;
    private Map<String, RequestCount> requestCountFromIp;
    private static final Long MAX_REQUESTS_PER_MINUTE = 100L;

    public static synchronized DefaultDosProtectionService getInstance() {
        if (dosProtectionService == null) {
            dosProtectionService = new DefaultDosProtectionService();
        }
        return dosProtectionService;
    }

    private DefaultDosProtectionService() {
        requestCountFromIp = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isAllowed(String ip, LocalDateTime requestTimestamp) {
        if (requestCountFromIp.containsKey(ip)) {
            return isCustomerExceededMaxAllowedRequests(ip, requestTimestamp);
        }
        requestCountFromIp.put(ip, new RequestCount(1L, requestTimestamp));
        return true;
    }

    private boolean isCustomerExceededMaxAllowedRequests(String ip, LocalDateTime requestTimestamp) {
        RequestCount requestCount = requestCountFromIp.get(ip);
        long secondsBetweenFirstAndCurrentRequest = Duration.between(requestCount.getFirstRequestTimestamp(),
                requestTimestamp).toSeconds();
        if (secondsBetweenFirstAndCurrentRequest > 60) {
            requestCountFromIp.remove(ip);
            return true;
        } else if (requestCount.getCount() > MAX_REQUESTS_PER_MINUTE) {
            return false;
        } else {
            requestCount.setCount(requestCount.getCount() + 1);
            return true;
        }
    }

}
