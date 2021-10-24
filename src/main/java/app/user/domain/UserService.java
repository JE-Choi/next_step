package app.user.domain;

import app.user.Infrastructure.UserDataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.RequestWrapper;
import webserver.session.Session;

import java.util.Objects;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void join(UserVO userVO) {
        UserDataBase.addUser(userVO);
        logger.debug("새로운 user 추가됨. >> {}", userVO.toString());
    }

    public boolean login(UserVO userVO, Session session) {
        final UserVO userById = UserDataBase.findUserById(userVO.getUserId());
        if (Objects.nonNull(userById)) {
            if (userById.equalsPassword(userVO.getPassword())) {
                session.setAttribute("user", userById);
                return true;
            }
        }
        return false;
    }

    public boolean logout(RequestWrapper requestWrapper) {
        if (requestWrapper.isLogined()) {
            requestWrapper.getSession().removeAttribute("user");
            return true;
        }
        return false;
    }
}
