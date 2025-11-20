package tests;

import User.User;
import User.UserFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;

import java.util.List;
import java.util.Objects;

public class AddToCartTest extends BaseTest {

    @DataProvider(name = "validUsers")
    public Object[][] provideUsers() {
        return new Object[][]{
                {UserFactory.createStandardUser()},
                {UserFactory.createLockedUser()},
                {UserFactory.createProblemUser()},
                {UserFactory.createPerformanceGlitchUser()},
                {UserFactory.createErrorUser()},
                {UserFactory.createVisualUser()}
        };
    }

    @Test(priority = 1, dataProvider = "validUsers", description = "Add all items to cart and check cart")
    public void addAllItems_thenCheckCart(User user) {
        System.out.println("AddToCartTest are running in thread: " + Thread.currentThread().getName());

        openLoginPage();

        InventoryPage inventoryPage = loginPage.loginAs(user);

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/inventory.html"), "The user is not logged in");

        List<String> addedItems = inventoryPage.addAllProductsToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.open();

        List<String> itemsInCart = cartPage.getAllItemsNameInCart();
        boolean allExist = itemsInCart.containsAll(addedItems);
        Assert.assertTrue(allExist, "Expected items in cart: " + addedItems.size() + "; actual items in the cart: " + itemsInCart.size());
    }
}
