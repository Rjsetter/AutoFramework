package cn.yq.tests.手网出单;


import cn.yq.base.TestBase;
import cn.yq.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.yq.util.seleniumTools.*;

public class 异地旧车过户 extends TestBase {
    String cityName = "上海市";        //城市
    String provinceJC = "渝";        //省份简称
    int year = 2016;    //车初登年份
    int month = 6;      //车初登月份
    int day = 12;       //车初登日
    private static WebDriver driver;  //浏览器头
    private String vehicleNo = getEngineNumber.getEngineNo(6); //生成随机车牌
    private String vinNo = getVin.getRandomVin();                   //生成随机车架号
    private String engineNo = getEngineNumber.getEngineNo(10); //生成随机发动机号
    private String IDCard = IdCardGenerator.generate();    //随机生成身份证号
    private String name = getPersonInfo.getChineseName();   //随机生成姓名
    private String phone = getPersonInfo.getTel();    //随机生成手机号
    private String email = getPersonInfo.getEmail(8, 10);//随机生成邮箱地址
    private String address = getPersonInfo.getRoad(); //随机生成街道地址
    private int thisYear = 2019;    //当前年份，用于后面日期选择
    private String carName = "康迪SMA7001BEV04纯电动轿车  2015款 K17纯电动";
    TestBase testBase;
    String host;
    String url;


    @BeforeClass
    public void setUp() throws Exception {
        testBase = new TestBase();
        host = prop.getProperty("hostTest");
        url = host + "/open/activity/chezheng/index?business_id=czcx";
    }

    @Test
    public void printInfo() {
        System.out.println("----------生成测试信息如下----------");
        System.out.println("姓名：\t" + name);
        System.out.println("电话：\t" + phone);
        System.out.println("身份证:\t" + IDCard);
        System.out.println("邮件：\t" + email);
        System.out.println("住址：\t" + address);
        System.out.println("车牌：\t" + provinceJC + vehicleNo);
        System.out.println("车架号：\t" + vinNo);
        System.out.println("发动机：\t" + engineNo);
        System.out.println("车登记日期：" + year + "-" + month + "-" + day);
    }

    //打开且模拟手机
    @Test
    public void case1() {
        //模拟手机发送请求
        Map<String, String> mobileEmulation = new HashMap<String, String>();
        //mobileEmulation.put("deviceName", "iPhone 6/7/8");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        //启动最大化
        chromeOptions.addArguments("--start-maximized");
        //去除自动控制
        chromeOptions.addArguments("disable-infobars");
        //加载默认配置信息
//        chromeOptions.addArguments("user-data-dir=C:/Users/sx_yeqiang/AppData/Local/Google/Chrome/User Data");
        //无头浏览器
        //chromeOptions.addArguments("headless");
        Map<String, Object> prefs = new HashMap<String, Object>();
        //禁止图片
        prefs.put("profile.managed_default_content_settings.images", 1);
        //禁止Css
        prefs.put("profile.managed_default_content_settings.css", 2); // 2就是代表禁止加载的意思
        chromeOptions.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(chromeOptions);
        System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
        System.setProperty("webdriver.gecko.driver", "C:\\Program Files (x86)\\Mozilla Firefox\\geckodriver.exe");
        //设置隐性等待时间
//        FirefoxBinary firefoxBinary = new FirefoxBinary();
//        firefoxBinary.addCommandLineOptions("--headless");
//        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(url);
    }

    //选择城市
    @Test(dependsOnMethods = "case1")
    public void case2() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //1.点击请选择投保城市
        driver.findElement(By.xpath("/html/body/article/article/div[1]/section[1]/section[3]/div[2]/i")).click();
        //2.输入城市名
        driver.findElement(By.name("searchCityText")).sendKeys(cityName);
        //3.选择城市
        driver.findElement(By.xpath("/html/body/article/article/article/section[2]/ul/li")).click();
    }

    //选择省份简称
    @Test(dependsOnMethods = "case2")
    public void case3() {
        sleep(3000);
        driver.findElement(By.xpath("/html/body/article/article/div[1]/section[1]/section[4]/div[2]/i")).click();
        //返回简称xpath的地址
        GetXpath p = new GetXpath();
        String url = p.getPath(provinceJC);
        sleep(1000);
        driver.findElement(By.xpath(url)).click();
    }

    //输入车牌
    @Test(dependsOnMethods = "case3")
    public void case4() {
        driver.findElement(By.name("licenseNo")).sendKeys(vehicleNo);
    }

    //点击立即报价按钮
    @Test(dependsOnMethods = "case4")
    public void case5() {
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/div[1]/section[2]/button")).click();
    }

    //异地投保
    @Test(dependsOnMethods = "case4")
    public void ydtb() {
        sleep(1000);
        if (havePopUp(driver)) {
            driver.findElement(By.xpath("/html/body/article/article[2]/section/section[2]/div[2]")).click();
        }
    }

    //输入车架号和发动机号
    @Test(dependsOnMethods = "ydtb")
    public void case6() {
        toBotton(driver);
        sleep(1000);
        driver.findElement(By.name("vehicleFrameNo")).sendKeys(vinNo); //输入车架号
        driver.findElement(By.name("vehicleEngineNo")).sendKeys(engineNo);//输入车牌号
    }

    //选择日期
    @Test(dependsOnMethods = "case6")
    public void case7() {
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/section[3]/section[4]/div[2]")).click();//点击初登日期请选择
        //判断年份是否为今年,且年份还须小于今年
        if (year != thisYear && year < thisYear) {
            int clickCount = thisYear - year;  //记录点击次数
            while (clickCount > 0) {
                driver.findElement(By.xpath("/html/body/article/article/article[1]/section/section[1]/div[1]/i[2]")).click();
                sleep(1000);
                clickCount--;
            }
        }
        //月份
        new Actions(driver).moveToElement(driver.findElement(By.xpath("/html/body/article/article/article[1]/section/section[1]/div[2]"))).perform();
        sleep(1000);
        String monthXpath = GetXpath.getMonthXpath(month);
        driver.findElement(By.xpath(monthXpath)).click();
        //日
        sleep(1000);
        String dayXpath = GetXpath.getDayXpath(year, month, day);
        driver.findElement(By.xpath(dayXpath)).click();
        Assert.assertEquals(false, havePopUp(driver));
    }

    //输入个人信息
    @Test(dependsOnMethods = "case7")
    public void case8() {
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/section[4]/section[4]/div[2]/input")).sendKeys(IDCard);
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/section[4]/section[5]/div[2]/input")).sendKeys(name);
        driver.findElement(By.xpath("/html/body/article/article/section[4]/section[9]/div[2]/input")).sendKeys(phone);
    }

    //过户车
    @Test(dependsOnMethods = "case8")
    public void guohuche() {
        toBotton(driver);
        driver.findElement(By.xpath("/html/body/article/article/section[5]/section[6]/section/div/i")).click();
        sleep(400);
        driver.findElement(By.xpath("/html/body/article/article/section[5]/section[7]/div[2]")).click();
        sleep(400);
        String dayXpath = GetXpath.getDayXpath(2019, 1, 7);
        driver.findElement(By.xpath(dayXpath)).click();
        sleep(400);
    }

    //选择车辆
    @Test(dependsOnMethods = "guohuche")
    public void case9() {
        //点击选择框
        driver.findElement(By.xpath("/html/body/article/article/section[6]/div")).click();
        sleep(1000);
        //输入信息
        driver.findElement(By.xpath("/html/body/article/article/article/section/form/input[1]")).sendKeys(carName);
        driver.findElement(By.xpath("/html/body/article/article/article/section/form/input[2]"));
        sleep(1000);
        driver.switchTo().activeElement().click();
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/article/section/form/input[2]")).click();
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/section[2]/article/section/ul/li")).click();
        //确认车辆信息按钮
        sleep(1000);
        //立即报价按钮
        driver.findElement(By.xpath("/html/body/article/article/section[6]/div")).click();
        sleep(1000);

    }

    //短信验证并进行报价
    @Test(dependsOnMethods = "case9")
    public void case10() {
        //短信验证
        driver.findElement(By.xpath("/html/body/article/article[2]/section/section[2]/div[3]")).click();
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article[2]/section/section[2]/div[2]/input")).sendKeys("123456");
        sleep(1000);
        driver.findElement(By.xpath(" /html/body/article/article[2]/section/section[3]/div")).click();//点击报价
        //判断是否报错
    }

    //投保方案确认
    @Test(dependsOnMethods = "case10")
    public void case11() {
        //判断是否会有把报错
        sleep(5000);
        if (isElementPresent(driver, By.xpath("/html/body/article/article[2]/section/section[1]"))) {
            System.out.println("ErrorMessage:" + driver.findElement(By.xpath("/html/body/article/article[2]/section/section[1]")).getText());
            //点击确认按钮
            driver.findElement(By.xpath("/html/body/article/article[2]/section/section[2]")).click();
        }
        sleep(1000);
        //确认报价按钮
        driver.findElement(By.xpath("/html/body/article/article/footer/div[3]")).click();
        sleep(1000);
        //亲，请确认如下保险日期是否准确:
        driver.findElement(By.xpath(" /html/body/article/article[2]/section/section[2]/div[2]")).click();
    }

    //确认订单信息
    @Test(dependsOnMethods = "case11")
    public void case12() {
        toBotton(driver);
        //邮箱
        driver.findElement(By.name("ownerEmail")).sendKeys(email);
        //收件人地址
        sleep(1000);
        //拖动滚动条到最底部
        driver.findElement(By.xpath("/html/body/article/article/section[8]/section[1]/section[3]/div[2]/div[1]")).click();
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/article[2]/section/header/span[1]")).click();
        sleep(1000);
        driver.findElement(By.name("deliverDetailAddress")).sendKeys(address);
        //确认条款
        toBotton(driver);
        driver.findElement(By.xpath("/html/body/article/article/section[11]/section[1]/section/div[2]/i")).click();
        //确定订单
        driver.findElement(By.xpath("/html/body/article/article/section[13]/div[2]")).click();
        sleep(3000);
        Assert.assertEquals(false, havePopUp(driver));
    }

    @Test(dependsOnMethods = "case12")
    public void pay() {
        System.out.println("订单号：" + driver.findElement(By.xpath("//*[@id=\"payForm\"]/ul[1]/li[1]/a/div[2]/div/strong")).getText());
        driver.findElement(By.xpath("//*[@id=\"payForm\"]/ul[3]/li[3]/div/label/i")).click();
        driver.findElement(By.id("payBtn")).click();
        sleep(2000);
        //确认付款
        driver.findElement(By.xpath("//*[@id=\"cashierPreConfirm\"]/div[2]/button")).click();
        sleep(1000);
        //输入密码
        driver.findElement(By.xpath("//*[@id=\"spwd_unencrypt\"]")).sendKeys("543604");
        sleep(3000);
        //完成
        //投保资料上传
        sleep(2000);
        driver.findElement(By.xpath("/html/body/article/article/section[1]/section/section/section[2]/section[1]/div/div")).click();
        sleep(2000);
        System.out.println(driver.findElement(By.xpath("/html/body/article/article/section/div[1]/section/div[2]/p[1]")).getText());
        System.out.println(driver.findElement(By.xpath("/html/body/article/article/section/div[1]/section/div[2]/p[2]")).getText());
    }


//    @Test(dependsOnMethods = "case12")
//    public void 电子签名前置(){
//        try {
//            Robot robot = new Robot();
//            robot.keyPress(KeyEvent.VK_SHIFT);
//            robot.keyPress(KeyEvent.VK_CONTROL);
//            robot.keyPress(KeyEvent.VK_I);
//            Thread.sleep(1000);
//            robot.keyRelease(KeyEvent.VK_I);;
//            robot.keyPress(KeyEvent.VK_M);
//            robot.keyRelease(KeyEvent.VK_CONTROL);
//            robot.keyRelease(KeyEvent.VK_SHIFT);
//            robot.keyRelease(KeyEvent.VK_M);
//            Thread.sleep(10000);
//            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
//            //确认上述内容，签字
//            Thread.sleep(3000);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        driver.findElement(By.xpath("/html/body/article/article/footer/div")).click();
//    }


}
