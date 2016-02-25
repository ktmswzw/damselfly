package com.damselfly.controller.activiti;

import com.damselfly.activiti.tools.ExecutionBean;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.mybatis.Page;
import com.damselfly.common.util.Result;
import com.damselfly.viewModel.GridModel;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by v on 2014/7/11.
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping(value = "/workflow/processinstance")
public class ProcessInstanceController  extends BaseAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RuntimeService runtimeService;


    public static final String LIST = "/management/workflow/running-manage";

    @RequestMapping(value="/running-manage", method= RequestMethod.GET)
    public String runningManager(HttpServletRequest request) {
        return LIST;
    }



    @RequestMapping(value = "/running")
    @ResponseBody
    public GridModel running(HttpServletRequest request) {
        GridModel m = new GridModel();
        Page page = page();
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        List<ProcessInstance> list = processInstanceQuery.listPage(page.getOffset()<0?0:page.getOffset(), page.getLimit());

        //List<ProcessDefinitionBean> resultList = new ArrayList();
        for (ProcessInstance processInstance : list) {
            ExecutionEntity entity = (ExecutionEntity) processInstance;
            ExecutionBean bean = new ExecutionBean();
            try {
                BeanUtils.copyProperties(bean, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        m.setRows(list);
        m.setTotal(list.size());
        return m;
    }

    /**
     * 挂起、激活流程实例
     */
    @RequestMapping(value = "update/{state}/{processInstanceId}")
    @ResponseBody
    public Result updateState(@PathVariable("state") String state, @PathVariable("processInstanceId") String processInstanceId,
                              RedirectAttributes redirectAttributes) {
        Result result = new Result();
        try {
            if (state.equals("active")) {
                runtimeService.activateProcessInstanceById(processInstanceId);
                result.setMsg("已激活ID为[" + processInstanceId + "]的流程实例");
            } else if (state.equals("suspend")) {
                runtimeService.suspendProcessInstanceById(processInstanceId);
                result.setMsg("已挂起ID为[" + processInstanceId + "]的流程实例。");
            }
            result.setSuccessful(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.debug("error");
            result.setSuccessful(false);
        }
        return  result;
    }
}