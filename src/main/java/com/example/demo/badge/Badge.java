package com.example.demo.badge;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.Month;
import java.time.Year;

@Entity
@Table
@Data
@RequiredArgsConstructor
public class Badge implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    private Month month;
    private Year year;
    @Min(1)
    @Max(3)
    private int place;

    public Badge(Month month, Year year, int place) {
        this.month = month;
        this.year = year;
        this.place = place;
    }
}
