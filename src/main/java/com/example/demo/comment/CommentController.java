package com.example.demo.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public void saveComment(@RequestBody CommentDTO commentDTO) {
        commentService.saveComment(commentDTO);
    }

    @GetMapping("/byPost")
    public List<CommentDTO> getAllCommentsFromAPost(@RequestParam Long id) {
        return commentService.getAllCommentsFromAPost(id);
    }

    @PutMapping
    public void updateComment(@RequestBody Comment comment) {
        commentService.updateComment(comment);
    }
}
