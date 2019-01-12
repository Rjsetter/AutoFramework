package cn.yq.tests.others;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestSelenium {
    private static WebDriver driver;
    private int count = 0;

    @DataProvider(name = "Authentication")
    public static Object[] credentials() {
        return new Object[]{"搜狐", "百度", "谷歌", "腾讯"};
    }

    @Test(dataProvider = "Authentication")
    public void test(String userName) {
        count += 1;
        Map<String, String> mobileEmulation = new HashMap<String, String>();
        mobileEmulation.put("deviceName", "iPhone 6/7/8");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.baidu.com/");
        driver.findElement(By.xpath("//*[@id=\"kw\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"kw\"]")).sendKeys(userName);
        driver.findElement(By.id("su")).click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
        System.out.println("正在进行第" + count + "次测试......");
    }

}
