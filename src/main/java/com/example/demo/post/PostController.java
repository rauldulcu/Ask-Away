package com.example.demo.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/byId")
    public Post getPostById(@RequestParam Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public void savePost(@RequestBody PostDTO postDTO) {
        postService.savePost(postDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
    }

    @PutMapping
    public void updatePost(@RequestBody PostDTO postDTO) {
        postService.updatePost(postDTO);
    }

}
