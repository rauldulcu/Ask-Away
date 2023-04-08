package com.example.demo.User;

import com.example.demo.badge.Badge;
import com.example.demo.util.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private Long id;
    private Role role;
    private String userName;
    private String password;
    private String email;
    private int tokens;
    private List<Long> badgesId;
    private String userTitle;

    public User convertToUser(List<Badge> badges) {
        return User.builder()
                .id(this.id)
                .role(this.role)
                .userName(this.userName)
                .password(this.password)
                .email(this.email)
                .tokens(this.tokens)
                .badges(badges)
                .userTitle(this.userTitle)
                .build();
    }
}

