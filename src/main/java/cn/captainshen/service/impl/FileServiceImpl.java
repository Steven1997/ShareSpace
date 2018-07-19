package cn.captainshen.service.impl;

import cn.captainshen.dao.FileDao;
import cn.captainshen.entity.LocalFile;
import cn.captainshen.entity.User;
import cn.captainshen.enums.FileUploadStatusEnum;
import cn.captainshen.service.FileService;
import cn.captainshen.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;


@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private FileDao fileDao;

    @Override
    public FileUploadStatusEnum uploadFile(User user, MultipartFile multipartFile,
                                           long timeMillis, String fileDesc, String fileState) {
        FileUploadStatusEnum saveStatus = fileUtil.saveUploadFile(user.getUsername(), multipartFile, timeMillis);
        // 文件本地保存失败
        if(!(saveStatus == FileUploadStatusEnum.SUCCESS)){
            return saveStatus;
        }
        //文件信息入库
        String fileName = multipartFile.getOriginalFilename();
        Date fileDate = new Date(timeMillis);
        LocalFile file = new LocalFile();
        int state = 0;
        file.setFileName(fileName);
        file.setFileDesc(fileDesc);
        file.setFileDate(fileDate);
        file.setFilePath(fileUtil.getFileSaveLocation(user.getUsername(), fileName, timeMillis));
        file.setFileType(multipartFile.getContentType());
        file.setFileTag("none");
        file.setUserid(user.getUserid());
        switch (fileState){
            case "private": state = 0; break;
            case "group"  : state = 1; break;
            case "public" : state = 2; break;
            default:;
        }
        file.setFileState(state);
        int res = fileDao.addFile(file);
        // 插入失败
        if(res < 1){
            return FileUploadStatusEnum.SQL_ERROR;
        }
        return FileUploadStatusEnum.SUCCESS;
    }

    @Override
    public Boolean checkDownloadable(User user, LocalFile localFile) {
        // 用户自己的文件或公开文件 直接下载
        if(localFile.getUserid() == user.getUserid() || localFile.getFileState() == 2){
            return true;
        }
        // 同组内的用户文件 可下载
        Boolean res =false;

        if(true){
           res = true;
        }
        return res;
    }

    @Override
    public List<LocalFile> findFileByName(String fileName) {
        return fileDao.findFileByName(fileName);
    }

    @Override
    public List<LocalFile> findFileByDesc(String fileDesc) {
        return fileDao.findFileByDesc(fileDesc);
    }

    @Override
    public List<LocalFile> findFileByUserId(int userId) {
        return fileDao.findFileByUserId(userId);
    }

    @Override
    public List<LocalFile> findDownloadedFileByUserId(int userId) {
        return fileDao.findDownloadedFileByUserId(userId);
    }

    @Override
    public File findRealFileByFileId(int fileId) {
        LocalFile localFile = fileDao.findFileByFileId(fileId);
        String filePath = localFile.getFilePath();
        System.out.println(filePath);
        return new File(filePath);
    }
}
