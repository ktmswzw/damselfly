package com.damselfly.controller.core;

import com.damselfly.business.controller.WebCrawler;
import com.damselfly.business.entity.Culture;
import com.damselfly.business.entity.Opportunities;
import com.damselfly.business.service.CultureService;
import com.damselfly.business.service.OpportunitiesService;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.util.JacksonMapper;
import com.damselfly.common.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import us.codecraft.webmagic.Spider;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by vincent on 2014/11/18.
 */
@Controller
@RequestMapping("/")
public class WebController extends BaseAction {
    private static final String HOME = "index";
    private static final String ABOUT = "sale/about";
    private static final String CONTACT = "sale/contact";
    private static final String PRIVACY = "sale/privacy";
    private static final String TERMS = "sale/terms";
    private static final String THANKS = "sale/thanks";
    private static final String MAP = "sale/map";
    private static final String ERROR = "sale/error";
    @Autowired
    OpportunitiesService opportunitiesService;
    @Autowired
    CultureService cultureService;

    @RequestMapping(value="")
    @ResponseBody
    public ModelAndView index() {
        ModelAndView model = new ModelAndView(HOME);
        model.addObject("current","index");
        return model;
    }

    @RequestMapping(value="about",method= RequestMethod.GET)
    @ResponseBody
    public ModelAndView about() {
        ModelAndView model = new ModelAndView(ABOUT);
        model.addObject("current","about");
        return model;
    }

    @RequestMapping(value="contact",method= RequestMethod.GET)
    @ResponseBody
    public ModelAndView contact() {
        ModelAndView model = new ModelAndView(CONTACT);
        model.addObject("current","contact");
        return model;
    }

    @RequestMapping(value="privacy",method= RequestMethod.GET)
    @ResponseBody
    public ModelAndView privacy() {
        ModelAndView model = new ModelAndView(PRIVACY);
        model.addObject("current","privacy");
        return model;
    }

    @RequestMapping(value="terms",method= RequestMethod.GET)
    @ResponseBody
    public ModelAndView terms() {
        ModelAndView model = new ModelAndView(TERMS);
        model.addObject("current","terms");
        return model;
    }

    @RequestMapping(value="map",method= RequestMethod.GET)
    @ResponseBody
    public ModelAndView map(HttpServletRequest request) {
        ModelAndView model = new ModelAndView(MAP);
        model.addObject("current","map");
        List<Culture> list = cultureService.findAll(page(),new Culture());
        model.addObject("showList",list);//
        ObjectMapper mapper = JacksonMapper.getInstance();
        try {
            String json =mapper.writeValueAsString(list);
            model.addObject("cultureList",json);//
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return model;
    }

    /**
     * 表格文化信息
     * @return GridModel
     */
    @RequestMapping(value="/flash")
    @ResponseBody
    public Result flash() {
        Result result = new Result();
        try {
            int i =1;

            Culture culture_null = new Culture();
            culture_null.setTitle("1");
            cultureService.delete(culture_null);

            while (i<21) {
                WebCrawler webCrawler = new WebCrawler();

                webCrawler.setCs(cultureService);
                Spider.create(webCrawler)
                        //从"https://github.com/code4craft"开始抓
                        .addUrl("http://jh.bxwhj.com/active_query1.aspx?key=&time1=&time2=&area=&type=&street=&__EVENTTARGET=AspNetPager1&__EVENTARGUMENT=" + i)
                                //开启1个线程抓取
                        .thread(1)
                                //启动爬虫
                        .run();
                i++;
            }
            result.setMsg("加载成功");
            result.setSuccessful(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            return result;
        }
    }


    @RequestMapping(value="/update/{id}/{lng}/{lat}/{temp}",method= RequestMethod.POST)
    @ResponseBody
    public Result updateMap(@PathVariable int id,@PathVariable String lng,@PathVariable String lat,@PathVariable String temp) {
        Result result = new Result();
        try {
            Culture culture = new Culture();
            culture.setId(id);
            culture.setLongitude(lng);
            culture.setLatitude(lat);
            cultureService.update(culture);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            return result;
        }
    }


    /**
     * 保存商机咨询
     * @param opportunities
     * @return Result
     */
    @RequestMapping(value="/thanks")
    @ResponseBody
    public ModelAndView saveAddOpportunities(@ModelAttribute Opportunities opportunities) {

        ModelAndView model = new ModelAndView("");
        model.addObject("current","thanks");

        try {
            if(opportunities!=null) {
                if(opportunities.getEmail()!=null)
                    opportunitiesService.save(opportunities);
                else
                    model.setViewName(ERROR);
            }
            model.setViewName(THANKS);
            return model;
        } catch (Exception e){
            model.setViewName(ERROR);
            return model;
        }
    }
}
