package app.user.Infrastructure;

import java.util.Collection;
import java.util.Map;

import app.user.domain.UserVO;
import com.google.common.collect.Maps;

public class UserDataBase {
    private static Map<String, UserVO> users = Maps.newHashMap();

    public static void addUser(UserVO user) {
        users.put(user.getUserId(), user);
    }

    public static UserVO findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<UserVO> findAll() {
        return users.values();
    }
}
