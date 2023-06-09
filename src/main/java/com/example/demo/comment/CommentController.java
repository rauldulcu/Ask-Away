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
    public List<CommentDTO> getAllCommentsFromAPost(@RequestParam Long id, @RequestParam Integer pageNumber, @RequestParam Integer elementsPerPage) {
        return commentService.getAllCommentsFromAPost(id,pageNumber,elementsPerPage);
    }

    @PutMapping
    public void updateComment(@RequestBody CommentDTO commentDTO) {
        commentService.updateComment(commentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
    }

    @PutMapping("/chooseAnswer")
    public void setCommentToBestAnswer(@RequestParam Long id) {
        commentService.setCommentToBestAnswer(id);
    }

}
