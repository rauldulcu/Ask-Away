package com.example.demo.comment;

import com.example.demo.post.Post;
import com.example.demo.post.PostRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
        if (authorOptional.isPresent() && postOptional.isPresent()) {
            commentDTO.setBestAnswer(false);
            commentRepository.save(commentDTO.convertToComment(postOptional.get(), authorOptional.get()));
        }
    }

    public List<CommentDTO> getAllCommentsFromAPost(Long id, Integer pageNumber, Integer elementsPerPage) {
        Pageable pageable = PageRequest.of(pageNumber,elementsPerPage);
        return (commentRepository.getAllCommentsByPost_Id(id,pageable).stream().map(Comment::convertToDTO).toList());
    }

    public void updateComment(CommentDTO commentDTO) {
        User author = userRepository.findById(commentDTO.getAuthorId()).orElseThrow(() ->
                new EntityNotFoundException("No user with this ID found", HttpStatus.BAD_REQUEST));
        Post post = postRepository.findById(commentDTO.getPostId()).orElseThrow(() ->
                new EntityNotFoundException("No post with this ID found", HttpStatus.BAD_REQUEST));
        commentRepository.save(commentDTO.convertToComment(post, author));
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id))
            throw new EntityNotFoundException("No comment with this ID found", HttpStatus.BAD_REQUEST);
        commentRepository.deleteById(id);
    }

    public void setCommentToBestAnswer(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("No comment with this ID found", HttpStatus.BAD_REQUEST));
        comment.setBestAnswer(true);
        commentRepository.save(comment);
        if (comment.getPost().isRewardedByAuthor())
            comment.getAuthor().addTokens(comment.getPost().getRewardTokens());
        else comment.getAuthor().addTokens(1);
        userRepository.save(comment.getAuthor());
    }
}
