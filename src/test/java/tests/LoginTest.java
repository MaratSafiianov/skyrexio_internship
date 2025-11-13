package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;

public class LoginTest extends BaseTest {
    private static final String PASSWORD_FOR_ALL_USERS = "secret_sauce";
    private static final String INVALID_PASSWORD = "wrong_password";
    private static final String EXPECTED_MESSAGE = "Epic sadface: Username and password do not match any user in this " +
            "service";
    private static final String EXPECTED_MESSAGE_EMPTY_FIELDS = "Epic sadface: Username is required";
    private static final String EXPECTED_MESSAGE_EMPTY_PASSWORD = "Epic sadface: Password is required";

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

    @Test(priority = 1, dataProvider = "validUsers", testName = "Valid login, valid password")
    public void positiveLoginTest(String username) {
        InventoryPage inventoryPage = loginPage.loginAs(username, PASSWORD_FOR_ALL_USERS);

        Assert.assertTrue(inventoryPage.isPageOpened(),
                "Inventory page did not open for user: " + username);
        Assert.assertTrue(inventoryPage.isBurgerMenuVisible(),
                "Burger menu is not visible for user: " + username);
    }

    @Test(priority = 2, dataProvider = "validUsers", testName = "Valid login, invalid password")
    public void negativeLoginTest(String username) {
        loginPage.loginAs(username, INVALID_PASSWORD);

        // проверка отображения сообщения
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "The error message is not displayed for user: " + username);

        // проверка текста сообщения
        Assert.assertEquals(loginPage.getErrorMessage(), EXPECTED_MESSAGE,
                "The error message text is not correct for user: " + username);
    }

    @Test(priority = 3, testName = "Empty fields")
    public void negativeLogin_emptyFields() {
        loginPage.clickLogin();

        // проверка отображения сообщения
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "The error message is not displayed");

        // проверка текста сообщения
        Assert.assertEquals(loginPage.getErrorMessage(), EXPECTED_MESSAGE_EMPTY_FIELDS,
                "Wrong error message for empty fields.");
    }

    @Test(priority = 4, dataProvider = "validUsers", testName = "Valid login, empty password field")
    public void negativeLogin_emptyPassword(String username) {
        loginPage.loginAs(username, "");

        // проверка отображения сообщения
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "The error message is not displayed for user: " + username);

        // проверка текста сообщения
        Assert.assertEquals(loginPage.getErrorMessage(), EXPECTED_MESSAGE_EMPTY_PASSWORD, "Wrong error message for empty" +
                " password for user: " + username);
    }
}