package cn.captainshen.service;

import cn.captainshen.entity.LocalFile;
import cn.captainshen.entity.User;
import cn.captainshen.enums.FileUploadStatusEnum;
import org.springframework.cglib.core.Local;
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

    /**
     * 根据文件名关键字模糊搜索共享、已审核文件
     * @param fileName
     * @return
     */
    List<LocalFile> findPublicFilesByFileName(String fileName);

    /**
     * 根据文件Id查询文件
     * @param fileId
     * @return
     */
    LocalFile findFileByFileId(int fileId);

    /**
     * 用户修改文件信息
     * @param fileId
     * @param fileDesc
     * @param fileState
     */
    void updateFileInfo(int fileId, String fileDesc, String fileState);

    /**
     * 删除文件
     * @param fileId
     */
    void deleteFileByFileId(int fileId);

    /**
     * 添加一条下载记录
     * @param userId    下载者id
     * @param fileId    文件id
     */
    void addDownloadRecord(int userId, int fileId);
}
