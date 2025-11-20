package User;

import utils.PropertyReader;

public class UserFactory {

    public static User createStandardUser() {
        return new User(PropertyReader.getProperty("saucedemo.standard_user"), PropertyReader.getProperty("saucedemo.password"));
    }

    public static User createLockedUser() {
        return new User(PropertyReader.getProperty("saucedemo.locked_user"), PropertyReader.getProperty("saucedemo.password"));
    }

    public static User createProblemUser() {
        return new User(PropertyReader.getProperty("saucedemo.problem_user"), PropertyReader.getProperty("saucedemo.password"));
    }

    public static User createPerformanceGlitchUser() {
        return new User(PropertyReader.getProperty("saucedemo.performance_glitch_user"), PropertyReader.getProperty("saucedemo.password"));
    }

    public static User createErrorUser() {
        return new User(PropertyReader.getProperty("saucedemo.error_user"), PropertyReader.getProperty("saucedemo.password"));
    }

    public static User createVisualUser() {
        return new User(PropertyReader.getProperty("saucedemo.visual_user"), PropertyReader.getProperty("saucedemo.password"));
    }
}
