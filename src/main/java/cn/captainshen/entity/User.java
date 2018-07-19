package cn.captainshen.entity;

public class User {
    private Integer userid; //用户编号
    private String username; //用户名
    private String userpwd; //用户密码
    private String usersex; //用户性别
    private Integer grade; //用户积分
    private String userimage; //用户头像

    public User() {
    }

    public User(Integer userid, String username, String userpwd, String usersex, Integer grade, String userimage) {
        this.userid = userid;
        this.username = username;
        this.userpwd = userpwd;
        this.usersex = usersex;
        this.grade = grade;
        this.userimage = userimage;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }
}
