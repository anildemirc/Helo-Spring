package tr.anil.questapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.RefreshTokenDao;
import tr.anil.questapp.entity.RefreshToken;
import tr.anil.questapp.entity.User;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${questapp.refresh.token.expires.in}")
    private Long expiresSecond;

    private RefreshTokenDao refreshTokenDao;

    public RefreshTokenService(RefreshTokenDao refreshTokenDao) {
        this.refreshTokenDao = refreshTokenDao;
    }

    public boolean isRefreshExpired(RefreshToken token) {
        return token.getExpiryDate().after(new Date());
    }

    public String createRefreshToken(User user) {
        RefreshToken token = refreshTokenDao.findByUserId(user.getId());
        if (token == null)
            token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Date.from(Instant.now().plusSeconds(expiresSecond)));
        refreshTokenDao.save(token);
        return token.getToken();
    }

    public RefreshToken getByUser(Long userId) {
        return refreshTokenDao.findByUserId(userId);
    }
}
