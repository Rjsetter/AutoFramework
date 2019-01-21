package cn.yq.tests.分享演示;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class 打开百度 {
    public static void main(String []args){
    WebDriver driver ;
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--start-maximized");
    driver = new ChromeDriver(chromeOptions);

    driver.get("https://www.baidu.com");
    //getTitle()获取当前页面的title，用System.out.println()打印在控制台
    System.out.println("当前打开页面的标题是： "+ driver.getTitle());
    }
}
