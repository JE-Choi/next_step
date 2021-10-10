package webserver.controller;

import controller.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpMethod;
import webserver.request.RequestWrapper;
import webserver.servlet.annotation.RequestMapping;
import webserver.servlet.annotation.RestController;

/**
 * https://memostack.tistory.com/161
 */
@RestController(value = "/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/join", method = HttpMethod.POST)
    @RequestMapping(value = "/join", method = HttpMethod.GET)
    public void join(RequestWrapper requestWrapper) {
        // Question: 중복으로 3번찍힘
        LOGGER.debug(requestWrapper.getRequest().getRequestLine().toString());
        LOGGER.info("회원가입 실행중");
    }

    @RequestMapping(value = "/view", method = HttpMethod.GET)
    public void view(RequestWrapper requestWrapper) {
        LOGGER.debug(requestWrapper.getRequest().getRequestLine().toString());
        LOGGER.info("보기 실행중");
    }

    @RequestMapping(value = "/delete", method = HttpMethod.DELETE)
    public void delete(RequestWrapper requestWrapper) {
        LOGGER.debug(requestWrapper.getRequest().getRequestLine().toString());
        LOGGER.info("삭제 실행중");
    }
}
