package cn.captainshen.entity;

public class Group {
    private Integer groupid; //群组编号
    private String grouppwd; //群组密码
    private String groupname; //群组名称
    private String groupdesc; //群组描述
    private Integer userid; //创建人用户编号

    public Group(Integer groupid, String grouppwd, String groupname, String groupdesc) {
        this.groupid = groupid;
        this.grouppwd = grouppwd;
        this.groupname = groupname;
        this.groupdesc = groupdesc;
    }

    public Group() {
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getGrouppwd() {
        return grouppwd;
    }

    public void setGrouppwd(String grouppwd) {
        this.grouppwd = grouppwd;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupdesc() {
        return groupdesc;
    }

    public void setGroupdesc(String groupdesc) {
        this.groupdesc = groupdesc;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
