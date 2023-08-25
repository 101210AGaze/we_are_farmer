package com.farmer.dataanalysis.spider;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.farmer.dataanalysis.Utils.CsvUtil;
import com.farmer.dataanalysis.Utils.HttpUtil;
import com.farmer.dataanalysis.Utils.TimeUtil;
import com.farmer.dataanalysis.bean.spiderBeans.mList;
import com.farmer.dataanalysis.bean.spiderBeans.msgBean;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.File;
import java.util.List;


public class PriceSpider {
    //后续需要将该方法改为定时任务，如每天8点定时爬取数据
    @Test
    public void getPrice() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNum","1");
        jsonObject.put("pageSize","10000");
        jsonObject.put("marketId","");
        jsonObject.put("pid","");
        jsonObject.put("varietyId","");
        String html = HttpUtil.sendJsonAndGetHtml("http://pfsc.agri.cn/api/priceQuotationController/pageList?key=&order=",
                jsonObject,"UTF-8");

        String info = Jsoup.parse(html).select("body").text();
        msgBean msgBean = JSON.parseObject(info, msgBean.class);
        List<mList> data = msgBean.getContent().getList();
        File filePath = new File("C:\\Users\\黄文敬\\Desktop\\爬虫\\爬取数据\\"
                + TimeUtil.format(System.currentTimeMillis(),"yyyyMMdd")+"价格.csv");
        CsvUtil.export(filePath,data);//将爬取到的数据封装到CSV中，默认没有第一行的数据说明
    }
}
