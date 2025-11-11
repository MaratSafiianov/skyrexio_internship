package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InventoryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By pageTitle = By.className("title");
    private final By burgerMenu = By.id("react-burger-menu-btn");

    public InventoryPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isPageOpened() {
        return wait.until(ExpectedConditions.textToBe(pageTitle, "Products"));
    }

    public boolean isBurgerMenuVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(burgerMenu)).isDisplayed();
    }
}
