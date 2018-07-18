package cn.captainshen.service;

import cn.captainshen.entity.User;
import cn.captainshen.enums.FileUploadStatusEnum;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {
    /**
     * 文件上传处理
     * @param user              上传用户
     * @param multipartFile     上传文件
     * @param timeMillis        上传时间
     * @return
     */
    public FileUploadStatusEnum uploadFile(User user, MultipartFile multipartFile, long timeMillis);
}
