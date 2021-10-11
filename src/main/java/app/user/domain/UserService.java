package app.user.domain;

import app.user.Infrastructure.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void join(JoinUserVO joinUserVO) {
        DataBase.addUser(joinUserVO);
        logger.debug("새로운 user 추가됨. >> {}", joinUserVO.toString());
    }
}
