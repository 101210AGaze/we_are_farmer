package com.farmer.dataanalysis.spider;

import com.farmer.dataanalysis.Utils.HttpUtil;
import org.junit.Test;

public class CloudSpider {
    @Test
    public void getCloudData(){
        String html = HttpUtil.getHtmlByGet("http://daping.agdata.cn/Api4Datas/getDatas/2");
        System.out.println(html);
    }
}
