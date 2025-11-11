package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;

public class LoginTests extends BaseTest {
    private String passwordForAllUsers = "secret_sauce";
    private String invalidPassword = "wrong_password";
    private String expectedMessage = "Epic sadface: Username and password do not match any user in this service";
    private String expectedMessageEmptyFields = "Epic sadface: Username is required";
    private String expectedMessageEmptyPassword = "Epic sadface: Password is required";

    @DataProvider(name = "validUsers")
    public Object[][] provideUsers() {
        return new Object[][]{
                {"standard_user"},
                {"problem_user"},
                {"performance_glitch_user"},
                {"error_user"},
                {"visual_user"}
        };
    }

    @Test(priority = 1, dataProvider = "validUsers")
    public void positiveLoginTest(String username) {
        InventoryPage inventoryPage = loginPage.loginAs(username, passwordForAllUsers);

        Assert.assertTrue(inventoryPage.isPageOpened(),
                "Inventory page did not open for user: " + username);
        Assert.assertTrue(inventoryPage.isBurgerMenuVisible(),
                "Burger menu is not visible for user: " + username);
    }

    @Test(priority = 2, dataProvider = "validUsers")
    public void negativeLoginTest(String username) {
        loginPage.loginAs(username, invalidPassword);
        Assert.assertEquals(loginPage.getErrorMessage(), expectedMessage,
                "The message is not displayed for user: " + username);
    }

    @Test(priority = 3)
    public void negativeLogin_emptyFields() {
        loginPage.clickLogin();

        Assert.assertEquals(loginPage.getErrorMessage(), expectedMessageEmptyFields,
                "Wrong error message for empty fields.");
    }

    @Test(priority = 4, dataProvider = "validUsers")
    public void negativeLogin_emptyPassword(String username) {
        loginPage.loginAs(username, "");
        Assert.assertEquals(loginPage.getErrorMessage(), expectedMessageEmptyPassword,
                "The message is not displayed for user: " + username);
    }
}
