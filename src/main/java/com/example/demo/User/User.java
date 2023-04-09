package com.example.demo.User;

import com.example.demo.badge.Badge;
import com.example.demo.util.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    private Role role;
    @Column(unique = true)
    private String userName;
    private String password;
    @Email(message = "Must be a valid email address")
    @Column(unique = true)
    private String email;
    private int tokens;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Badge> badges;
    @Nullable
    private String userTitle;

    public void addBadge(Badge badge) {
        this.badges.add(badge);
    }

    public UserDTO convertToDTO() {
        return UserDTO.builder()
                .id(this.id)
                .role(this.role)
                .userName(this.userName)
                .password(this.password)
                .email(this.email)
                .tokens(this.tokens)
                .badgesId(badges.stream().map(Badge::getId).toList())
                .userTitle(this.userTitle)
                .build();
    }
}
