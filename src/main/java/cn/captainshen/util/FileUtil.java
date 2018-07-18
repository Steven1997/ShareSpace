package cn.captainshen.util;

import cn.captainshen.enums.FileUploadStatusEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ResourceBundle;


@Component("fileUtil")
public class FileUtil {
    ResourceBundle resource = ResourceBundle.getBundle("config");
    String rootWorkspace = resource.getString("workspace");
    /**
     * 保存上传文件至工作空间
     * @param username      用户名
     * @param uploadFile    文件内容
     * @param timeMillis    上传时间戳(防止重名)
     * @return
     * @throws IOException
     */
    public FileUploadStatusEnum saveUploadFile(String username, MultipartFile uploadFile, long timeMillis){
        String workspace = rootWorkspace + "/" + username;
        // 创建用户文件夹
        File file = new File(workspace);
        if(!file.isDirectory()){
            file.delete();
            file.mkdir();
        }
        if(!file.exists()) file.mkdir();
        // 文件写入
        if(uploadFile != null){
            try{
                String fileName = uploadFile.getOriginalFilename();
                String fileSaveLocation = this.getFileSaveLocation(username, fileName, timeMillis);
                uploadFile.transferTo(new File(fileSaveLocation));
            }catch (Exception e){
                e.printStackTrace();
                return FileUploadStatusEnum.UNKNOWN_FAILURE;
            }
        }
        return FileUploadStatusEnum.SUCCESS;
    }

    /**
     * 获取文件真实保存地址
     * @param username  用户名
     * @param fileName  文件名
     * @return
     */
    public String getFileSaveLocation(String username, String fileName, long timeMillis){
        String workspace = rootWorkspace +  "/" + username;
        // 将真实存储文件名改为name + (time_tag) + suffix
        int index = fileName.lastIndexOf(".");
        String name = "";
        for(int i = 0; i < index; ++i){
            name += fileName.charAt(i);
        }
        name += "(" + timeMillis + ").";
        for(int i = index + 1; i < fileName.length(); ++i){
            name += fileName.charAt(i);
        }
        return  workspace + "/" + name;
    }
}