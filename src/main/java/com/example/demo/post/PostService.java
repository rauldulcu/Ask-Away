package com.example.demo.post;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void savePost(PostDTO postDTO) {
        Optional<User> authorOptional = userRepository.findById(postDTO.getAuthorId());
        if (authorOptional.isPresent() && hasEnoughTokens(authorOptional.get(), postDTO))
            postRepository.save(postDTO.convertToPost(authorOptional.get()));
    }

    private boolean hasEnoughTokens(User author, PostDTO postDTO) {
        return author.getTokens() >= postDTO.getRewardTokens();
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public void updatePost(PostDTO postDTO) {
        Optional<User> authorOptional = userRepository.findById(postDTO.getAuthorId());
        authorOptional.ifPresent(user -> postRepository.save(postDTO.convertToPost(user)));
    }
}
