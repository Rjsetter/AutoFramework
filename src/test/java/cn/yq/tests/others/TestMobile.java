package cn.yq.tests.others;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class TestMobile {
    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        // 设置禁止加载项
        Map<String, Integer> p = new HashMap<String, Integer>();
        // 禁止加载js
        p.put("profile.default_content_settings.javascript", 2); // 2就是代表禁止加载的意思
        // 禁止加载css
        p.put("profile.default_content_settings.images", 2); // 2就是代表禁止加载的意思
        //    options.setExperimentalOption("prefs", p);
        //    options.addArguments("headless");
        String url = "user-agent=\"Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1\"";
        options.addArguments("--disable-images");
        options.addArguments("--start-maximized ");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-javascript");
        options.addArguments(url);

        WebDriver driver = new ChromeDriver(options);
        System.out.println("----test1----");
        driver.get("https://c-uat.zhongan.com/insure/index.html?flowId=FJ006356961feb1f2ec&i=e#/");
        ((JavascriptExecutor) driver).executeScript("https://static.zhongan.com/website/public/js/zepto/v1.0/zepto.min.js");
        System.out.println("----test2----");

    }
}
