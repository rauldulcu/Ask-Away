package com.example.demo.User;

import com.example.demo.util.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Data
@RequiredArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    @ElementCollection(targetClass = Role.class)
    private List<Role> role;
    private String userName;
    private String password;
    @Email(message = "Must be a valid email address")
    @Column(unique = true)
    private String email;
    private int tokens;
}
