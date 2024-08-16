package tr.anil.questapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody User user) {
        return new ResponseEntity<>(new UserResponse(userService.saveUser(user)), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        return new ResponseEntity<>(new UserResponse(user, user.getFollowingCount(), user.getFollowerCount()), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestBody User user) {
        return new ResponseEntity<>(new UserResponse(userService.updateUser(userId,user)), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/activity/{userId}")
    public ResponseEntity<List<Object>> getActivityByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getActivityByUser(userId), HttpStatus.OK);
    }

}
