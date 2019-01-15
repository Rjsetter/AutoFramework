package cn.yq.tests.ZA2C;


import cn.yq.base.TestBase;
import cn.yq.util.*;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static cn.yq.util.seleniumTools.*;

public class 自动化出单2C extends TestBase {
    String cityName = "海口市";        //城市
    String provinceJC = "闽";        //省份简称
    String provinceName = "海南省";  //省份
    int year = 2018;    //车初登年份
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
    private int thisYear = 2018;    //当前年份，用于后面日期选择
    private String carName = "康迪SMA7001BEV04纯电动轿车  2015款 K17纯电动";
    private  String orderNum = "";
    TestBase testBase;
    String host;
    String url;

    @BeforeClass
    public void setUp() throws Exception {
        testBase = new TestBase();
        host = prop.getProperty("2CUAT");
        url = host + "";
//        ?business_id=10002537697010&utm_source=ZhiyingAPP&auto_test=true&auto_payment=false
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
        driver = ChromeDriver();
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        driver.get(url);
    }

    //选择城市
    @Test(dependsOnMethods = "case1")
    public void case2() {
        //1.点击请选择投保城市
        driver.findElement(By.xpath("/html/body/article/article/section[1]/section[1]/section/section[1]/section/section[1]/div[2]/i")).click();
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article[2]/section/section[1]/ul/li[1]")).click();
        //获取省份
        sleep(1000);
        String pClassName = GetXpath.get2CProvinceClassName(provinceName);
        //System.out.println(pClassName);
        sleep(1000);
        int height = 200;
        while (!driver.findElement(By.className(pClassName)).isDisplayed()) {
            String js = "var q=document.getElementById('provinceListScroll').scrollTop=" + height;
            executeJS(driver, js);
            height += 100;
            sleep(1000);
        }
        driver.findElement(By.className(pClassName)).click();

        //获取市
        String cityClassName = GetXpath.get2CCityClassName(cityName);
        int height1 = 200;
        while (!driver.findElement(By.className(cityClassName)).isDisplayed()) {
            String js = "var q=document.getElementById('cityListScroll').scrollTop=" + height1;
            executeJS(driver, js);
            height1 += 100;
            sleep(200);
        }
        driver.findElement(By.className(cityClassName)).click();
    }

    //选择省份简称
    @Test(dependsOnMethods = "case2")
    public void case3() {
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/section[1]/section[1]/section/section[1]/section/section[2]/section/div[1]/i[1]")).click();
        //返回简称xpath的地址
        GetXpath p = new GetXpath();
        String url = p.getPath2C(provinceJC);
        sleep(1000);
        //System.out.println(url);
        driver.findElement(By.xpath(url)).click();
        sleep(1000);
        driver.findElement(By.xpath(GetXpath.getKeyBoardXpath('='))).click();
    }

    //输入车牌
    @Test(dependsOnMethods = "case3")
    public void case4() {
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/section[1]/section[1]/section/section[1]/section/section[2]/section/div[2]")).click();
        sleep(1000);
        //清除文本框中本来的placeholder
//        driver.findElement(By.xpath("/html/body/article/article/section[1]/section[1]/section/section[1]/section/section[2]/section/div[2]")).clear();
        for(int i = 0 ;i<5;i++){
            sleep(500);
            driver.findElement(By.xpath(GetXpath.getKeyBoardXpath('-'))).click();
        }
        sleep(3000);
        for (int i = 0; i < vehicleNo.length(); i++) {
            sleep(500);
            String url = GetXpath.getKeyBoardXpath(vehicleNo.charAt(i));
            if (i == 1) {
                //清除输入框本来的占位符
                driver.findElement(By.xpath(GetXpath.getKeyBoardXpath('-'))).click();
                driver.findElement(By.xpath(GetXpath.getKeyBoardXpath('-'))).click();
            }
            driver.findElement(By.xpath(url)).click();
        }
        sleep(500);
        driver.findElement(By.xpath(GetXpath.getKeyBoardXpath('='))).click();
        sleep(1000);
    }

    //点击获取报价按钮
    @Test(dependsOnMethods = "case4")
    public void case5() {
        driver.findElement(By.xpath("/html/body/article/article/section[1]/section[1]/section/section[2]")).click();
    }

    //异地投保
    @Test(dependsOnMethods = "case4")
    public void 异地投保() {
        if (havePopUp(driver)) {
            sleep(1000);
            driver.findElement(By.xpath("/html/body/article/article[2]/section/section/section[2]/div[2]")).click();
        }
    }

    //输入车架号和发动机号
    @Test(dependsOnMethods = "异地投保")
    public void case6() {
        sleep(2000);
        driver.navigate().refresh();
        sleep(1000);
        //输入车架号
        driver.findElement(By.name("vehicleFrameNo")).click();
        //清除占位符
        sleep(1000);
        driver.findElement(By.xpath(GetXpath.getKeyBoardXpath('-'))).click();
        for (int i = 0; i < vinNo.length(); i++) {
            sleep(100);
            String url = GetXpath.getKeyBoardXpath(vinNo.charAt(i));
            driver.findElement(By.xpath(url)).click();
        }
        sleep(1000);
        driver.findElement(By.xpath(GetXpath.getKeyBoardXpath('='))).click();
        sleep(1000);
        //输入发动机号
        driver.findElement(By.xpath("/html/body/article/article/section[4]/section[2]/div[2]")).click();
        for (int i = 0; i < engineNo.length(); i++) {
            sleep(200);
            String url = GetXpath.getKeyBoardXpath(engineNo.charAt(i));
            driver.findElement(By.xpath(url)).click();
        }
        sleep(500);
        driver.findElement(By.xpath(GetXpath.getKeyBoardXpath('='))).click();
        sleep(5000);
    }

    //选择日期
    @Test(dependsOnMethods = "case6")
    public void case7() {
        sleep(1000);
        toBotton(driver);
        //点击初登日期请选择
        driver.findElement(By.cssSelector("body > article > article > section.cm-mg-b.cm-bgc-white.cm-fs-15.cm-mg-b-10.cm-bd-b.cm-bd-dark.cm-lastChild-noBd > section:nth-child(3) > div.cm-flex-1.cm-lh-32.date-ico.cm-cur-ptr.cm-clr-gray")).click();
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/article[1]/section/header/span[1]")).click();
    }

    //输入个人信息
    @Test(dependsOnMethods = "case7")
    public void case8() {
        sleep(1000);
        driver.findElement(By.name("identifyNo")).sendKeys(IDCard);
        sleep(1000);
        driver.findElement(By.name("vehicleOwnerName")).sendKeys(name);
        driver.findElement(By.name("phone")).sendKeys(phone);
//        //发票日期
//        driver.findElement(By.xpath("/html/body/article/article/section[3]/section[8]/div[2]")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.xpath("/html/body/article/article/article[1]/section/header/span[1]")).click();
    }

    //选择车辆
    @Test(dependsOnMethods = "case8")
    public void case9() {
        //点击选择框
        sleep(1000);
        driver.findElement(By.cssSelector("body > article > article > section.cm-mg-b.cm-bgc-white.cm-fs-15.cm-mg-b-10.cm-bd-b.cm-bd-dark.cm-lastChild-noBd > section.cm-min-ht-50.cm-pd-lr-15.cm-bd-b.cm-flex.cm-col-c > div.cm-flex-1.cm-pd-tb-8.cm-aw-r.cm-cur-ptr.cm-lh-32.cm-clr-gray")).click();
        sleep(1000);
        //输入信息
        driver.findElement(By.xpath("/html/body/article/article/article/section/form/input")).sendKeys(carName);
        driver.findElement(By.xpath("/html/body/article/article/article/section/form/input")).sendKeys(Keys.ENTER);
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/section[2]/section/section/ul/li/p[1]")).click();
        sleep(1000);
        //立即报价按钮
        driver.findElement(By.xpath("/html/body/article/article/section[6]/div")).click();
        //确认按钮
        sleep(500);
        driver.findElement(By.xpath("/html/body/article/article/article[2]/section/section[2]")).click();
    }

    //短信验证并进行报价
    @Test(dependsOnMethods = "case9")
    public void case10() {
        //短验证码 获取按钮
        driver.findElement(By.xpath("/html/body/article/article[2]/section/section[2]/div[3]")).click();
        sleep(1000);
        driver.findElement(By.name("code")).sendKeys("123456");
        sleep(1000);
        //点击报价
        driver.findElement(By.xpath("/html/body/article/article[2]/section/section[3]/div")).click();
    }

    //投保方案确认
    @Test(dependsOnMethods = "case10")
    public void case11() {
        //判断是否会有把报错
        sleep(3000);
        if (isElementPresent(driver, By.xpath("/html/body/article/article[2]/section/section[1]"))) {
            System.out.println("ErrorMessage:" + driver.findElement(By.xpath("/html/body/article/article[2]/section/section[1]")).getText());
            //点击确认按钮
            driver.findElement(By.xpath("/html/body/article/article[2]/section/section[2]")).click();
        }
        sleep(1000);
        //确认报价按钮
        driver.findElement(By.xpath("/html/body/article/article/footer/div[2]")).click();
        sleep(1000);
        //亲，请确认如下保险日期是否准确:
        driver.findElement(By.xpath("/html/body/article/article[2]/section/section/section[2]/div[2]")).click();
    }

    //确认订单信息
    @Test(dependsOnMethods = "case11")
    public void case12() {
        sleep(5000);
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        sleep(1000);
        //邮箱
        driver.findElement(By.name("distributionEmail")).sendKeys(email);
        //收件人地址
        sleep(1000);
        driver.findElement(By.cssSelector("body > article > article > section.cm-bgc-white.cm-fs-15.insure-list.cm-bd-b.cm-mg-b-10 > section > section:nth-child(1) > section:nth-child(4) > div.cm-flex-1.cm-pos-rel.cm-flex.cm-flex-col > div.cm-aw-r.cm-cur-ptr.cm-clr-gray")).click();
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/article[1]/section/header/span[1]")).click();
        sleep(1000);
        driver.findElement(By.name("deliverDetailAddress")).sendKeys(address);
        //确认条款
        sleep(1000);
        toBotton(driver);
        driver.findElement(By.cssSelector("body > article > article > section:nth-child(9) > section.cm-ht-50.cm-flex.cm-col-c.cm-pd-l-15.cm-bd-b > section > div > i")).click();
        //确定订单
        driver.findElement(By.xpath("/html/body/article/article/section[10]/div[2]")).click();
        sleep(10000);
        Assert.assertEquals(false, havePopUp(driver));
    }

    @Test(dependsOnMethods = "case12")
    public void 电子签名前置() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_I);
            Thread.sleep(1000);
            robot.keyRelease(KeyEvent.VK_I);
            robot.keyPress(KeyEvent.VK_M);
            Thread.sleep(1000);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_M);
            Thread.sleep(1000);
            driver.navigate().refresh();
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //确认上述内容，签字
        JsClick(driver, By.xpath("/html/body/article/article/section[2]/footer/div"));
    }

    @Test(dependsOnMethods = "电子签名前置")
    public void 签名操作()throws AWTException {
            Point locationPoint;
            Locatable elementLocation;
            System.out.println("走电子投保签名前置流程！");
            sleep(5000);
            List<Double> point = new LinkedList<Double>();
            point = getPoint();
            for (int i = 0; i < name.length(); i++) {
                WebElement canvas = driver.findElement(By.id("signaturePanel"));
                int x = point.get(0).intValue();
                int y = point.get(1).intValue();
                System.out.println(x +","+y);
                System.out.println("-------------0------------");
                //release()表示释放鼠标
                System.out.println("-------------1------------");
                System.out.println(canvas.isEnabled());
                //开始画一条线（前面是起始坐标0.0，后边是终点坐标200.200）
                sleep(1000);
                Robot  robot = new Robot();// 创建Robot对象
                robot.mouseMove(x,y);
                // 按下和释放鼠标左键，选定工程
                robot.mousePress(KeyEvent.BUTTON1_MASK);
                sleep(1000);
                robot.mouseMove(x+30, y+50);
                robot.mouseRelease(KeyEvent.BUTTON1_MASK);
                System.out.println("-------------2------------");
                sleep(1000);
                //点击打钩
                JsClick(driver, By.xpath("/html/body/article/article/article/article/article/section/div[2]/div[2]"));
        }
        sleep(2000);
        JsClick(driver,By.xpath("/html/body/article/article/article/article/section[2]"));
        sleep(1000);
        //完成
        JsClick(driver,By.xpath("/html/body/article/article/section[2]/footer/div"));
        orderNum = driver.findElement(By.xpath("//*[@id=\"payForm\"]/ul[1]/li[1]/a/div[2]/div/strong")).getText();
        System.out.println("订单号："+orderNum);
    }


    @Test(dependsOnMethods = "签名操作")
    public void pay() {
        System.out.println("订单号：" + driver.findElement(By.xpath("//*[@id=\"payForm\"]/ul[1]/li[1]/a/div[2]/div/strong")).getText());
        JsClick(driver,By.xpath("//*[@id=\"payForm\"]/ul[3]/li[3]/div/label/i"));
        sleep(500);
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


    public List<Double> getPoint()
    {
        List<Double> point = new LinkedList<>();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat g_tem = Imgcodecs.imread("D:\\a.jpg");
        Mat g_src = Imgcodecs.imread("D:\\y.jpg");

        int result_rows = g_src.rows() - g_tem.rows() + 1;
        int result_cols = g_src.cols() - g_tem.cols() + 1;
        Mat g_result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
        Imgproc.matchTemplate(g_src, g_tem, g_result, Imgproc.TM_CCORR_NORMED); // 归一化平方差匹配法
        // Imgproc.matchTemplate(g_src, g_tem, g_result,
        // Imgproc.TM_CCOEFF_NORMED); // 归一化相关系数匹配法

        // Imgproc.matchTemplate(g_src, g_tem, g_result, Imgproc.TM_CCOEFF);
        // //
        // 相关系数匹配法：1表示完美的匹配；-1表示最差的匹配。

        // Imgproc.matchTemplate(g_src, g_tem, g_result, Imgproc.TM_CCORR); //
        // 相关匹配法

        // Imgproc.matchTemplate(g_src, g_tem, g_result,Imgproc.TM_SQDIFF); //
        // 平方差匹配法：该方法采用平方差来进行匹配；最好的匹配值为0；匹配越差，匹配值越大。

        // Imgproc.matchTemplate(g_src, g_tem,g_result,Imgproc.TM_CCORR_NORMED);
        // // 归一化相关匹配法
        Core.normalize(g_result, g_result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
        org.opencv.core.Point matchLocation = new org.opencv.core.Point();
        Core.MinMaxLocResult mmlr = Core.minMaxLoc(g_result);

        matchLocation = mmlr.maxLoc; // 此处使用maxLoc还是minLoc取决于使用的匹配算法
        point.add(matchLocation.x);
        point.add(matchLocation.y);
        Imgproc.rectangle(g_src, matchLocation,
                new org.opencv.core.Point(matchLocation.x + g_tem.cols(), matchLocation.y + g_tem.rows()),
                new Scalar(0, 0, 0, 0));

        Imgcodecs.imwrite("D:\\match.jpg", g_src);
        return point;
    }
}
