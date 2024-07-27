package tr.anil.questapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "_follow")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "follower_user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    User followerUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "followed_user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    User followedUser;
}
