package com.damselfly.controller.core;

import com.damselfly.common.baseaction.BaseAction;
import com.mongodb.gridfs.GridFSDBFile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 2015/4/8.
 */
public class GridFSController   extends BaseAction {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GridFsTemplate gridFsTemplate;


    @RequestMapping(value = "/uploadGrid", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public String startProcess(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                try {
                    gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {

        }
        return "";
    }


    @RequestMapping(value="/list", method=RequestMethod.GET)
    @ResponseBody
    public  List<File> listFiles() {
        List<GridFSDBFile> list = gridFsTemplate.find(null);
        List<File> listFiles = new ArrayList<File>();
        for (GridFSDBFile gridFSDBFile : list) {
            listFiles.add(convertToFile(gridFSDBFile));
        }
        return listFiles;
    }

    private File convertToFile(GridFSDBFile file){
        return new File(file.getFilename());
    }


    @RequestMapping(value = "/file/{name}", method = RequestMethod.GET)
    public void handleFileDownload(@PathVariable("name") String name,HttpServletResponse response) {
        GridFSDBFile file = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("filename").is(name)));
        if (file != null) {
            try {
                response.setContentType(file.getContentType());
                response.setContentLength((new Long(file.getLength()).intValue()));
                response.setHeader("content-Disposition", "attachment; filename=" + file.getFilename());// "attachment;filename=test.xls"
                // copy it to response's OutputStream
                IOUtils.copyLarge(file.getInputStream(), response.getOutputStream());
            } catch (IOException ex) {
                //_logger.info("Error writing file to output stream. Filename was '" + id + "'");
                //throw new RuntimeException("IOError writing file to output stream");
            }
        }
        //return (MultipartFile) file;
    }
}

