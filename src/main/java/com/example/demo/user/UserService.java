package com.example.demo.user;

import com.example.demo.badge.Badge;
import com.example.demo.badge.BadgeRepository;
import com.example.demo.comment.CommentRepository;
import com.example.demo.post.PostRepository;
import com.example.demo.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final BadgeRepository badgeRepository;

    @Autowired
    public UserService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, BadgeRepository badgeRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.badgeRepository = badgeRepository;
    }

    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    public List<UserDTO> getAllUsers() {
        List<User> list = (List<User>) userRepository.findAll();
        return list.stream().map(User::convertToDTO).toList();
    }

    public void saveUser(UserDTO userDTO) {
        User user = new User();
        userRepository.save(user.createAccount(userDTO));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new EntityNotFoundException("No user with this ID found", HttpStatus.BAD_REQUEST);
        postRepository.deleteAllByAuthor_Id(id);
        commentRepository.deleteAllByAuthor_Id(id);
        userRepository.deleteById(id);
    }

    public void updateUser(UserDTO userDTO) {
        if (!userRepository.existsById(userDTO.getId()))
            throw new EntityNotFoundException("No user with this ID found", HttpStatus.BAD_REQUEST);
        List<Badge> badgesList = new ArrayList<>();
        userDTO.getBadgesId().forEach(badgeId -> {
                    Optional<Badge> badgeOptional = badgeRepository.findById(badgeId);
                    badgeOptional.ifPresent(badgesList::add);
                }
        );
        userRepository.save(userDTO.convertToUser(badgesList));
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("No user found with this email", HttpStatus.BAD_REQUEST));
    }

    public void resetAllTokens() {
        userRepository.findAll().forEach(user -> {
            user.setTokens(0);
            userRepository.save(user);
        });
    }

    public List<Integer> findUniqueTokenValues() {
        return userRepository.findTop3DistinctTokenValues();
    }

    public void assignBadges(List<Badge> badgeList) {
        List<Integer> top3Tokens = userRepository.findTop3DistinctTokenValues();
        for (int i = 0; i < top3Tokens.size(); i++) {
            Badge badge = badgeList.get(i);
            userRepository.findUsersByTokens(top3Tokens.get(i)).forEach(user -> {
                assigningAction(user, badge);
            });
        }
    }

    private void assigningAction(User user, Badge badge) {
        user.addBadge(badge);
        user.setUserTitle(getTitle(badge.getPlace(), badge.getSeason().getSeasonNumber()));
        userRepository.save(user);
    }

    private String getTitle(int place, int seasonNumber) {
        return "Season " + seasonNumber + " #" + place;
    }

    public User findUserByUserName(String userName) {
        return userRepository.findUserByEmail(userName).orElseThrow(() ->
                new EntityNotFoundException("No user found with this username", HttpStatus.BAD_REQUEST));
    }
}
