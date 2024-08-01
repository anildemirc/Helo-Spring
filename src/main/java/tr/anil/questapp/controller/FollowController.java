package tr.anil.questapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.anil.questapp.entity.Follow;
import tr.anil.questapp.request.FollowRequest;
import tr.anil.questapp.response.FollowResponse;
import tr.anil.questapp.response.UserResponse;
import tr.anil.questapp.service.FollowService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping
    public FollowResponse getFollow(@RequestParam(value = "followerId") Optional<Long> followerId, @RequestParam(value = "followedId") Optional<Long> followedId) {
        return followService.getFollow(followerId.get(),followedId.get());
    }


    @GetMapping("/getmyfolloweds/{userId}")
    public ResponseEntity<List<UserResponse>> getAllFollowedUserByFollowedUserId(@PathVariable Long userId){
        return new ResponseEntity<>(followService.getFollowListByFollowedUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/getmyfollowers/{userId}")
    public ResponseEntity<List<UserResponse>> getAllFollowerUserByFollowerUserId(@PathVariable Long userId){
        return new ResponseEntity<>(followService.getFollowListByFollowerUserId(userId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<FollowResponse> saveFollow(@RequestBody FollowRequest followRequest){
        return new ResponseEntity<>(followService.save(followRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{followId}")
    public ResponseEntity<String> deleteFollow(@PathVariable Long followId) {
        followService.deleteById(followId);
        return new ResponseEntity<>("Silme işlemi başarılı", HttpStatus.OK);
    }

}
