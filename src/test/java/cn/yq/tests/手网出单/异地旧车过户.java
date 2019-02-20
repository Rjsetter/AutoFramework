package cn.yq.tests.手网出单;


import cn.yq.base.TestBase;
import cn.yq.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.yq.util.seleniumTools.*;

public class 异地旧车过户 extends TestBase {
    String cityName = "重庆市";        //城市
    String provinceJC = "闽";        //省份简称
    int year = 2015;    //车初登年份
    int month = 5;      //车初登月份
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
        host = prop.getProperty("SWUAT");
        url = host + "/open/activity/chezheng/index?business_id=czcx";
        System.out.println(url);
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
    public void 打开网址() {
        driver = ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(url);
    }

    //选择城市
    @Test(dependsOnMethods = "打开网址")
    public void 选择城市() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        toBotton(driver);
        //1.点击请选择投保城市
        driver.findElement(By.xpath("/html/body/article/article/div[1]/section[1]/section[3]/div[2]/i")).click();
        //2.输入城市名
        driver.findElement(By.name("searchCityText")).sendKeys(cityName);
        //3.选择城市
        driver.findElement(By.xpath("/html/body/article/article/article/section[2]/ul/li")).click();
    }

    //选择省份简称
    @Test(dependsOnMethods = "选择城市")
    public void 省份简称() {
        sleep(3000);
        driver.findElement(By.xpath("/html/body/article/article/div[1]/section[1]/section[4]/div[2]/i")).click();
        //返回简称xpath的地址
        GetXpath p = new GetXpath();
        String url = p.getPath(provinceJC);
        sleep(1000);
        driver.findElement(By.xpath(url)).click();
    }

    @Test(dependsOnMethods = "省份简称")
    public void 输入车牌() {
        driver.findElement(By.name("licenseNo")).sendKeys(vehicleNo);
    }

    //点击立即报价按钮
    @Test(dependsOnMethods = "输入车牌")
    public void 报价按钮() {
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/div[1]/section[2]/button")).click();
    }

    //异地投保
    @Test(dependsOnMethods = "报价按钮")
    public void 异地投保() {
        sleep(1000);
        if (havePopUp(driver)) {
            driver.findElement(By.xpath("/html/body/article/article[2]/section/section[2]/div[2]")).click();
        }
    }

    //输入车架号和发动机号
    @Test(dependsOnMethods = "异地投保")
    public void 输入车架和发动机号() {
        sleep(3000);
        new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("vehicleFrameNo")));
        driver.findElement(By.name("vehicleFrameNo")).sendKeys(vinNo); //输入车架号
        new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("vehicleEngineNo")));
        driver.findElement(By.name("vehicleEngineNo")).sendKeys(engineNo);//输入车牌号
    }

    //选择日期
    @Test(dependsOnMethods = "输入车架和发动机号")
    public void 选择日期() {
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
    @Test(dependsOnMethods = "选择日期")
    public void 个人信息() {
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/section[4]/section[4]/div[2]/input")).sendKeys(IDCard);
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article/section[4]/section[5]/div[2]/input")).sendKeys(name);
        driver.findElement(By.xpath("/html/body/article/article/section[4]/section[9]/div[2]/input")).sendKeys(phone);
    }

    //过户车
    @Test(dependsOnMethods = "个人信息")
    public void 过户车() {
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
    @Test(dependsOnMethods = "过户车")
    public void 选择车辆() {
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
    @Test(dependsOnMethods = "选择车辆")
    public void 短信验证() {
        //短信验证
        driver.findElement(By.xpath("/html/body/article/article[2]/section/section[2]/div[3]")).click();
        sleep(1000);
        driver.findElement(By.xpath("/html/body/article/article[2]/section/section[2]/div[2]/input")).sendKeys("123456");
        sleep(1000);
        driver.findElement(By.xpath(" /html/body/article/article[2]/section/section[3]/div")).click();//点击报价
        //判断是否报错
    }

    //投保方案确认
    @Test(dependsOnMethods = "短信验证")
    public void 投保方案确认() {
        //判断是否会有把报错
        sleep(5000);
        if (isElementPresent(driver, By.xpath("/html/body/article/article[2]/section/section[1]"))) {
            System.out.println("ErrorMessage:" + driver.findElement(By.xpath("/html/body/article/article[2]/section/section[1]")).getText());
            //点击确认按钮
            driver.findElement(By.xpath("/html/body/article/article[2]/section/section[2]")).click();
        }
        sleep(2000);
        //确认报价按钮
        driver.findElement(By.xpath("/html/body/article/article/footer/div[3]")).click();
        sleep(1000);
        //亲，请确认如下保险日期是否准确:
        driver.findElement(By.xpath(" /html/body/article/article[2]/section/section[2]/div[2]")).click();
    }

    //确认订单信息
    @Test(dependsOnMethods = "投保方案确认")
    public void 确认订单() {
        toBotton(driver);
        //邮箱
        driver.findElement(By.name("ownerEmail")).sendKeys(email);
        //收件人地址
        sleep(1000);
        //拖动滚动条到最底部
        toBotton(driver);
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


    @Test(dependsOnMethods = "支付操作",enabled = false)
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
        JsClick(driver, By.xpath("/html/body/article/article/footer/div"));
    }

    @Test(dependsOnMethods = "电子签名前置",enabled = false)
    public void 签名操作()throws AWTException ,Exception{
        System.out.println("走电子投保签名前置流程！");
        sleep(5000);
        java.util.List<Double> point = new LinkedList<Double>();
        String address = "D:";
        CaptureScreen.captureScreen(address,"source.jpg");
        sleep(1000);
        for (int i = 0; i < name.length(); i++) {
            int x = point.get(0).intValue();
            int y = point.get(1).intValue();
            System.out.println(x +","+y);
            System.out.println("-------------0------------");
            //release()表示释放鼠标
            System.out.println("-------------1------------");
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
            JsClick(driver, By.xpath("/html/body/article/article/article/article/article/section/div/button[2]"));
        }
        sleep(2000);
        JsClick(driver,By.xpath("/html/body/article/article/article/article/section[2]/button"));
        sleep(2000);
        //完成
        String orderNum = driver.findElement(By.xpath("//*[@id=\"payForm\"]/ul[1]/li[1]/a/div[2]/div/strong")).getText();
        System.out.println("订单号："+orderNum);
    }
//    public function testDraw() {
//        try {
//            $this->execute(array('script' => "  " +
//                            "var c = document.getElementById('canvas');
//                    var ctx = c.getContext('2d');
//            ctx.beginPath();
//            ctx.arc(100, 75, 50, 0, 2 * Math.PI);
//            ctx.stroke();",
//            'args' => array()));
//
//            echo 'done';
//            sleep(10);
//        } catch (Exception $ex) {
//            echo 'not done';
//        }

    @Test(dependsOnMethods = "确认订单")
    public void 支付操作() {
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

//    public List<Double> getPoint()
//    {
//        List<Double> point = new LinkedList<Double>();
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//
//        Mat g_tem = Imgcodecs.imread("C:\\Users\\sx_yeqiang\\Downloads\\AutoFramework\\screenshot\\target.jpg");
//        Mat g_src = Imgcodecs.imread("C:\\Users\\sx_yeqiang\\Downloads\\AutoFramework\\screenshot\\source.jpg");
//
//        int result_rows = g_src.rows() - g_tem.rows() + 1;
//        int result_cols = g_src.cols() - g_tem.cols() + 1;
//        Mat g_result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
//        Imgproc.matchTemplate(g_src, g_tem, g_result, Imgproc.TM_CCORR_NORMED); // 归一化平方差匹配法
//        Core.normalize(g_result, g_result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
//        org.opencv.core.Point matchLocation = new org.opencv.core.Point();
//        Core.MinMaxLocResult mmlr = Core.minMaxLoc(g_result);
//
//        matchLocation = mmlr.maxLoc; // 此处使用maxLoc还是minLoc取决于使用的匹配算法
//        point.add(matchLocation.x);
//        point.add(matchLocation.y);
//        Imgproc.rectangle(g_src, matchLocation,
//                new org.opencv.core.Point(matchLocation.x + g_tem.cols(), matchLocation.y + g_tem.rows()),
//                new Scalar(0, 0, 0, 0));
//
//        Imgcodecs.imwrite("C:\\Users\\sx_yeqiang\\Downloads\\AutoFramework\\screenshot\\match.jpg", g_src);
//        return point;
//    }
}