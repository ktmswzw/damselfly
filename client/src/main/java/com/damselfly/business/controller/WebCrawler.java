package com.damselfly.business.controller;

import com.damselfly.business.entity.Culture;
import com.damselfly.business.service.CultureService;
import com.damselfly.common.baseaction.BaseAction;
import com.damselfly.common.util.Result;
import com.damselfly.common.util.SimpleDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.Date;
import java.util.List;

/**
 * Created by vincent on 2015/1/2.
 */
//view-source:http://jh.bxwhj.com/active_query1.aspx?key=&time1=&time2=&area=&type=&street=&__EVENTTARGET=AspNetPager1&__EVENTARGUMENT=1
public class WebCrawler implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    private CultureService cs;

    public CultureService getCs() {
        return cs;
    }

    public void setCs(CultureService cultureService) {
        this.cs = cultureService;
    }

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    //
/*        text(n)	第n个直接文本子节点，为0表示所有	text() only
        allText()	所有的直接和间接文本子节点	not support
        tidyText()	所有的直接和间接文本子节点，并将一些标签替换为换行，使纯文本显示更整洁	not support
        html()	内部html，不包括标签的html本身	not support
        outerHtml()	内部html，包括标签的html本身	not support
        regex(@attr,expr,group)	这里@attr和group均可选，默认是group0	not support
    */
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
 //       page.putField("author", page.getUrl().regex("https://jh.bxwhj\\.com//active_query1.aspx*").toString());
        //page.putField("author", page.getUrl().regex("https://jh.bxwhj\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name") == null) {
//            //skip this page
//            page.setSkip(true);
//        }

        List<Selectable> a_name = page.getHtml().xpath("//div[@class='a_name']/html()").nodes();
        List<Selectable> a_summary = page.getHtml().xpath("//div[@class='a_summary']/html()").nodes();
        List<Selectable> a_type = page.getHtml().xpath("//div[@class='a_type']/html()").nodes();
        List<Selectable> a_address = page.getHtml().xpath("//div[@class='a_address']/html()").nodes();
        List<Selectable> a_time = page.getHtml().xpath("//div[@class='a_time']/html()").nodes();
        List<Selectable> a_contacts = page.getHtml().xpath("//div[@class='a_contacts']/html()").nodes();
        List<Selectable> a_phone = page.getHtml().xpath("//div[@class='a_phone']/html()").nodes();

        for(int i=0;i<a_name.size();i++)
        {
            Culture culture = new Culture();
            culture.setTitle(a_name.get(i).get());
            culture.setContent(a_summary.get(i).get());
            culture.setCultureType(a_type.get(i).get());
            culture.setVenue(a_address.get(i).get());
            String dayTemp = a_time.get(i).get();
            try {
                if (dayTemp.indexOf("至") > 0) {
                    String[] t = StringUtils.strip(dayTemp.trim()).split("至");
                    culture.setStartTime(SimpleDate.strToDate(t[0].trim()));
                    culture.setEndTime(SimpleDate.strToDate(t[1].trim()));
                } else {
                    culture.setStartTime(SimpleDate.strToDate(a_time.get(i).get().trim()));
                }
            }
            catch (Exception e)
            {
                culture.setStartTime(new Date());
            }
            finally {
            }
                culture.setOrganizer(a_contacts.get(i).get() + ":" + a_phone.get(i).get());
            this.cs.save(culture);
        }

        // 部分三：从页面发现后续的url地址来抓取
        //page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
    }

        @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        int i =1;
        while (i<21) {
            Spider.create(new WebCrawler())
                    //从"https://github.com/code4craft"开始抓
                    .addUrl("http://jh.bxwhj.com/active_query1.aspx?key=&time1=&time2=&area=&type=&street=&__EVENTTARGET=AspNetPager1&__EVENTARGUMENT=" + i)
                            //开启5个线程抓取
                    .thread(1)
                            //启动爬虫
                    .run();
            i++;
        }
    }
}
