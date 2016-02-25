package com.damselfly.controller.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.util.JacksonMapper;
import com.damselfly.viewModel.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by v on 2014/8/14.
 */
@Controller
@RequestMapping("/core/tool")
@SuppressWarnings("unchecked")
public class ToolController   extends BaseAction  {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final String PRINT = "include/print";

    @RequestMapping(value="/print")
    public String print() {
        return PRINT;
    }

    @RequestMapping(value="/printX")
    @ResponseBody
    public ModelAndView printX() {
        ModelAndView mav = new ModelAndView(PRINT);
        Table table = dynamicForm(Table.class);
        try {
            ObjectMapper mapper = JacksonMapper.getInstance();
            String json =mapper.writeValueAsString(table);
            logger.debug(json);
            mav.addObject("doWhat", "print");
            mav.addObject("table",json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return mav;
    }
}
