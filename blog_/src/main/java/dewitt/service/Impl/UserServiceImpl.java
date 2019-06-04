package dewitt.service.Impl;

import dewitt.dao.UserDao;
import dewitt.entity.User;
import dewitt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public int reg(User user) {
        User loadUserByUsername = userDao.loadUserByUsername(user.getUsername());
        if (loadUserByUsername != null) return 1;
        user.setPassword("{noop}" + user.getPassword());
        user.setEnabled(1);
        int uresult = userDao.reg(user);
        int rresult = userDao.setUserRole(user.getUsername(), "ROLE_USER");
        if (uresult==1&&rresult==1)return 0;
        else return 2;
    }

    @Override
    public User getUser(String username) {
        return userDao.getUser(username);
    }



    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public int updateUserImg(String username,String img) {
        return userDao.updateUserimg(username,img);
    }

    @Override
    public int updateUsershort(String username, String myshort) {
        return userDao.updateUsershort(username,myshort);
    }
}
