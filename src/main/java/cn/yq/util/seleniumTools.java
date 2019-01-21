package cn.yq.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class seleniumTools {
    /**
     * 强制睡眠，输入睡眠时间
     *
     * @param time
     */
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }

    /**
     * 传入流浪器头和js字符串  执行js
     *
     * @param driver
     * @param js
     */
    public static void executeJS(WebDriver driver, String js) {
        ((JavascriptExecutor) driver).executeScript(js);
    }

    /**
     * 接受浏览器头，拖动页面到底部
     *
     * @param driver
     */
    public static void toBotton(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * 接受一个浏览器头和需要点击的元素的路径方式by【findElement（）】
     *
     * @param driver
     * @param by
     */
    public static void JsClick(WebDriver driver, By by) {
        WebElement element = driver.findElement(by);
        try {
            if (element.isEnabled() && element.isDisplayed()) {
                System.out.println("使用JS进行页面元素单击");
                //执行JS语句arguments[0].click();
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            } else {
                System.out.println("页面上元素无法进行单击操作");
            }
        } catch (StaleElementReferenceException e) {
            System.out.println("页面元素没有附加在页面中" + Arrays.toString(e.getStackTrace()));
        } catch (NoSuchElementException e) {
            System.out.println("在页面中没有找到要操作的元素" + Arrays.toString(e.getStackTrace()));
        } catch (Exception e) {
            System.out.println("无法完成单击操作" + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * 判断页面是否存在该元素
     *
     * @param driver
     * @param by
     * @return
     */
    public static boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否有弹窗错误
     *
     * @param driver
     * @return
     */
    public static boolean havePopUp(WebDriver driver) {
        if (isElementPresent(driver, By.xpath("/html/body/article/article[2]/section/section[1]"))) {
            System.out.println("Message:" + driver.findElement(By.xpath("/html/body/article/article[2]/section/section[1]")).getText());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回chrome的浏览器头
     *
     * @return
     */
    public static WebDriver ChromeDriver() {
        WebDriver driver;  //浏览器头
        Map<String, String> mobileEmulation = new HashMap<String, String>();
        //mobileEmulation.put("deviceName", "iPhone 6/7/8");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
         chromeOptions.addArguments("--start-maximized");
        //无头浏览器
//        chromeOptions.addArguments("headless");
        //去除自动控制
        chromeOptions.addArguments("disable-infobars");
        //加载默认配置信息
        chromeOptions.addArguments("user-data-dir=C:/Users/sx_yeqiang/AppData/Local/Google/Chrome/User Data");
        driver = new ChromeDriver(chromeOptions);
        return driver;
    }

    /**
     * 返回firefox的浏览器头
     *
     * @return
     */
    public static WebDriver getFireFox() {
        WebDriver driver;
        System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
        System.setProperty("webdriver.gecko.driver", "C:\\Program Files (x86)\\Mozilla Firefox\\geckodriver.exe");
        //设置隐性等待时间
        driver = new FirefoxDriver();
        return driver;
    }

    /**
     *
     * @param driver
     * @param name  截图的名字
     */
    public static void ScreenShot(WebDriver driver,String name){
        //截图到output
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String savePath = "C:\\Users\\sx_yeqiang\\Downloads\\AutoFramework\\screenshot\\"+name +".png";
            //复制内容到指定文件中
            FileUtils.copyFile(scrFile, new File(savePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private void injectjQueryIfNeeded(WebDriver driver) {
        if(!jQueryLoaded(driver))
            injectjQuery(driver);
    }


    // 判断是已加载jQuery
    public Boolean jQueryLoaded(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        Boolean loaded;
        try{
            loaded = (Boolean) jse.executeScript("return "+ "jQuery()!=null");
        } catch(WebDriverException e) {
            loaded = false;
        }
        return loaded;
    }
    // 通过注入jQuery
    public void injectjQuery(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(" var headID = "

                + "document.getElementsByTagName(\"head\")[0];"
                + "var newScript = document.createElement('script');"
                + "newScript.type = 'text/javascript';" + "newScript.src = "
                + "'http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js';"
                + "headID.appendChild(newScript);");
    }
}

