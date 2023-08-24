package com.farmer.dataanalysis.spider;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.farmer.dataanalysis.Utils.ExcelUtil;
import com.farmer.dataanalysis.Utils.HttpUtil;
import com.farmer.dataanalysis.bean.mList;
import com.farmer.dataanalysis.bean.msgBean;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.util.List;


public class dataSpider {


    //后续需要将该方法改为定时任务，如每天8点定时爬取疫情数据
    @Test

    public void testSpider() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNum","1");
        jsonObject.put("pageSize","10000");
        jsonObject.put("marketId","");
        //jsonObject.put("provinceCode","110000");
        jsonObject.put("pid","AE");
        jsonObject.put("varietyId","");
        String html = HttpUtil.sendJsonAndGetHtml("http://pfsc.agri.cn/api/priceQuotationController/pageList?key=&order=",
                jsonObject,"UTF-8");

        String info = Jsoup.parse(html).select("body").text();
        msgBean msgBean = JSON.parseObject(info, msgBean.class);
        List<mList> data = msgBean.getContent().getList();
        ExcelUtil.export(msgBean.getContent().getTotal(),data);//将爬取到的数据封装到Excel中


        //1、爬取指定页面

        //2、解析页面中的指定内容    （这里需要根据爬取到的网页源码，使用jsoup筛选出我们所需要的数据）

        //3、使用正则表达式获取json数据

        //4、对json数据进行更进一步的解析
    }
}
