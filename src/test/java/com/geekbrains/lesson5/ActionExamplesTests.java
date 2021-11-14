package com.geekbrains.lesson5;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.htmlelements.matchers.WebElementMatchers.hasText;
import static ru.yandex.qatools.htmlelements.matchers.WebElementMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.not;


import java.util.List;
import java.util.concurrent.TimeUnit;


public class ActionExamplesTests {
    WebDriver driver;
    WebDriverWait webDriverWait;

    @BeforeAll
    static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void initBrowser() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 5);
        login(driver);
    }

    @Test
    public void dragAndDropTest() throws InterruptedException {
        driver.get("https://crm.geekbrains.space/dashboard");
        driver.findElement(By.xpath("//div[@class='column-manager dropdown']")).click();

        Actions actions = new Actions(driver);
        actions.clickAndHold(driver.findElement(By.xpath("//label[.='Владелец']/ancestor::tr//span")))
                .dragAndDrop(driver.findElement(By.xpath("//label[.='Владелец']/ancestor::tr")),
                        driver.findElement(By.xpath("//label[.='Наименование']/ancestor::tr")))
                .build().perform();

//        Thread.sleep(2000);
        webDriverWait.until(d -> d.findElements(
                By.xpath("//thead[@class='grid-header']//tr[@class='grid-header-row']/th[contains(@class, 'sort')]")))
                .get(0).getText().equals("ВЛАДЕЛЕЦ");

        List<WebElement> headers = driver.findElements(
                By.xpath("//thead[@class='grid-header']//tr[@class='grid-header-row']/th[contains(@class, 'sort')]"));

//        Assertions.assertTrue(headers.get(0).getText().equals("Владелец"));
        Assertions.assertEquals("ВЛАДЕЛЕЦ", headers.get(0).getText());

        //assertThat(headers.get(0), not(isDisplayed()));
        assertThat(headers.get(0), hasText("ВЛАДЕЛЕЦ"));
    }

    @Test
    public void createContact() throws InterruptedException {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//a/span[.='Контрагенты']"))).build().perform();
        driver.findElement(By.xpath("//span[.='Контактные лица']")).click();
        Thread.sleep(6000);
        driver.findElement(By.xpath("//a[.='Создать контактное лицо']")).click();
        Thread.sleep(6000);
        driver.findElement(By.xpath("//input[@name='crm_contact[lastName]']")).sendKeys("test");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='crm_contact[firstName]']")).sendKeys("test");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[.='Укажите организацию']")).click();
        Thread.sleep(8000);
        driver.findElement(By.xpath("//div[text()='12323142342134']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@name='crm_contact[jobTitle]']")).sendKeys("test");
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@data-action='{\"route\":\"crm_contact_view\",\"params\":{\"id\":\"$id\"}}']")).click();
        Thread.sleep(15000);

        WebElement checkMessage = driver.findElement(By.xpath("//div[@class='message']"));
        assertThat(checkMessage.getText(), equalTo("Контактное лицо сохранено"));

        WebElement button = driver.findElement(By.xpath("//div[@class='pull-left btn-group icons-holder']/ancestor::div[@class='pull-right title-buttons-container']"));
        assertThat(button, isDisplayed());
    }

    @Test
    public void createProject() throws InterruptedException {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//span[.='Проекты']/ancestor::a"))).build().perform();
        driver.findElement(By.xpath("//span[.='Мои проекты']")).click();
        Thread.sleep(8000);
        driver.findElement(By.xpath("//a[.='Создать проект']")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//input[contains(@id, 'crm_project_name-uid')]")).sendKeys("TestProject");
        driver.findElement(By.xpath("//span[.='Укажите организацию']")).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("//div[.='12323142342134']")).click();
        Select selectDepartment = new Select(driver.findElement(By.xpath("//select[contains(@id, 'crm_project_businessUnit-uid')]")));
        selectDepartment.selectByVisibleText("Research & Development");
        Select selectCurator = new Select(driver.findElement(By.name("crm_project[curator]")));
        selectCurator.selectByVisibleText("Ким Юрий");
        Select selectBoss = new Select(driver.findElement(By.name("crm_project[rp]")));
        selectBoss.selectByVisibleText("Ямпольский Михаил");
        Select selectManager = new Select(driver.findElement(By.name("crm_project[manager]")));
        selectManager.selectByVisibleText("Прохорова Екатерина");
        driver.findElement(By.xpath("//button[@data-action='{\"route\":\"crm_project_view\",\"params\":{\"id\":\"$id\"}}']")).click();
        Thread.sleep(8000);

        WebElement error = driver.findElement(By.xpath("//span[@class='validation-failed']"));
        assertThat(error, isDisplayed());
}

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    static void login(WebDriver driver) {
        driver.get("https://crm.geekbrains.space/");
        WebElement element = driver.findElement(By.id("prependedInput"));
        element.sendKeys("Applanatest1");
        driver.findElement(By.id("prependedInput2")).sendKeys("Student2020!");
        driver.findElement(By.id("_submit")).click();
    }

}
