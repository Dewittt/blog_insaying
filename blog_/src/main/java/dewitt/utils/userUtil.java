package dewitt.utils;

import dewitt.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class userUtil {
    public static Authentication getCurrentUser() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        return user;
    }
}
