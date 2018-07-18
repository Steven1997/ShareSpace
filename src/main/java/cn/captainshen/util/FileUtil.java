package cn.captainshen.util;

import cn.captainshen.enums.FileUploadStatusEnum;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ResourceBundle;


@Component("fileUtil")
public class FileUtil {
    ResourceBundle resource = ResourceBundle.getBundle("file");
    /**
     * 工作空间文件写入
     * @param username      用户名
     * @param uploadFile    文件内容
     * @param timeMillis    上传时间戳(防止重名)
     * @return
     * @throws IOException
     */
    public FileUploadStatusEnum upload(String username, File uploadFile, long timeMillis){
        // 加载文件上传属性配置
        String workspace = resource.getString("workspace");
        workspace += "/" + username;
        System.out.println(workspace);
        // 创建用户文件夹
        File file = new File(workspace);
        if(!file.isDirectory()){
            file.delete();
            file.mkdir();
        }
        if(!file.exists()) file.mkdir();
        //TODO 文件数量、大小判断
        // 文件写入
        if(uploadFile != null){
            try{
                InputStream inputStream = new FileInputStream(uploadFile);
                String fileName = uploadFile.getName();
                // 将真实存储文件名改为name + time_tag + suffix
                int index = fileName.lastIndexOf(".");
                String name = "";
                for(int i = 0; i < index; ++i){
                    name += fileName.charAt(i);
                }
                name += timeMillis;
                name += ".";
                for(int i = index + 1; i < fileName.length(); ++i){
                    name += fileName.charAt(i);
                }
                System.out.println(name);
                FileOutputStream outputStream = new FileOutputStream(workspace + "/" +  name);
                int len = 0;
                byte[] buffer = new byte[4096];
                while((len = inputStream.read(buffer)) != -1){
                    outputStream.write(buffer, 0, len);
                }
            }catch (Exception e){
                e.printStackTrace();
                return FileUploadStatusEnum.UNKNOWN_FAILURE;
            }
        }
        return FileUploadStatusEnum.SUCCESS;
    }
}
