package com.farmer.dataanalysis.spider;

import com.farmer.dataanalysis.Utils.CaptchaPictureUtil;
import org.junit.Test;

public class pythonTest {
    @Test
    public void test(){
        String pythonScriptPath = "C:\\Java_project\\SpiderTest\\src\\main\\java\\com\\spidertest\\spider\\pythonTest.py";
        String imagePath = "C:\\Java_project\\SpiderTest\\src\\main\\java\\com\\spidertest\\captchaPictures\\captcha.png";
        String s = CaptchaPictureUtil.pythonGetCaptcha(pythonScriptPath, imagePath);
        System.out.println(s);
    }



}
