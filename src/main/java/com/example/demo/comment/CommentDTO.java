package com.example.demo.comment;

import com.example.demo.user.User;
import com.example.demo.post.Post;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Builder
@Data
public class CommentDTO {
    private Long id;
    private String text;
    private Long postId;
    private Long authorId;
    private Date date;
    private Time time;
    private boolean isBestAnswer;

    public Comment convertToComment(Post post, User author) {
        return Comment.builder()
                .id(this.id)
                .text(this.text)
                .post(post)
                .author(author)
                .date(this.date)
                .time(this.time)
                .isBestAnswer(this.isBestAnswer)
                .build();
    }
}
