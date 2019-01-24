package cn.yq.tests.分享演示;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Xpath定位 {
    public static void main(String []args){
        WebDriver driver ;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.baidu.com");
        //getTitle()获取当前页面的title，用System.out.println()打印在控制台
        System.out.println("当前打开页面的标题是： "+ driver.getTitle());
        driver.findElement(By.linkText("新闻")).click();
        //用xpath定位
        String text = driver.findElement(By.xpath("//div[@class='bot-right']/ol/li[1]")).getText();
        System.out.println(text);
        //*[@id="pane-news"]/div/ul/li[1]/strong/a
    }
}
