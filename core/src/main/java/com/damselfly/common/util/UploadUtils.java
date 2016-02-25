package com.damselfly.common.util;

import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by v on 2014/8/28.
 */
public class UploadUtils {
    public static String upload(MultipartFile file,HttpServletRequest request){
        String logoPathDir = "/upload/"
                + SimpleDate.format(SimpleDate.localDate(new Date(),1));
        //String contextPath = request.getSession().getServletContext().getRealPath(logoPathDir);
        //String filePath = contextPath + File.separator ;
        String filePath = logoPathDir ;
        File file_in = new File(filePath);
        file_in.mkdirs();

        String newFilenameBase = UUID.randomUUID().toString();
        String originalFileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newFilename = newFilenameBase + originalFileExtension;
        File source = new File(filePath+"/"+newFilename);
        try {
            file.transferTo(source);
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR";
        }
        return filePath+"/"+newFilename;
    }

    public static boolean uploadMongoDB(String key,MultipartFile file,GridFsTemplate gridFsTemplate)
    {
        boolean flag = false;
        if (!file.isEmpty()) {
            if (!file.isEmpty()) {
                try {

                    gridFsTemplate.store(file.getInputStream(), key);
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


}
