package org.example.ingtest.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

//Pentru referinta: https://www.baeldung.com/spring-security-block-brute-force-authentication-attempts

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5;
    private static final Duration BLOCK_DURATION = Duration.ofMinutes(5);

    private final HttpServletRequest request;
    private final LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService(HttpServletRequest request) {
        this.request = request;
        this.attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(BLOCK_DURATION)
                .build(new CacheLoader<>() {
                    @Override
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public void loginSucceeded() {
        attemptsCache.invalidate(getClientIP());
    }

    public void loginFailed() {
        String key = getClientIP();
        int attempts;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException ex) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked() {
        try {
            return attemptsCache.get(getClientIP()) >= MAX_ATTEMPTS;
        } catch (ExecutionException ex) {
            return false;
        }
    }

    public long getBlockDurationSecodns() {
        return BLOCK_DURATION.getSeconds();
    }

    private String getClientIP() {
        return request.getRemoteAddr();
    }
}
