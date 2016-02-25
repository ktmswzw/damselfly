package com.damselfly.controller.core;

import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Controller
@RequestMapping(value = "/upload/main")
public class UploadController extends BaseAction {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private MongoConverter mongoConverter;

    @Autowired
    private  MongoDbFactory mongoDbFactory;


    /**
     * 读取启动流程的表单字段
     */
    @RequestMapping(value = "/uploadMongoDB", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    @ResponseBody
    public Boolean startProcess(@RequestParam("file") MultipartFile file) {
        boolean flag = false;
        if (!file.isEmpty()) {
            UploadUtils uploadUtils = new UploadUtils();
            flag = uploadUtils.uploadMongoDB(UUID.randomUUID().toString(),file,gridFsTemplate);
        }
        return flag;
    }

}
