package dewitt.dao;

import dewitt.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface UserDao {
    User loadUserByUsername(@Param("username") String username);//ok
    int reg(User user);//ok
    int deleteUserRole(String username);//ok
    int setUserRole(@Param("username") String username,@Param("authority") String authority);//ok
    User getUser(@Param("username") String username);//ok
    int delete(@Param("username") String username);//ok
    User getUserById(@Param("id") Integer id);//ok
    int updateUserimg(@Param("username") String username,@Param("img") String img);//ok
    int updateUsershort(@Param("username") String username,@Param("myshort") String myshort);
}
