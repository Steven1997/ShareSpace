package cn.captainshen.dao;

import cn.captainshen.entity.LocalFile;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileDao {
    /**
     * 添加文件
     * @param file
     * @return
     */
    int addFile(LocalFile file);

    /**
     * 根据文件描述模糊查询
     * @param fileDesc
     * @return
     */
    List<LocalFile> findFileByDesc(String fileDesc);

    /**
     * 根据文件名模糊查询
     * @param fileName
     * @return
     */
    List<LocalFile> findFileByName(String fileName);

    /**
     * 根据文件id查询
     * @param fileId
     * @return
     */
    LocalFile findFileByFileId(int fileId);
    /**
     * 查询用户文件
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
     * 模糊搜索公开文件
     * @param fileName
     * @return
     */
    List<LocalFile> findPublicFilesByFileName(String fileName);

    /**
     * 修改文件信息
     * @param fileId
     * @param fileDesc
     * @param fileState
     */
    void updateFileInfo(int fileId, String fileDesc, int fileState);

    /**
     * 删除文件
     * @param fileId
     */
    void deleteFileByFileId(int fileId);

    /**
     * 添加下载记录
     * @param file
     */
    void addDownloadRecord(LocalFile file);
}
