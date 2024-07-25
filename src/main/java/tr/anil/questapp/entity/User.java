package tr.anil.questapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "_user")
public class User {

    @Id
    @GeneratedValue
    Long id;

    String username;
    String password;
    int avatar;


}
