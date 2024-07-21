package tr.anil.questapp.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;
import tr.anil.questapp.entity.Like;
import tr.anil.questapp.service.LikeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }


    @GetMapping
    public List<Like> getAllLike(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return likeService.getAllLike(userId, postId);
    }

    @GetMapping("/{likeId}")
    public Like getLikeById(@PathVariable Long likeId) {
        return likeService.getLikeById(likeId);
    }

    @PostMapping
    public Like saveLike(@RequestParam Long userId, @RequestParam Long postId) {
        return likeService.save(userId, postId);
    }

    @DeleteMapping("/likeId")
    public void deleteLikeById(@PathVariable Long likeId) {
        likeService.deleteById(likeId);
    }
}
