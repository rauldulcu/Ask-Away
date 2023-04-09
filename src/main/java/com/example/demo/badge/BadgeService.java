package com.example.demo.badge;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import com.example.demo.season.Season;
import com.example.demo.season.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BadgeService {
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final SeasonRepository seasonRepository;

    @Autowired
    public BadgeService(UserRepository userRepository, BadgeRepository badgeRepository, SeasonRepository seasonRepository) {
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
        this.seasonRepository = seasonRepository;
    }

    public Badge findBadgeById(Long id) {
        return badgeRepository.findBadgeById(id);
    }

    public List<Badge> createBadges(Season season) {
        List<Badge> badges = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Badge badge = badgeRepository.save(new Badge(season, i));
            badges.add(badge);

        }

        return badges;
    }
}
