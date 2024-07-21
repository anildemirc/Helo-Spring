package tr.anil.questapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.anil.questapp.entity.User;

public interface UserDao extends JpaRepository<User, Long> {





}
