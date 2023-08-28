package saucedemotests;

import core.BaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginTests extends BaseClass {

    @BeforeEach
    public void beforeAllTests() {
        driver = startBrowser(BrowserTypes.CHROME);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(URL);
    }

    @Test
    public void userAuthenticated_when_validCredentialsProvided() {
        authenticateWithUser("standard_user", "secret_sauce");
        WebElement inventoryPageTitle = driver.findElement(By.xpath(APP_LOGO));
        Assertions.assertTrue(inventoryPageTitle.isDisplayed(), HEADER_IS_NOT_VISIBLE_AFTER_LOGIN);
    }
}
