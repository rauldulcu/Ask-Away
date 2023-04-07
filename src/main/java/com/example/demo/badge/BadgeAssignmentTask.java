package com.example.demo.badge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BadgeAssignmentTask {
    private final BadgeService badgeService;

    @Autowired
    public BadgeAssignmentTask(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @Scheduled(cron = "0 45 * * * ?")
    public void assignBadges() {
        badgeService.assignBadges();
    }
}
