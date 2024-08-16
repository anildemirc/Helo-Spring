package tr.anil.questapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "_user")
public class User {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, unique = true)
    String username;
    @Column(nullable = false)
    String password;

    int followingCount;
    int followerCount;
    int avatar;
}
