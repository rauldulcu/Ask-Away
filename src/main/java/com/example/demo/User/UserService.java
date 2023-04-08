package com.example.demo.User;

import com.example.demo.badge.Badge;
import com.example.demo.badge.BadgeRepository;
import com.example.demo.util.Role;
import com.example.demo.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;

    @Autowired
    public UserService(UserRepository userRepository, BadgeRepository badgeRepository) {
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<User> list = (List<User>) userRepository.findAll();
        return list.stream().map(User::convertToDTO).toList();
    }

    public void saveUser(UserDTO userDTO) {
        List<Badge> badgesList = new ArrayList<>();
        userDTO.getBadgesId().forEach(badgeId -> {
                    Optional<Badge> badgeOptional = badgeRepository.findById(badgeId);
                    badgeOptional.ifPresent(badgesList::add);
                }
        );
        userDTO.setRole(Role.USER);
        userRepository.save(userDTO.convertToUser(badgesList));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("No user found with this email", HttpStatus.BAD_REQUEST));
    }
}
