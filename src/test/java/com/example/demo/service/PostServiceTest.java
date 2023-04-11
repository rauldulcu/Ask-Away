package com.example.demo.service;

import com.example.demo.badge.BadgeRepository;
import com.example.demo.post.Post;
import com.example.demo.post.PostDTO;
import com.example.demo.post.PostRepository;
import com.example.demo.post.PostService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.util.Role;
import com.example.demo.util.exception.ConditionsNotMetException;
import com.example.demo.util.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.demo.user.UserService.passwordEncoder;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    private static Post post;
    private static PostDTO postDTO;
    private static List<Post> postList;
    private static User user;
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private BadgeRepository badgeRepository;

    @InjectMocks
    private PostService postService;

    @BeforeAll
    static void init() {
        user = User.builder()
                .id(1L)
                .role(Role.USER)
                .userName("raul01")
                .password(passwordEncoder().encode("1234"))
                .email("raul@gmail.com")
                .tokens(50)
                .badges(Collections.emptyList())
                .userTitle(null)
                .build();
        post = Post.builder()
                .id(1L)
                .description("Description")
                .title("Post Title")
                .author(user)
                .isRewardedByAuthor(true)
                .rewardTokens(20)
                .date(Date.valueOf(LocalDate.now()))
                .time(Time.valueOf(LocalTime.now()))
                .build();
        postList = Arrays.asList(post);
        postDTO = post.convertToDTO();
    }

    @Test
    void getAllPosts_shouldReturnEmptyList_whenNoPostsExist() {
        Pageable pageable = PageRequest.of(0,5);
        when(postRepository.findAll(pageable)).thenReturn(Collections.emptyList());
        List<PostDTO> result = postService.getAllPosts(0,5);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllPosts_shouldReturnListOfPostDTOs_whenPostsExist() {
        Pageable pageable = PageRequest.of(0,5);
        when(postRepository.findAll(pageable)).thenReturn(postList);
        List<PostDTO> result = postService.getAllPosts(0,5);
        assertEquals(1, result.size());
        assertEquals(post.getId(), result.get(0).getId());
        assertEquals(post.getTitle(), result.get(0).getTitle());
    }

    @Test
    void getPostById_shouldReturnPost_whenPostExists() {
        when(postRepository.findPostById(1L)).thenReturn(Optional.of(post));
        Post result = postService.getPostById(1L);
        assertEquals(post.getId(), result.getId());
        assertEquals(post.getTitle(), result.getTitle());
    }

    @Test
    void getPostById_shouldThrowException_whenPostDoesNotExist() {
        when(postRepository.findPostById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> postService.getPostById(1L));
    }

    @Test
    void savePost_shouldSavePostAndDecreaseTokens_whenAuthorHasEnoughTokens() {
        user.setTokens(50);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.save(post)).thenReturn(post);
        postService.savePost(postDTO);
        assertEquals(30L, user.getTokens());
        verify(postRepository).save(post);
        verify(userRepository).save(user);
    }

    @Test
    void savePost_shouldThrowException_whenAuthorHasInsufficientTokens() {
        user.setTokens(10);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertThrows(ConditionsNotMetException.class, () -> postService.savePost(postDTO));
        assertEquals(10L, user.getTokens());
        verify(postRepository, never()).save(post);
        verify(userRepository, never()).save(user);
    }
    @Test
    void savePost_shouldThrowException_whenAuthorNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> postService.savePost(postDTO));
        verify(postRepository, never()).save(post);
        verify(userRepository, never()).save(user);
    }
}

