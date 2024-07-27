package tr.anil.questapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.anil.questapp.entity.RefreshToken;

public interface RefreshTokenDao extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByUserId(Long userId);
}
