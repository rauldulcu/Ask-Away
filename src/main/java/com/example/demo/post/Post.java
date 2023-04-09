package com.example.demo.post;

import com.example.demo.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
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

    public PostDTO convertToDTO() {
        return PostDTO.builder()
                .id(this.id)
                .description(this.description)
                .title(this.title)
                .authorId(this.author.getId())
                .isRewardedByAuthor(this.isRewardedByAuthor)
                .rewardTokens(this.rewardTokens)
                .date(this.date)
                .time(this.time)
                .build();
    }
}
