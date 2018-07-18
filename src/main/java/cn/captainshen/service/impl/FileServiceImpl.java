package cn.captainshen.service.impl;

import cn.captainshen.entity.User;
import cn.captainshen.enums.FileUploadStatusEnum;
import cn.captainshen.service.FileService;
import cn.captainshen.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileUtil fileUtil;

    @Override
    public FileUploadStatusEnum uploadFile(User user, MultipartFile multipartFile, long timeMillis) {
        FileUploadStatusEnum saveStatus = fileUtil.saveUploadFile(user.getUsername(), multipartFile, timeMillis);
        // 文件本地保存失败
        if(!(saveStatus == FileUploadStatusEnum.SUCCESS)){
            return saveStatus;
        }
        //TODO 数据入库

        return saveStatus;
    }
}
