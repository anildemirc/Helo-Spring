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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping
    public ResponseEntity<FollowResponse> getFollow(@RequestParam(value = "userId") Optional<Long> userId, @RequestParam(value = "followerId") Optional<Long> followerId) {
        Follow follow = followService.getFollow(userId.get().longValue(),followerId.get().longValue());
        return new ResponseEntity<>(follow != null ? new FollowResponse(follow):null, HttpStatus.OK);
    }


    @GetMapping("/getmyfollowings/{userId}")
    public ResponseEntity<List<UserResponse>> getAllFollowedUserByFollowingUserId(@PathVariable Long userId){
        return new ResponseEntity<>(followService.getFollowListByFollowingUserId(userId).stream().map(p -> new UserResponse(p)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/getmyfollowers/{userId}")
    public ResponseEntity<List<UserResponse>> getAllFollowerUserByFollowerUserId(@PathVariable Long userId){
        return new ResponseEntity<>(followService.getFollowListByFollowerUserId(userId).stream().map(p -> new UserResponse(p)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<FollowResponse> saveFollow(@RequestBody FollowRequest followRequest){
        return new ResponseEntity<>(new FollowResponse(followService.save(followRequest)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{followId}")
    public ResponseEntity<String> deleteFollow(@PathVariable Long followId) {
        followService.deleteById(followId);
        return new ResponseEntity<>("Silme işlemi başarılı", HttpStatus.OK);
    }

}
