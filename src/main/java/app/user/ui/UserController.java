package app.user.ui;

import app.user.Infrastructure.UserDataBase;
import app.user.domain.UserVO;
import app.user.domain.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpMethod;
import webserver.request.HttpRequest;
import webserver.request.RequestWrapper;
import webserver.servlet.annotation.RequestMapping;
import webserver.servlet.annotation.RestController;
import webserver.session.Session;

import java.util.Objects;

/**
 * https://memostack.tistory.com/161
 */
@RestController(value = "/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final UserService USER_SERVICE = new UserService();

    @RequestMapping(value = "/join", method = HttpMethod.POST)
    @RequestMapping(value = "/join", method = HttpMethod.GET)
    public String join(RequestWrapper requestWrapper) {
        USER_SERVICE.join(new UserVO(requestWrapper));
        LOGGER.info("회원가입 실행중");
        return "join 완료";
    }

    @RequestMapping(value = "/view", method = HttpMethod.GET)
    public String view(RequestWrapper requestWrapper) {
        LOGGER.debug(requestWrapper.getRequest().getRequestLine().toString());
        LOGGER.info("보기 실행중");
        return "view 완료";
    }

    @RequestMapping(value = "/delete", method = HttpMethod.DELETE)
    public String delete(RequestWrapper requestWrapper) {
        LOGGER.debug(requestWrapper.getRequest().getRequestLine().toString());
        LOGGER.info("삭제 실행중");
        return "delete 완료";
    }


    @RequestMapping(value = "/login", method = HttpMethod.POST)
    public String login(RequestWrapper requestWrapper) {
        final boolean login = USER_SERVICE.login(new UserVO(requestWrapper), requestWrapper.getSession());
        LOGGER.info(login ? "로그인 성공" : "로그인 실패");
        return login ? "로그인 성공" : "로그인 실패";
    }

    @RequestMapping(value = "/logout", method = HttpMethod.GET)
    public String logout(RequestWrapper requestWrapper) {
        return USER_SERVICE.logout(requestWrapper) ? "로그아웃 성공" : "로그아웃 실패";
    }
}
