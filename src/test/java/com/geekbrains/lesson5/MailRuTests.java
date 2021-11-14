package com.geekbrains.lesson5;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.htmlelements.matchers.WebElementMatchers.isDisplayed;

import java.util.concurrent.TimeUnit;


public class MailRuTests {
    WebDriver driver;
    WebDriverWait webDriverWait;

    @BeforeAll
    static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void initBrowser() throws InterruptedException {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 5);
        login(driver);
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    void writeLetter() throws InterruptedException {
        Actions action = new Actions(driver);

        action.moveToElement(driver.findElement(By.xpath("//a[@title='Написать письмо']"))).build();
        driver.findElement(By.xpath("//*[@href='/compose/']")).click();
        driver.findElement(By.xpath("//input[@tabindex='100']")).sendKeys("study.account@mail.ru");
        driver.findElement((By.xpath("//input[@tabindex='400']"))).sendKeys("test");
        driver.findElement(By.xpath("//div[@tabindex='505']/div[1]")).sendKeys("test");
        driver.findElement(By.xpath("//span[@class='button2 button2_base button2_primary button2_hover-support js-shortcut']")).click();

        WebElement letterSend = driver.findElement(By.xpath("//a[@class='layer__link']"));
        assertThat(letterSend.getText(), equalTo("Письмо отправлено"));
    }

    @Test
    void checkDraft() throws InterruptedException {
        driver.get("https://e.mail.ru/inbox/?back=1");
        Thread.sleep(4000);
        driver.findElement(By.xpath("//a[contains(@title, 'Черновики')]")).click();
        WebElement draft = driver.findElement(By.xpath("//div[@class='portal-menu-element__text']"));
        assertThat(draft, isDisplayed());
    }

    @Test
    void mainPageOpen() {
        driver.findElement(By.xpath("//div[@class='portal-menu-logo__content']")).click();

        WebElement mainPage = driver.findElement(By.xpath("//input[@id ='q']"));
        assertThat(mainPage, isDisplayed());
    }

    @Test
    void filterLetters() throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//div[@class='filters-control filters-control_short filters-control_pure']"))).build().perform();

        driver.findElement(By.xpath("//div[@class='filters-control filters-control_short filters-control_pure']")).click();
        driver.findElement(By.xpath("//span[@class='list-item__text' and contains(.,'Непрочитанные')]")).click();
//        Thread.sleep(2000);

        WebElement notRead = driver.findElement(By.xpath("//span[@class='filters-control__filter-text']"));
        assertThat(notRead.getText(), equalTo("Непрочитанные"));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    static void login(WebDriver driver) throws InterruptedException {
        driver.get("https://mail.ru/");
        driver.findElement((By.xpath("//input[@name='login']"))).sendKeys("study.account3");
        driver.findElement(By.xpath("//button[@data-testid='enter-password']")).click();
        driver.findElement(By.xpath("//input[@autocomplete='current-password']")).sendKeys("onlyforgeek2020-2021");
        driver.findElement(By.xpath("//button[@data-testid=\"login-to-mail\"]")).click();

    }

}
