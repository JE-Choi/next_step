package app.user.Infrastructure;

import java.util.Collection;
import java.util.Map;

import app.user.domain.JoinUserVO;
import com.google.common.collect.Maps;

public class DataBase {
    private static Map<String, JoinUserVO> users = Maps.newHashMap();

    public static void addUser(JoinUserVO joinUser) {
        users.put(joinUser.getUserId(), joinUser);
    }

    public static JoinUserVO findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<JoinUserVO> findAll() {
        return users.values();
    }
}
