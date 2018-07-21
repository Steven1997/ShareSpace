package cn.captainshen.service.impl;

import cn.captainshen.dao.FileDao;
import cn.captainshen.dao.UserDao;
import cn.captainshen.entity.LocalFile;
import cn.captainshen.entity.User;
import cn.captainshen.enums.FileUploadStatusEnum;
import cn.captainshen.service.FileService;
import cn.captainshen.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;
import java.io.File;
import java.util.Date;
import java.util.List;


@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private UserDao userDao;

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
        //TODO 同组内的用户文件可下载
        Boolean res =true;
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
        return new File(filePath);
    }

    @Override
    public List<LocalFile> findPublicFilesByFileName(String fileName) {
        // 替换% + 预编译 防止SQL注入
        fileName = fileName.replaceAll("%", "\\\\%");
        System.out.println(fileName);
        List<LocalFile> res =  fileDao.findPublicFilesByFileName(fileName);
        for(int i = 0; i < res.size(); ++i){
            LocalFile tmp = res.get(i);
            String username = userDao.findUserByUserId(tmp.getUserid()).getUsername();
            res.get(i).setUserName(username);
        }
        return res;
    }

    @Override
    public LocalFile findFileByFileId(int fileId) {
        return fileDao.findFileByFileId(fileId);
    }

    @Override
    public void updateFileInfo(int fileId, String fileDesc, String fileState) {
        int state;
        switch (fileState){
            case "private" : state = 0; break;
            case "group"   : state = 1; break;
            case "public"  : state = 2; break;
            //  非法参数
            default: return;
        }
        fileDao.updateFileInfo(fileId, fileDesc, state);
    }

    @Override
    public void deleteFileByFileId(int fileId) {
        File file = this.findRealFileByFileId(fileId);
        fileDao.deleteFileByFileId(fileId);
        file.delete();
    }

    @Override
    public void addDownloadRecord(int userId, int fileId) {
        LocalFile file = fileDao.findFileByFileId(fileId);
        User user = userDao.findUserByUserId(userId);
        /**
         * 这里懒得再写一个下载关系实体类
         * LocalFile类中的userId在这里表示下载者的Id
         */
        file.setUserName(user.getUsername());
        file.setUserid(userId);
        file.setDownloadDate(new Date(System.currentTimeMillis()));
        fileDao.addDownloadRecord(file);
    }

    @Override
    public List<LocalFile> findGroupFilesByGroupId(int groupId) {
       List<LocalFile> res =  fileDao.findGroupFilesByGroupId(groupId);
       for(int i = 0; i < res.size(); ++i){
           LocalFile temp = res.get(i);
           User user = userDao.findUserByUserId(temp.getUserid());
           res.get(i).setUserName(user.getUsername());
       }
       return res;
    }
}