package tr.anil.questapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tr.anil.questapp.dao.UserDao;
import tr.anil.questapp.entity.User;
import tr.anil.questapp.response.UserResponse;
import tr.anil.questapp.service.FollowService;
import tr.anil.questapp.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private FollowService followService;

    public UserController(UserService userService, FollowService followService) {
        this.userService = userService;
        this.followService = followService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable Long userId) {
        return new UserResponse(userService.getUser(userId), followService.getFollowedCountByUserId(userId), followService.getFollowerCountByUserId(userId));
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User user) {
        return userService.updateUser(userId,user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getActivityByUser(@PathVariable Long userId) {
        return userService.getActivityByUser(userId);
    }

}
