package com.example.demo.security;

import com.example.demo.post.Post;
import com.example.demo.post.PostRepository;
import com.example.demo.user.UserRepository;
import com.example.demo.util.Role;
import com.example.demo.util.exception.EntityNotFoundException;
import com.example.demo.util.exception.ForbiddenActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Component("postAuthorAccessControl")
public class PostAuthorAccessControl {
    private final PostRepository postRepository;

    @Autowired
    public PostAuthorAccessControl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public boolean checkPostAuthor(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedUserAuthority = authentication.getAuthorities().toString();
        if (!loggedUserAuthority.substring(1, loggedUserAuthority.length() - 1).equals(Role.USER.toString())) {
            return true;
        }
        String loggedUserUsername = authentication.getName();
        System.out.println(postId);
        Optional<Post> postOptional = postRepository.findPostById(postId);
        if (postOptional.isPresent()) {
            String authorUsername = postOptional.get().getAuthor().getUserName();
            return loggedUserUsername.equals(authorUsername);
        } else throw new EntityNotFoundException("No post with this ID found", HttpStatus.BAD_REQUEST);
    }
}