package app.user.domain;


import webserver.request.RequestWrapper;

public class JoinUserVO {
    private String userId;
    private String password;
    private String name;
    private String email;

    public JoinUserVO(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public JoinUserVO(RequestWrapper requestWrapper){
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
