package cn.captainshen.service;

import cn.captainshen.entity.LocalFile;
import cn.captainshen.entity.User;
import cn.captainshen.enums.FileUploadStatusEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;


public interface FileService {
    /**
     * 文件上传处理
     * @param user              上传用户
     * @param multipartFile     上传文件
     * @param timeMillis        上传时间
     * @param fileDesc          文件描述
     * @param fileState         文件可见状态
     * @return
     */
    FileUploadStatusEnum uploadFile(User user, MultipartFile multipartFile,
                                           long timeMillis, String fileDesc, String fileState);

    /**
     * 根据文件名查询文件
     * @param fileName
     * @return
     */
    List<LocalFile> findFileByName(String fileName);

    /**
     * 根据描述查询文件
     * @param fileDesc
     * @return
     */
    List<LocalFile> findFileByDesc(String fileDesc);

    /**
     * 根据用户id查询文件
     * @param userId
     * @return
     */
    List<LocalFile> findFileByUserId(int userId);

    /**
     * 查询用户已下载文件
     * @param userId
     * @return
     */
    List<LocalFile> findDownloadedFileByUserId(int userId);

    /**
     * 根据文件id获取真实文件
     * @param fileId
     * @return
     */
    File findRealFileByFileId(int fileId);

    /**
     * 判断用户下载权限
     * @param user
     * @param localFile
     * @return true 可下载 false 不可下载
     */
    Boolean checkDownloadable(User user, LocalFile localFile);
}
