package com.geekbrains.lesson3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class MailTests {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://mail.ru/");
        login(driver);
        writeLetter(driver);
    }

    static void login(WebDriver driver) {
        driver.findElement((By.xpath("//input[@name='login']"))).sendKeys("study.account3");
        driver.findElement(By.xpath("//button[@data-testid='enter-password']")).click();
        driver.findElement(By.xpath("//input[@autocomplete='current-password']")).sendKeys("onlyforgeek2020-2021");
        driver.findElement(By.xpath("//button[@data-testid=\"login-to-mail\"]")).click();
    }

    static void writeLetter(WebDriver driver) throws InterruptedException {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//a[@title='Написать письмо']"))).build();
        driver.findElement(By.xpath("//*[@href='/compose/']")).click();
        driver.findElement(By.xpath("//input[@tabindex='100']")).sendKeys("study.account@mail.ru");
        driver.findElement((By.xpath("//input[@tabindex='400']"))).sendKeys("test");
        driver.findElement(By.xpath("//div[@tabindex='505']/div[1]")).sendKeys("test");
        driver.findElement(By.xpath("//span[@class='button2 button2_base button2_primary button2_hover-support js-shortcut']")).click();
        Thread.sleep(5000);
        driver.quit();
    }

}
