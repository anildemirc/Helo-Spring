package tr.anil.questapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.anil.questapp.dao.UserDao;
import tr.anil.questapp.entity.User;
import tr.anil.questapp.security.JwtUserDetails;

@Service
public class UserDetailService implements UserDetailsService {

    private UserDao userDao;

    public UserDetailService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        return JwtUserDetails.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userDao.findById(id).get();
        return JwtUserDetails.create(user);
    }
}
