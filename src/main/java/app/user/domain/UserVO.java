package app.user.domain;


import webserver.request.RequestWrapper;
import webserver.session.Session;

public class UserVO {
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserVO(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public UserVO(RequestWrapper requestWrapper) {
        this.userId = requestWrapper.getParameter("userId");
        this.password = requestWrapper.getParameter("password");
        this.name = requestWrapper.getParameter("name");
        this.email = requestWrapper.getParameter("email");
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    /**
     * 비밀번호가 일치하는지 확인
     */
    public boolean equalsPassword(final String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "JoinUserVO{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
