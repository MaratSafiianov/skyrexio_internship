package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends BasePage {

    private static final String PATH = "/inventory.html";

    private static final String ADD_TO_CART = "//*[text()='%s']//ancestor::div[@class='inventory_item']//child" +
            "::button[text()='Add to cart']";

    private static final By PAGE_TITLE = By.className("title");
    private static final By BURGER_MENU = By.id("react-burger-menu-btn");

    private static final By PRODUCT_CARD = By.className("inventory_item_description");
    private static final By PRODUCT_NAME = By.className("inventory_item_name");
    private static final By ADD_TO_CART_BUTTON = By.className("btn_inventory");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage open() {
        super.open(PATH);
        return this;
    }

    public boolean isPageOpened() {
        return wait.until(ExpectedConditions.textToBe(PAGE_TITLE, "Products"));
    }

    public boolean isBurgerMenuVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(BURGER_MENU)).isDisplayed();
    }

    public void addToCart(String goodsName) {
        By addToCart = By.xpath(ADD_TO_CART.formatted(goodsName));
        driver.findElement(addToCart).click();
    }

    // добавление всех товаров в корзину
    public List<String> addAllProductsToCart() {
        List<WebElement> products = driver.findElements(PRODUCT_CARD);
        List<String> addedProducts = new ArrayList<>();

        for (WebElement product : products) {
            String name = product.findElement(PRODUCT_NAME).getText();
            product.findElement(ADD_TO_CART_BUTTON).click();
            addedProducts.add(name);
        }

        return addedProducts;
    }
}
