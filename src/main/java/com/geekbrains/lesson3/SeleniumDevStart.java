package com.geekbrains.lesson3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumDevStart {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.ru/");
        Thread.sleep(5000);
        driver.quit();

//        WebDriverManager.chromedriver().setup();
//        WebDriver ffDriver = new FirefoxDriver();
//        ffDriver.get("https://ya.ru");
//        Thread.sleep(5000);
//        ffDriver.quit();
    }
}
