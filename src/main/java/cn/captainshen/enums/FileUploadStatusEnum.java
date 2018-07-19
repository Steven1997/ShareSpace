package cn.captainshen.enums;

public enum FileUploadStatusEnum {
    SUCCESS("上传成功"),
    FILE_NUM_EXCEEDED("文件数量超限"),
    FILE_SIZE_EXCEEDED("文件大小超限"),
    UNKNOWN_FAILURE("未知错误"),
    SQL_ERROR("数据库写入错误");
    /**
     * 状态信息
     */
    private String statusInfo;
    FileUploadStatusEnum(String statusInfo){
        this.statusInfo = statusInfo;
    }

    public String getStatusInfo() {
        return statusInfo;
    }
}
