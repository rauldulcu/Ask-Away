package com.example.demo.comment;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import com.example.demo.post.Post;
import com.example.demo.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public void saveComment(CommentDTO commentDTO) {
        Optional<User> authorOptional = userRepository.findById(commentDTO.getAuthorId());
        Optional<Post> postOptional = postRepository.findById(commentDTO.getPostId());
        if (authorOptional.isPresent() && postOptional.isPresent())
            commentRepository.save(commentDTO.convertToComment(postOptional.get(), authorOptional.get()));
    }

    public List<CommentDTO> getAllCommentsFromAPost(Long id) {
        return (commentRepository.getAllCommentsByPost_Id(id).stream().map(Comment::convertToDTO).toList());
    }

    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }
}
