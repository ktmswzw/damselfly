package com.damselfly.business.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonEncoding;
import com.damselfly.business.entity.Test;
import com.damselfly.business.service.TestService;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.mybatis.Page;
import com.damselfly.common.util.JacksonMapper;
import com.damselfly.common.util.Result;
import com.damselfly.viewModel.GridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

/**
 * Created by xecoder on Sun Sep 20 22:32:26 GMT+08:00 2015.
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping(value = "/business/test")
public class TestController extends BaseAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TestService testService;

    private static final String INDEX = "/business/test/list";
    private static final String EDIT = "/business/test/edit";

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String index() {
        return INDEX;
    }


    /**
     * 表格测试表
     * @return GridModel
     */
    @RequestMapping(value="/list", method= RequestMethod.GET)
    @ResponseBody
    public GridModel list() {
        Test test = SearchForm(Test.class);
        Page info = testService.findByPage(page(), test);
        GridModel m = new GridModel();
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }


    /**
     * 添加测试表
     * @return ModelAndView
     */
    @RequestMapping(value="/add")
    @ResponseBody
    public ModelAndView add() {
        ModelAndView mav = new ModelAndView(EDIT);
        Test test = new Test();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(test);
            mav.addObject("message", "完成");
            mav.addObject("test",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 编辑测试表
     * @return ModelAndView
     */
    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView edit(@PathVariable Integer id) {
        logger.debug("edit id = " + id);
        ModelAndView mav = new ModelAndView(EDIT);
        try {
            Test test =  testService.get(id);
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(test);
            mav.addObject("message", "完成");
            mav.addObject("test",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }



    /**
     * 保存测试表
     * @param test
     * @return Result
     */
    @RequestMapping(value="/save")
    @ResponseBody
    public Result saveAddTest(@ModelAttribute Test test) {
        Result result = new Result();
        try {
            if (test.getTestId() != null)
            {
                testService.update(test);
                result.setMsg("成功");
                result.setSuccessful(true);
            }
            else
            {
                testService.save(test);
                result.setMsg("成功");
                result.setSuccessful(true);
            }
        } finally {
                result.setMsg("失败");
                result.setSuccessful(false);
        }
        return result;
    }

    /**
     * 查询单个测试表
     * @param id
     * @return
     */
    @RequestMapping(value="/get/{id}")
    @ResponseBody
    public Test getInfo(@PathVariable Integer id) {
        return  testService.get(id);
    }

    /**
     * 删除测试表
     * @param id
     * @return
     */
    @RequestMapping(value="/delete/{id}")
    @ResponseBody
    public Result deleteInfo(@PathVariable Integer id) {
        Result result = new Result();
        testService.delete(id);
        result.setSuccessful(true);
        result.setMsg("删除成功");
        return result;
    }
}

