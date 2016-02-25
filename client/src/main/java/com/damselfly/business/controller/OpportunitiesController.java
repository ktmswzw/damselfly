package com.damselfly.business.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.damselfly.business.entity.Opportunities;
import com.damselfly.business.service.OpportunitiesService;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.mybatis.Page;
import com.damselfly.common.util.JacksonMapper;
import com.damselfly.common.util.Result;
import com.damselfly.viewModel.GridModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by V on Tue Nov 25 22:55:43 CST 2014.
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping(value = "/business/opportunities")
public class OpportunitiesController extends BaseAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OpportunitiesService opportunitiesService;
    private static final String INDEX = "/business/opportunities/list";
    private static final String EDIT = "/business/opportunities/edit";
    private static final String THANKS = "sale/thanks";

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String index() {
        return INDEX;
    }


    /**
     * 表格商机咨询
     * @return GridModel
     */
    @RequestMapping(value="/list")
    @ResponseBody
    public GridModel list() {
        Opportunities opportunities = SearchForm(Opportunities.class);
        Page info = opportunitiesService.findByPage(page(), opportunities);
        GridModel m = new GridModel();
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }

    /**
     * 打印商机咨询
     * @return JSONObject
     */
    @RequestMapping(value="/print")
    @ResponseBody
    public JSONObject print(){
        Opportunities opportunities = SearchForm(Opportunities.class);
        JSONObject jsonobject = new JSONObject();
        Page info = opportunitiesService.findByPage(page(), opportunities);
        List<Opportunities> opportunitiess = info.getRows();
        try {
            JSONArray jarray = new JSONArray();
            for(Opportunities c : opportunitiess)
            {
                jarray.add(c);
            }
            jsonobject.put("data", jarray);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonobject;
    }

    /**
     * 添加商机咨询
     * @return ModelAndView
     */
    @RequestMapping(value="/add")
    @ResponseBody
    public ModelAndView add() {
        ModelAndView mav = new ModelAndView(EDIT);
        Opportunities opportunities = new Opportunities();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(opportunities);
            mav.addObject("doWhat", "add");
            mav.addObject("opportunities",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 编辑商机咨询
     * @return ModelAndView
     */
    @RequestMapping(value="/edit/{id}")
    @ResponseBody
    public ModelAndView edit(@PathVariable Integer id) {
        logger.debug("edit id = " + id);
        ModelAndView mav = new ModelAndView(EDIT);
        try {
            Opportunities opportunities =  opportunitiesService.get(id);
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(opportunities);
            mav.addObject("doWhat", "edit");
            mav.addObject("opportunities",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }


    /**
     * 保存商机咨询
     * @param opportunities
     * @return Result
     */
    @RequestMapping(value="/saveAdd")
    public String saveAddOpportunities(@ModelAttribute Opportunities opportunities) {
        try {
            opportunitiesService.saveMongoDB(opportunities);
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json  =mapper.writeValueAsString(opportunities);
            opportunitiesService.saveMongoDB(json);
            opportunitiesService.save(opportunities);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {

        }
        return THANKS;
    }

    /**
         * 保存商机咨询
         * @param opportunities
         * @return Result
         */
        @RequestMapping(value="/saveEdit")
        @ResponseBody
        public Result saveEditOpportunities(@ModelAttribute Opportunities opportunities) {
            Result result = new Result();
            try {
                if (opportunities.getOpportunitiesId() != null && opportunities.getOpportunitiesId() > 1) {
                    opportunitiesService.update(opportunities);
                } else {
                    opportunitiesService.save(opportunities);
                }
                result.setMsg("操作成功");
                result.setSuccessful(true);
            } finally {

            }
            return result;
        }

    /**
     * 查询单个商机咨询
     * @param id
     * @return
     */
    @RequestMapping(value="/get/{id}")
    @ResponseBody
    public Opportunities getInfo(@PathVariable Integer id) {
        return  opportunitiesService.get(id);
    }

    /**
     * 删除商机咨询
     * @param id
     * @return
     */
    @RequestMapping(value="/delete/{id}")
    @ResponseBody
    public Result deleteInfo(@PathVariable Integer id) {
        Result result = new Result();
        opportunitiesService.delete(id);
        result.setSuccessful(true);
        result.setMsg("删除成功");
        return result;
    }

}

