package com.example.demo.badge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/badges")
public class BadgeController {
    private final BadgeService badgeService;

    @Autowired
    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping("/{id}")
    public Badge getBadgeById(@PathVariable("id") Long id) {
        return badgeService.findBadgeById(id);
    }
}
