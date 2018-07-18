package cn.captainshen.entity;

import java.util.Date;

public class LocalFile {
    String fileName;
    // 文件描述
    String fileDesc;
    // 文件标签
    String fileTag;
    // 文件路径
    String filePath;
    // 文件审核状态 0 待审核 1 通过 2 驳回
    int fileCheck;
    // 文件公开状态 0 私密 1 用户所在组可见 2 全部公开
    int fileState;
    // 文件下载次数
    int downloadNum;
    // 文件上传日期
    Date fileDate;
    // 文件类型
    String fileType;
    // 文件上传者id
    int userid;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    public String getFileTag() {
        return fileTag;
    }

    public void setFileTag(String fileTag) {
        this.fileTag = fileTag;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getFileCheck() {
        return fileCheck;
    }

    public void setFileCheck(int fileCheck) {
        this.fileCheck = fileCheck;
    }

    public int getFileState() {
        return fileState;
    }

    public void setFileState(int fileState) {
        this.fileState = fileState;
    }

    public int getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(int downloadNum) {
        this.downloadNum = downloadNum;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}

