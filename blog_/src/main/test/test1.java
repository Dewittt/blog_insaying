import dewitt.dao.UserDao;
import dewitt.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/*.xml","/mapper/*.xml","/mybatis-config.xml"})
@WebAppConfiguration("src/main/resources")
public class test1 {
    @Autowired
    private UserDao userDao;


}
