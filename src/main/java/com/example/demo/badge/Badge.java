package com.example.demo.badge;

import com.example.demo.season.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Badge implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    @ManyToOne
    private Season season;
    @Min(1)
    @Max(3)
    private int place;

    public Badge(Season season, int place) {
        this.season = season;
        this.place = place;
    }
}
