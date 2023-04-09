package com.example.demo.season;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table
@NoArgsConstructor
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seasonId;
    private int seasonNumber;
    private Date seasonStartDate;
    private Date seasonEndDate;

    public Season(Date seasonStartDate, Date seasonEndDate, int seasonNumber) {
        this.seasonStartDate = seasonStartDate;
        this.seasonEndDate = seasonEndDate;
        this.seasonNumber = seasonNumber;
    }
}
