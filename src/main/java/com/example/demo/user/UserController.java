package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers(@RequestParam Integer pageNumber, @RequestParam Integer elementsPerPage) {
        return userService.getAllUsers(pageNumber,elementsPerPage);
    }

    @PostMapping
    public void saveUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @PutMapping
    public void updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
    }

    @GetMapping("/byEmail")
    public User getUserByEmail(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }

    @GetMapping("/byUsername")
    public User getUserByUserName(@RequestParam String userName) {
        return userService.findUserByUserName(userName);
    }
}
