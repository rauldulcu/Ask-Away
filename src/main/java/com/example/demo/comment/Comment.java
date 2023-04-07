package com.example.demo.comment;

import com.example.demo.User.User;
import com.example.demo.post.Post;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Date;

@Entity
@Table
@Data
@RequiredArgsConstructor
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
    private boolean isBest;
}
