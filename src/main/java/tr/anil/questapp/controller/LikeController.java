package tr.anil.questapp.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.anil.questapp.entity.Like;
import tr.anil.questapp.request.LikeCreateRequest;
import tr.anil.questapp.response.LikeResponse;
import tr.anil.questapp.service.LikeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }


    @GetMapping
    public ResponseEntity<List<LikeResponse>> getAllLike(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return new ResponseEntity<>(likeService.getAllLike(userId, postId).stream().map(p -> new LikeResponse(p)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{likeId}")
    public ResponseEntity<LikeResponse> getLikeById(@PathVariable Long likeId) {
        return new ResponseEntity<>(new LikeResponse(likeService.getLikeById(likeId)),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LikeResponse> saveLike(@RequestBody LikeCreateRequest likeCreateRequest) {
        return new ResponseEntity<>(new LikeResponse(likeService.save(likeCreateRequest)), HttpStatus.OK);
    }

    @DeleteMapping("/{likeId}")
    public void deleteLikeById(@PathVariable Long likeId) {
        likeService.deleteById(likeId);
    }
}
