package com.damselfly.controller.core;

import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.util.DownloadUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * Created by v on 2014/8/28.
 */
@Controller
@RequestMapping("/download")
public class DownloadController   extends BaseAction {

    @RequestMapping(value = "/get")
    public String download(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "filePath") String filePath) throws Exception {


        filePath = filePath.replace("/", "\\");

        if (StringUtils.isEmpty(filePath) || filePath.contains("\\.\\.")) {
            response.setContentType("text/html;charset=utf-8");
            return null;
        }
        filePath = URLDecoder.decode(filePath, "UTF-8");


        DownloadUtils.download(request, response, filePath);

        return null;
    }
}
