package com.example.demo.comment;

import com.example.demo.post.Post;
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
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    private String text;
    @ManyToOne
    private Post post;
    @OneToOne
    private User author;
    private Date date;
    private Time time;

    private boolean isBestAnswer;

    public CommentDTO convertToDTO() {
        return CommentDTO.builder()
                .id(this.id)
                .text(this.text)
                .postId(this.post.getId())
                .authorId(this.author.getId())
                .date(this.date)
                .time(this.time)
                .isBestAnswer(this.isBestAnswer)
                .build();
    }
}
