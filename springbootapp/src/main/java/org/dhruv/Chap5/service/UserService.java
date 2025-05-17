package org.dhruv.Chap5.service;

import org.dhruv.Chap5.annotations.RateLimited;
import org.dhruv.Chap5.annotations.TrackUsage;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @TrackUsage
    @RateLimited
    public String getUserById(Long id) {
        // Simulate business logic
        sleep(200);
        return "User-" + id;
    }

    @TrackUsage
    public void createUser(String name) { 
        // Simulate user creation logic
        sleep(100);
        System.out.println("User created: " + name);
    }

    @TrackUsage
    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}
