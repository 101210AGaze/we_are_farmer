package com.farmer.dataanalysis.spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.farmer.dataanalysis.Utils.CsvUtil;
import com.farmer.dataanalysis.Utils.HttpUtil;
import com.farmer.dataanalysis.Utils.TimeUtil;
import org.jsoup.Jsoup;
import org.junit.Test;
import com.farmer.dataanalysis.bean.spiderBeans.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsSpider {
    @Test
    public void getNews() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currentPage","1");
        jsonObject.put("pageSize","15");
        jsonObject.put("stateCode","3");
        String html = HttpUtil.sendJsonAndGetHtml("http://pfsc.agri.cn/price_portal/web/portal-price-information/selectListByPage",
                jsonObject,"UTF-8");
        String info = Jsoup.parse(html).select("body").text();
        NewsBean newsBean = JSON.parseObject(info, NewsBean.class);
        List<NewsRecord> data = newsBean.getData().getRecords();
        List<String> idList = new ArrayList<>();
        for (NewsRecord record : data){
            idList.add(record.getId());
        }
        List<NewsData>nb2 = new ArrayList<>();
        for (String id : idList){
            String html2 = HttpUtil.getHtmlByPost2(
                    "http://pfsc.agri.cn/price_portal/web/portal-price-information/detail","id",id);
            String info2 = Jsoup.parse(html2).select("body").text();
            NewsData nb = JSON.parseObject(info2, NewsBean.class).getData();
            if(Objects.equals(nb.getPublishDate(), TimeUtil.format(System.currentTimeMillis(), "yyyy-MM-dd"))){
                System.out.println(nb.getPublishDate());
                nb2.add(nb);
            }
        }
        File filePath = new File("C:\\Users\\黄文敬\\Desktop\\爬虫\\爬取数据\\"
                + TimeUtil.format(System.currentTimeMillis(),"yyyyMMdd")+"新闻.csv");
        CsvUtil.export(filePath,nb2);//将爬取到的数据封装到CSV中，默认没有第一行的数据说明
    }
}
