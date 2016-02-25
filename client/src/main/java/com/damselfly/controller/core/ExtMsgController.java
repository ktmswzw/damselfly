package com.damselfly.controller.core;

import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.util.Result;
import com.damselfly.entity.ExtMsg;
import com.damselfly.entity.User;
import com.damselfly.service.core.ExtMsgService;
import com.damselfly.shiro.ShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.damselfly.shiro.SecurityUtils.getSubject;

/**
 * Created by v on 2014/7/12.
 */
@Controller
@RequestMapping("/console/extmsg")
public class ExtMsgController  extends BaseAction {

    @Autowired
    private ExtMsgService extMsgService;

    @RequestMapping(value="/findListByExtMsg/{name}/{type}")
    @ResponseBody
    public List<ExtMsg> findListByExtMsg(@PathVariable String name,@PathVariable String type) {
        ExtMsg extMsg = new ExtMsg();
        extMsg.setName(name);
        extMsg.setType(type);
        List<ExtMsg> list = extMsgService.findList(page(), extMsg);
        return list;
    }

    @RequestMapping(value="/addExtMsg/{value}/{type}")
    @ResponseBody
    public Result addExtMsg(@PathVariable String value,@PathVariable String type) {
        ExtMsg extMsg = new ExtMsg();
        Subject subject = getSubject();
        ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
        String username = shiroUser.getLoginName();
        extMsg.setName(username);
        extMsg.setType(type);
        if(StringUtils.equals(type,"theme"))
        {
            User user = shiroUser.getUser();
            user.setTheme(value);
            shiroUser.setUser(user);

        }
        List<ExtMsg> list  = extMsgService.findList(page(),extMsg);
        if (list.size() > 0) {
            for (ExtMsg extMsg1 : list) {
                extMsg1.setValue(value);

                extMsgService.update(extMsg1);
            }
        } else {
            extMsg.setValue(value);
            extMsgService.save(extMsg);
        }

        Result result = new Result();
        result.setMsg("ok");
        result.setSuccessful(true);
        return result;
    }

    @RequestMapping(value="/getValue/{type}")
    @ResponseBody
    public Result getValue(@PathVariable String type) {
        ExtMsg extMsg = new ExtMsg();
        Subject subject = getSubject();
        ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
        String username = shiroUser.getLoginName();
        extMsg.setName(username);
        extMsg.setType(type);

        List<ExtMsg> list  = extMsgService.findList(page(),extMsg);
        String temp ="default";
        if (list.size() > 0) {
            for (ExtMsg extMsg1 : list) {
                temp = extMsg1.getValue();
            }
        }

        Result result = new Result();
        result.setMsg(temp);
        result.setSuccessful(true);
        return result;
    }

    @RequestMapping(value="/getThemeValue")
    @ResponseBody
    public Result getThemeValue() {
        Subject subject = getSubject();
        ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
        Result result = new Result();
        result.setMsg(shiroUser.getUser().getTheme());
        result.setSuccessful(true);
        return result;
    }
}
