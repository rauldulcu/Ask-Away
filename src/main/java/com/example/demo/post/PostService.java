package com.example.demo.post;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import com.example.demo.util.exception.ConditionsNotMetException;
import com.example.demo.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<PostDTO> getAllPosts() {
        List<Post> list = (List<Post>) postRepository.findAll();
        return list.stream().map(Post::convertToDTO).toList();
    }

    public Post getPostById(Long id) {
        Optional<Post> postOptional = postRepository.findPostById(id);
        if (postOptional.isPresent()) return postOptional.get();
        else throw new EntityNotFoundException("No post found with this ID", HttpStatus.BAD_REQUEST);
    }

    public void savePost(PostDTO postDTO) {
        User author = userRepository.findById(postDTO.getAuthorId()).orElseThrow(() ->
                new EntityNotFoundException("No user with this ID found", HttpStatus.BAD_REQUEST));
        if (!hasEnoughTokens(author, postDTO))
            throw new ConditionsNotMetException("Not enough tokens", HttpStatus.BAD_REQUEST);
        postRepository.save(postDTO.convertToPost(author));
        author.setTokens(author.getTokens() - postDTO.getRewardTokens());
        userRepository.save(author);
    }

    private boolean hasEnoughTokens(User author, PostDTO postDTO) {
        return author.getTokens() >= postDTO.getRewardTokens();
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id))
            throw new EntityNotFoundException("No post with this ID found", HttpStatus.BAD_REQUEST);
        postRepository.deleteById(id);
    }

    public void updatePost(PostDTO postDTO) {
        User author = userRepository.findById(postDTO.getAuthorId()).orElseThrow(() ->
                new EntityNotFoundException("No user with this ID found", HttpStatus.BAD_REQUEST));
        postRepository.save(postDTO.convertToPost(author));
    }
}
