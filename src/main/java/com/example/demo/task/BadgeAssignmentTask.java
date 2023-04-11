package com.example.demo.task;

import com.example.demo.badge.Badge;
import com.example.demo.badge.BadgeService;
import com.example.demo.season.SeasonService;
import com.example.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BadgeAssignmentTask {
    private final BadgeService badgeService;
    private final SeasonService seasonService;
    private final UserService userService;

    @Autowired
    public BadgeAssignmentTask(BadgeService badgeService, SeasonService seasonService, UserService userService) {
        this.badgeService = badgeService;
        this.seasonService = seasonService;
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 0 1 */3 *")
    public void assignBadges() {
        List<Badge> badgeList = badgeService.createBadges(seasonService.createSeason());
        userService.assignBadges(badgeList);
        userService.resetAllTokens();
    }
}
