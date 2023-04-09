package com.example.demo.post;

import com.example.demo.user.User;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
public class PostDTO {
    private Long id;
    private String description;
    private String title;
    private Long authorId;
    private boolean isRewardedByAuthor;
    private int rewardTokens;
    private Date date;
    private Time time;

    public Post convertToPost(User author) {
        return Post.builder()
                .id(this.id)
                .description(this.description)
                .title(this.title)
                .author(author)
                .isRewardedByAuthor(this.isRewardedByAuthor)
                .rewardTokens(this.rewardTokens)
                .date(this.date)
                .time(this.time)
                .build();
    }

}
