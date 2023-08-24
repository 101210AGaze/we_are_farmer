package com.farmer.dataanalysis.Utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public abstract class CaptchaPictureUtil {

    /**
     * @since 2023/8/6
     * @author HWJ
     * @DESC 针对所爬取的网页特地做的爬虫工具类，因为爬虫的过程中会出现验证码，而验证码是输入了搜索参数之后才会出现
     * @param url
     * @param filterName
     * @param eudName
     * @param outputDir
     */
    public static String getCaptchaPicture(String url,String filterName,String eudName,File outputDir,String pythonScriptPath){
        String html = null;
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();

        //通过配置参数禁止data;的出现,不会弹出浏览器，默认是后台静默运行
        //options.addArguments("--headless","--disable-gpu");
        //最大化浏览器窗口，否则location定位可能不准
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");


        // 创建 ChromeDriver 对象，同时传入 ChromeOptions 对象
        WebDriver driver = new ChromeDriver(options);

        driver.get(url);
        doSearch(driver, filterName, eudName);
        BufferedImage image = getImage(driver);
        try {
            ImageIO.write(image, "png", new File(outputDir,"captcha.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String captcha = CaptchaPictureUtil.pythonGetCaptcha(pythonScriptPath, outputDir.getPath()+"/captcha.png");
        driver.findElement(By.id("dynamic_imgcode")).sendKeys(captcha);
        driver.findElement(By.id("dynamic_submit")).click();
        try {
            Thread.sleep(5000);
            html = driver.getPageSource();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.quit(); // 一定要退出！不退出会有残留进程！
        return html;
    }

    /**
     * @since 2023/8/6
     * @author HWJ
     * @DESC 针对所爬取的网站让selenium做的模拟浏览器发起的请求，目的是为了触发网页弹出验证码
     * @param driver
     * @param filterName
     * @param eudName
     */
    public static void doSearch(WebDriver driver, String filterName, String eudName) {
        //两个搜索参数
        driver.findElement(By.id("filterName")).sendKeys(filterName);
        driver.findElement(By.id("eudName")).sendKeys(eudName);
        //触发"搜索"按钮的点击事件
        driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[1]/form/div/div[5]/input")).click();
    }

    public static BufferedImage getImage(WebDriver driver){
        WebElement img = driver.findElement(By.id("dynamic_img_code"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Point location = img.getLocation();
        System.out.println(location);
        Dimension size = img.getSize();
        System.out.println(size);
        //这里统一乘1.25是因为电脑的缩放比是125%，乘1.25能让location的定位精确
        int left = (int) (location.getX() * 1.25);
        int top = (int) (location.getY() * 1.25);
        int right = (int) (left + size.getWidth() * 1.25);
        int bottom = (int) (top + size.getHeight() * 1.25);
        File screenshot = ((ChromeDriver) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
        BufferedImage fullImage = null;
        try {
            fullImage = ImageIO.read(screenshot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fullImage.getSubimage(left, top, right - left, bottom - top); // 得到的就是验证码
    }

    /**
     * @since 2023/8/6
     * @DESC 使用py脚本解析验证码图片获取验证码
     * @param pythonScriptPath
     * @param imagePath
     * @return captcha
     */
    public static String pythonGetCaptcha(String pythonScriptPath,String imagePath){
        String captcha = null;
        try {
            // 1. 构建命令行执行的命令
            String[] command = {"python", pythonScriptPath, imagePath};
            // 2. 执行命令
            Process process = Runtime.getRuntime().exec(command);

            // 3. 获取命令输出
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder captchaBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                captchaBuilder.append(line);
            }
            captcha = captchaBuilder.toString();
            inputStream.close();
            reader.close();

            // 4. 等待命令执行完毕并获取返回值
        } catch (IOException e) {
            e.printStackTrace();
        }
        return captcha;
    }
}

