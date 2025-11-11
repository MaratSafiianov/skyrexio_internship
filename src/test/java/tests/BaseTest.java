package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;

import java.time.Duration;

public class BaseTest {
    WebDriver driver;
    LoginPage loginPage;
    WebDriverWait wait;

    @BeforeClass
    public void setUpClass() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--guest");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    @BeforeMethod
    public void openLoginPage() {
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver, wait);
    }

    @AfterClass
    public void tearDownClass() {
        if (driver != null) {
            driver.quit(); // Закрываем браузер только после всех тестов
        }
    }
}