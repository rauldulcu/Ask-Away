package com.example.demo.season;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class SeasonService {
    private static int currentSeason = 0;
    private final SeasonRepository seasonRepository;

    @Autowired
    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    public Season createSeason() {
        currentSeason++;
        Season season = new Season(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(3)), currentSeason);
        return seasonRepository.save(season);
    }
}
