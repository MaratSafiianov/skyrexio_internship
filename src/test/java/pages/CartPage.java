package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    private static final String PATH = "/cart.html";

    private static final By PAGE_TITLE = By.className("title");
    private static final By ITEM_NAME = By.className("inventory_item_name");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage open() {
        super.open(PATH);
        return this;
    }

    public boolean isPageOpened() {
        return wait.until(ExpectedConditions.textToBe(PAGE_TITLE, "Your Cart"));
    }

    public List<String> getAllItemsNameInCart() {
        List<WebElement> items = driver.findElements(ITEM_NAME);
        List<String> names = new ArrayList<>();

        for (WebElement item : items) {
            names.add(item.getText());
        }

        return names;
    }
}
