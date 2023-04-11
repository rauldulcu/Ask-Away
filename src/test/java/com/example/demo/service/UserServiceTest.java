package com.example.demo.service;

import com.example.demo.badge.Badge;
import com.example.demo.badge.BadgeRepository;
import com.example.demo.comment.CommentRepository;
import com.example.demo.post.PostRepository;
import com.example.demo.season.Season;
import com.example.demo.user.User;
import com.example.demo.user.UserDTO;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;
import com.example.demo.util.Role;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.user.UserService.passwordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static List<UserDTO> userDTOList;
    private static Badge badge;
    private static List<Badge> badgeList;
    private static List<User> userList;
    private static UserDTO userDTO;
    private static User user;
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private BadgeRepository badgeRepository;

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
        userList = new ArrayList<>();
        userDTOList = new ArrayList<>();
        userList.add(user);
        userDTO = user.convertToDTO();
        userDTOList.add(userDTO);
        badge = new Badge();
        badge.setId(1L);
        badgeList = List.of(badge);
    }

    @Test
    public void getAllUsersTest() {
        Pageable pageable = PageRequest.of(0, 5);
        when(userRepository.findAll(pageable)).thenReturn(userList);
        List<UserDTO> response = userService.getAllUsers(0, 5);
        assertEquals(response, userDTOList);
    }

    @Test
    public void testSaveUser() {
        userService.saveUser(userDTO);
        verify(userRepository).save(argThat(user -> Objects.equals(user.getId(), userDTO.getId())));
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);
        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
        verify(postRepository).deleteAllByAuthor_Id(userId);
        verify(commentRepository).deleteAllByAuthor_Id(userId);
    }

    @Test
    public void testDeleteUserNotFound() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(userId));
        verify(userRepository, never()).deleteById(userId);
        verify(postRepository, never()).deleteAllByAuthor_Id(userId);
        verify(commentRepository, never()).deleteAllByAuthor_Id(userId);
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.save(any())).thenReturn(user);
        userService.updateUser(userDTO);
        verify(userRepository, times(1)).save(any());
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getBadgesId(), user.getBadges().stream().map(Badge::getId).collect(Collectors.toList()));
    }

    @Test
    void testAssignBadges() {
        Season season = new Season(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), 1);
        Badge badge1 = new Badge(1L, season, 1);
        Badge badge2 = new Badge(2L, season, 2);
        Badge badge3 = new Badge(3L, season, 3);
        List<Badge> badgeList = List.of(badge1, badge2, badge3);

        User user1 = new User(1L, 100, new ArrayList<>());
        User user2 = new User(2L, 200, new ArrayList<>());
        User user3 = new User(3L, 300, new ArrayList<>());
        List<User> usersWith100Tokens = List.of(user1);
        List<User> usersWith200Tokens = List.of(user2);
        List<User> usersWith300Tokens = List.of(user3);

        when(userRepository.findTop3DistinctTokenValues()).thenReturn(List.of(100, 200, 300));
        when(userRepository.findUsersByTokens(100)).thenReturn(usersWith100Tokens);
        when(userRepository.findUsersByTokens(200)).thenReturn(usersWith200Tokens);
        when(userRepository.findUsersByTokens(300)).thenReturn(usersWith300Tokens);
        when(userRepository.save(user1)).thenReturn(user1);
        when(userRepository.save(user2)).thenReturn(user2);
        when(userRepository.save(user3)).thenReturn(user3);

        userService.assignBadges(badgeList);

        verify(userRepository, times(1)).save(user1);
        verify(userRepository, times(1)).save(user2);
        verify(userRepository, times(1)).save(user3);
    }
}