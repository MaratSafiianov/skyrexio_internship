package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;

import java.util.List;
import java.util.Objects;

public class AddToCartTest extends BaseTest {

    private static final String PASSWORD_FOR_ALL_USERS = "secret_sauce";

    @DataProvider(name = "validUsers")
    public Object[][] provideUsers() {
        return new Object[][]{
                {"standard_user"},
                {"locked_out_user"},
                {"problem_user"},
                {"performance_glitch_user"},
                {"error_user"},
                {"visual_user"}
        };
    }

    @Test(priority = 1, dataProvider = "validUsers", testName = "Add all items to cart and check cart")
    public void addAllItems_thenCheckCart(String username) {
        openLoginPage();

        InventoryPage inventoryPage = loginPage.loginAs(username, PASSWORD_FOR_ALL_USERS);

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/inventory.html"), "The user is not logged in");

        List<String> addedItems = inventoryPage.addAllProductsToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.open();

        List<String> itemsInCart = cartPage.getAllItemsNameInCart();
        boolean allExist = itemsInCart.containsAll(addedItems);
        Assert.assertTrue(allExist, "Expected items in cart: " + addedItems.size() + "; actual items in the cart: " + itemsInCart.size());
    }
}
