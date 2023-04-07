package com.example.demo.post;

import com.example.demo.User.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table
@Data
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    private String description;
    private String title;
    @ManyToOne
    private User author;
    private boolean isRewardedByAuthor;
    private int rewardTokens;
    private Date date;
    private Time time;
}
