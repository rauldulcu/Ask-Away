package com.example.demo.badge;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

@Service
public class BadgeService {
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;

    @Autowired
    public BadgeService(UserRepository userRepository, BadgeRepository badgeRepository) {
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
    }

    public Badge findBadgeById(Long id) {
        return badgeRepository.findBadgeById(id);
    }

    public void assignBadges() {
        List<User> topThreeUsers = userRepository.findTop3ByOrderByTokensDesc();
        for (int i = 0; i < 3; i++) {
            assigningAction(topThreeUsers.get(i), i);
        }
    }

    private void assigningAction(User user, int i) {
        user.addBadge(createBadge(i));
        user.setUserTitle(getTitle(i));
        userRepository.save(user);
    }

    private Badge createBadge(int i) {
        return badgeRepository.save(new Badge(getMonth(), getYear(), i + 1));
    }

    private String getTitle(int place) {
        return getMonth() + " " + getYear() + " #" + (place + 1);
    }

    private Month getMonth() {
        return LocalDate.now().getMonth();
    }

    private Year getYear() {
        return Year.of(LocalDate.now().getYear());
    }
}
