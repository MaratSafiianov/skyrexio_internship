package tests;

import User.User;
import User.UserFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;
import utils.PropertyReader;

public class LoginTest extends BaseTest {

    private static final String INVALID_PASSWORD = PropertyReader.getProperty("saucedemo.invalid_password");
    private static final String EXPECTED_MESSAGE = "Epic sadface: Username and password do not match any user in this service";
    private static final String EXPECTED_MESSAGE_EMPTY_FIELDS = "Epic sadface: Username is required";
    private static final String EXPECTED_MESSAGE_EMPTY_PASSWORD = "Epic sadface: Password is required";

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

    @Test(priority = 1, dataProvider = "validUsers", description = "Valid login, valid password")
    public void positiveLoginTest(User user) {
        System.out.println("LoginTest are running in thread: " + Thread.currentThread().getName());

        InventoryPage inventoryPage = loginPage.loginAs(user);

        Assert.assertTrue(inventoryPage.isPageOpened(),
                "Inventory page did not open for user: " + user.getUserName());
        Assert.assertTrue(inventoryPage.isBurgerMenuVisible(),
                "Burger menu is not visible for user: " + user.getUserName());
    }

    @Test(priority = 2, dataProvider = "validUsers", description = "Valid login, invalid password")
    public void negativeLoginTest(User user) {
        loginPage.loginAs(user.getUserName(), INVALID_PASSWORD);

        // проверка отображения сообщения
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "The error message is not displayed for user: " + user.getUserName());

        // проверка текста сообщения
        Assert.assertEquals(loginPage.getErrorMessage(), EXPECTED_MESSAGE,
                "The error message text is not correct for user: " + user.getUserName());
    }

    @Test(priority = 3, description = "Empty fields")
    public void negativeLogin_emptyFields() {
        loginPage.clickLogin();

        // проверка отображения сообщения
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "The error message is not displayed");

        // проверка текста сообщения
        Assert.assertEquals(loginPage.getErrorMessage(), EXPECTED_MESSAGE_EMPTY_FIELDS,
                "Wrong error message for empty fields.");
    }

    @Test(priority = 4, dataProvider = "validUsers", description = "Valid login, empty password field")
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
