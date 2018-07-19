package cn.captainshen.dao;

import cn.captainshen.entity.LocalFile;
import org.springframework.cglib.core.Local;
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
}
