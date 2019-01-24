package cn.yq.tests.爬虫;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static cn.yq.util.seleniumTools.sleep;

public class getCookie {

    public static void main(String [] args)throws InterruptedException{
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("headless");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("http://nsso.zhonganonline.com/login?service=za-idb&error=-301&target=http%3A%2F%2Fidb.zhonganonline.com/");
              sleep(1000);
        System.out.print(driver.getPageSource());
    }
}