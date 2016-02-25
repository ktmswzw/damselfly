package com.damselfly.controller.core;

import com.google.code.kaptcha.Constants;
import com.google.common.base.Strings;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.mybatis.Page;
import com.damselfly.common.util.CryptUtil;
import com.damselfly.common.util.Result;
import com.damselfly.common.util.SimpleDate;
import com.damselfly.controller.activiti.CoreTool;
import com.damselfly.entity.Reset;
import com.damselfly.entity.User;
import com.damselfly.service.core.ResetService;
import com.damselfly.service.core.UserService;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by mxz on 2014/8/29.
 */
@Controller
@RequestMapping("/reset")
@SuppressWarnings("unchecked")
public class RestController extends BaseAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String START = "/reset/reset";
    private static final String DOSET = "/reset/doset";


    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResetService resetService;

    @RequestMapping(value="/start", method= RequestMethod.GET)
    public String start() {
        return START;
    }

    /**
     * 发起流程
     * @param mail
     * @param code
     * @param request
     * @return
     */
    @RequestMapping(value="/sendmail/{mail}/{code}", method= RequestMethod.POST)
    @ResponseBody
    public Result sendMail(@PathVariable String mail,@PathVariable String code,HttpServletRequest request) {
        Result result = new Result();
		 HttpSession session = request.getSession();
         String code_s = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        logger.debug("******************验证码是: " + code);
        logger.debug("******************mail: " + mail);
        logger.debug("******************验证码s是: " + code_s);

        if(!StringUtils.equals(code,code_s))
        {
            result.setMsg("验证码错误");
            result.setSuccessful(false);
            return result;
        }

        User user = new User();
        user.setEmail(mail);
        if(userService.find(user).size()==0)
        {
            result.setMsg("邮箱错误");
            result.setSuccessful(false);
            return result;
        }

        CoreTool coreTool = new CoreTool();
        String processDefinitionId = coreTool.getKey("reset",repositoryService);
        if(StringUtils.length(processDefinitionId)==0)
        {
            result.setMsg("未发布密码找回流程");
            result.setSuccessful(false);
            return result;
        }

        Map<String, Object> formProperties = new HashMap<>();
        formProperties.put("email",mail);
        String resetUrl = "http://"+request.getRemoteHost()+":"+request.getServerPort()+"/reset/resetPwd/"+mail+"/";
        logger.debug("request.getRemoteHost()"+request.getRemoteHost());
        logger.debug("request.getLocalAddr()"+request.getLocalAddr());
        logger.debug("request.getLocalName()"+request.getLocalName());
        logger.debug("request.getServerPort()"+request.getServerPort());
        logger.debug("request.getContextPath()"+request.getContextPath());
        logger.debug("request.getServerName()" + request.getServerName());

        Date date = new Date();
        String dates = SimpleDate.formatDateTime(date);
        String salt = UUID.randomUUID().toString();

        Reset reset = new Reset();
        reset.setMail(mail);
        reset.setRequestDate(new Timestamp(date.getTime()));
        reset.setSalt(salt);
        reset.setState(1);
        resetService.save(reset);
        //停止历史
        clearHistoryProcess(mail);

        try {
            CryptUtil cryptUtil = new CryptUtil(salt);// 自定义密钥
            formProperties.put("email",mail);
            formProperties.put("resetUrl", resetUrl + cryptUtil.encrypt("mail$" + mail + "$" + dates));//加密
        } catch (Exception e) {
            e.printStackTrace();
        }

        runtimeService.startProcessInstanceById(processDefinitionId, mail, formProperties);

        result.setMsg("发送成功");
        result.setSuccessful(true);
        return result;
    }

    @RequestMapping(value="/resetPwd/{mail}/{cryptstr}")
    @ResponseBody
    public ModelAndView sendMail(@PathVariable String mail,@PathVariable String cryptstr) {
        ModelAndView model = new ModelAndView(DOSET);
        Reset reset = new Reset();
        reset.setMail(mail);
        reset.setState(1);
        List<Reset> resets = resetService.find(page(),reset);
        if(resets.size()==0)
        {
            model.addObject("msg", "该账号未发起密码找回流程");
            model.addObject("result", "false");
            return model;
        }
        else{
            for(Reset r : resets) {
                Reset reset1 = r;
                try {
                    CryptUtil cryptUtil = new CryptUtil(reset1.getSalt());// 自定义密钥
                    String [] cryptResult = StringUtils.split(cryptUtil.decrypt(cryptstr),"$");//解密结果
                    Date old;
                    if(cryptResult.length!=3)
                    {
                        model.addObject("msg","链接无效");
                        model.addObject("result", "false");
                        return model;
                    }
                    else
                    {
                        try
                        {
                            old = SimpleDate.strToDate(cryptResult[2]);
                        }
                        catch (Exception e)
                        {
                            model.addObject("msg","链接无效");
                            model.addObject("result", "false");
                            return model;
                        }
                    }

                    if(!StringUtils.equals(cryptResult[1],mail))
                    {
                        model.addObject("msg","抄袭啊");//邮箱不匹配
                        model.addObject("result", "false");
                        return model;
                    }

                    Date now = new Date();
                    if(!now.after(old))
                    {
                        model.addObject("msg","未来人，欢迎进入共产主义");
                        model.addObject("result", "false");
                        return model;
                    }
                    else
                    {
                        long s = (now.getTime() - old.getTime()) / 1000;
                        long count = s / 60;
                        if(count>30){
                            model.addObject("msg","链接无效，超过30分钟，请重新发起");
                            model.addObject("result", "false");
                            return model;
                        }
                        model.addObject("result","true");
                        User user = new User();
                        user.setEmail(mail);
                        List<User> list = userService.find(user);
                        if(list.size()>0)
                        model.addObject("user",list.get(0));
                        else
                        {
                            model.addObject("msg","账号已注销");
                            model.addObject("result", "false");
                            return model;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return model;
    }

    @RequestMapping(value="/updatePwd/{id}", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Result editingUser(@ModelAttribute User user, @PathVariable Integer id) {
        logger.debug("id = " + id);
        Result result = new Result();
        if(!Strings.isNullOrEmpty(id + ""))
            userService.update(user);

        Reset reset1 = new Reset();
        reset1.setMail(user.getEmail());
        List<Reset> list= resetService.find(null, reset1);
        for(Reset reset2 : list)
        {
            reset2.setState(0);
            resetService.update(reset2);
        }
        endProcess(user.getEmail());

        result.setMsg("操作成功");
        result.setSuccessful(true);
        return result;
    }


    private void clearHistoryProcess(String mail)
    {
        Page page = page();
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(mail);
        List<ProcessInstance> list = processInstanceQuery.listPage(page.getOffset()<0?0:page.getOffset(), page.getLimit());
        for(ProcessInstance p : list)
        {
            endProcessInstance(p.getProcessInstanceId());
        }
    }

    private void endProcess(String mail)
    {
        Page page = page();
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(mail);
        List<ProcessInstance> list = processInstanceQuery.listPage(page.getOffset()<0?0:page.getOffset(), page.getLimit());
        for(ProcessInstance p : list)
        {
            List<Task> tasks = taskService.createTaskQuery().processDefinitionId(p.getProcessDefinitionId()).active().orderByTaskId().desc().list();
            for(Task task : tasks)
            {
                taskService.complete(task.getId());
            }
        }
    }

    private void endProcessInstance(String processInstanceId) {
        runtimeService.deleteProcessInstance(processInstanceId, "覆盖终止");
    }

}
