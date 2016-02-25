package com.damselfly.business.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.damselfly.business.entity.Culture;
import com.damselfly.business.service.CultureService;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.mybatis.Page;
import com.damselfly.common.util.JacksonMapper;
import com.damselfly.common.util.Result;
import com.damselfly.viewModel.GridModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by V on Wed Dec 31 23:31:05 CST 2014.
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping(value = "/business/culture")
public class CultureController extends BaseAction {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CultureService cultureService;

    private static final String INDEX = "/business/culture/list";
    private static final String EDIT = "/business/culture/edit";

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String index() {
        return INDEX;
    }


    /**
     * 表格文化信息
     * @return GridModel
     */
    @RequestMapping(value="/list")
    @ResponseBody
    public GridModel list() {
        Culture culture = SearchForm(Culture.class);
        Page info = cultureService.findByPage(page(), culture);
        GridModel m = new GridModel();
        m.setRows(info.getRows());
        m.setTotal(info.getCount());
        return m;
    }




    /**
     * 打印文化信息
     * @return JSONObject
     */
    @RequestMapping(value="/print")
    @ResponseBody
    public JSONObject print(){
        Culture culture = SearchForm(Culture.class);
        JSONObject jsonobject = new JSONObject();
        Page info = cultureService.findByPage(page(), culture);
        List<Culture> cultures = info.getRows();
        try {
            JSONArray jarray = new JSONArray();
            for(Culture c : cultures)
            {
                jarray.add(c);
            }
            jsonobject.put("data",jarray);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonobject;
    }

    /**
     * 添加文化信息
     * @return ModelAndView
     */
    @RequestMapping(value="/add")
    @ResponseBody
    public ModelAndView add() {
        ModelAndView mav = new ModelAndView(EDIT);
        Culture culture = new Culture();
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(culture);
            mav.addObject("doWhat", "add");
            mav.addObject("culture",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 编辑文化信息
     * @return ModelAndView
     */
    @RequestMapping(value="/edit/{id}")
    @ResponseBody
    public ModelAndView edit(@PathVariable Integer id) {
        logger.debug("edit id = " + id);
        ModelAndView mav = new ModelAndView(EDIT);
        try {
            Culture culture =  cultureService.get(id);
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(culture);
            mav.addObject("doWhat", "edit");
            mav.addObject("culture",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 查看文化信息
     * @return ModelAndView
     */
    @RequestMapping(value="/view/{id}")
    @ResponseBody
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView(EDIT);
        try {
            Culture culture = cultureService.get(id);
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(culture);
            mav.addObject("doWhat", "view");
            mav.addObject("culture", json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 保存文化信息
     * @param culture
     * @return Result
     */
    @RequestMapping(value="/saveAdd")
    @ResponseBody
    public Result saveAddCulture(@ModelAttribute Culture culture) {
        Result result = new Result();
        try {
            cultureService.save(culture);
            result.setMsg("操作成功");
            result.setSuccessful(true);
        } finally {

        }
        return result;
    }

    /**
         * 保存文化信息
         * @param culture
         * @return Result
         */
        @RequestMapping(value="/saveEdit")
        @ResponseBody
        public Result saveEditCulture(@ModelAttribute Culture culture) {
            Result result = new Result();
            try {
                cultureService.update(culture);
                result.setMsg("操作成功");
                result.setSuccessful(true);
            } finally {

            }
            return result;
        }

    /**
     * 查询单个文化信息
     * @param id
     * @return
     */
    @RequestMapping(value="/get/{id}")
    @ResponseBody
    public Culture getInfo(@PathVariable Integer id) {
        return  cultureService.get(id);
    }

    /**
     * 删除文化信息
     * @param id
     * @return
     */
    @RequestMapping(value="/delete/{id}")
    @ResponseBody
    public Result deleteInfo(@PathVariable Integer id) {
        Result result = new Result();
        cultureService.delete(id);
        result.setSuccessful(true);
        result.setMsg("删除成功");
        return result;
    }
}

