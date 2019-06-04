package dewitt.service;

import dewitt.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

public interface UserService {
    int reg(User user);
    User getUser(String username);
    User getUserById(Integer id);
    int updateUserImg(String username,String img);
    int updateUsershort(String username,String myshort);
}
