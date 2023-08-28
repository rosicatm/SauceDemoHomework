package core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseClass {

    public static final String URL = "https://www.saucedemo.com/";
    public static final String INPUT_DATA_TEST_USERNAME = "//input[@data-test='username']";
    public static final String INPUT_DATA_TEST_PASSWORD = "//input[@data-test='password']";
    public static final String INPUT_DATA_TEST_LOGIN_BUTTON = "//input[@data-test='login-button']";
    public static final String GET_PRODUCT_BY_TITLE = "//div[@class='inventory_item' and descendant::div[text()='%s']]";
    public static final String ADD_PRODUCT_IN_CART = "//div[@class='inventory_item' and descendant::div[text()='%s']]";
    public static final String FIRST_NAME = "first-name";
    public static final String LAST_NAME = "last-name";
    public static final String POSTAL_CODE = "postal-code";
    public static WebDriver driver;

    public static WebDriverWait wait;

    public static final String TITLE_BACKPACK = "Sauce Labs Backpack";
    public static final String TITLE_SHIRT = "Sauce Labs Bolt T-Shirt";

    public static final double BACKPACK_PRICE = 29.99;
    public static final double TSHIRT_PRICE = 15.99;
    public static final double PLUS_TAXES = 3.68;

    public static final String APP_LOGO = "//div[@class='app_logo']";
    public static final String HEADER_IS_NOT_VISIBLE_AFTER_LOGIN = "Header is not visible after login.";

    public static final String ITEMS_COUNT_NOT_AS_EXPECTED = "Items count not as expected";
    public static final String ITEM_TITLE_NOT_AS_EXPECTED = "Item title not as expected";
    public static final String ITEMS_TOTAL_PRICE_NOT_AS_EXPECTED = "Items total price not as expected";
    public static final String BTN_INVENTORY = "btn_inventory";
    public static final String SHOPPING_CART_LINK = "shopping_cart_link";
    public static final String INVENTORY_ITEM_NAME = "inventory_item_name";
    public static final String CHECKOUT_ID = "checkout";
    public static final String CONTINUE_ID = "continue";
    public static final String SUMMARY_TOTAL_LABEL = "summary_total_label";
    public static final String FINISH_ID = "finish";
    public static final String BACK_TO_PRODUCTS_ID = "back-to-products";

    public static final String INPUT_DATA_TEST_CONTINUE = "//input[@data-test='continue']";
    public static final String SHOPPING_CARD_LINK_XPATH = "//div/a[@class='shopping_cart_link']";
    public static final String CART_IS_NOT_EMPTY = "Cart is not empty.";

    public enum BrowserTypes {
        FIREFOX,
        CHROME,
        EDGE
    }

    public static WebDriver startBrowser(BrowserTypes browserType) {
        // Setup browser
        switch (browserType) {
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                return new EdgeDriver(edgeOptions);
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                return new ChromeDriver(chromeOptions);
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                return new FirefoxDriver(firefoxOptions);
        }
        return null;
    }


    @BeforeEach
    public void beforeAllTests() {
        driver = startBrowser(BrowserTypes.CHROME);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(URL);
        authenticateWithUser("standard_user", "secret_sauce");
    }

    @AfterEach
    public void classTearDown() {
        driver.close();
    }

    protected static void authenticateWithUser(String username, String pass) {
        WebElement usernameInput = driver.findElement(By.xpath(INPUT_DATA_TEST_USERNAME));
        usernameInput.sendKeys(username);

        WebElement password = driver.findElement(By.xpath(INPUT_DATA_TEST_PASSWORD));
        password.sendKeys(pass);

        WebElement loginButton = driver.findElement(By.xpath(INPUT_DATA_TEST_LOGIN_BUTTON));
        loginButton.click();

        WebElement inventoryPageTitle = driver.findElement(By.xpath(APP_LOGO));
        wait.until(ExpectedConditions.visibilityOf(inventoryPageTitle));
    }

    protected WebElement getProductByTitle(String title) {
        return driver.findElement(By.xpath(String.format(GET_PRODUCT_BY_TITLE, title)));
    }

    protected static void fillShippingDetails(String firstName, String lastName, String zip) {
        driver.findElement(By.id(FIRST_NAME)).sendKeys(firstName);
        driver.findElement(By.id(LAST_NAME)).sendKeys(lastName);
        driver.findElement(By.id(POSTAL_CODE)).sendKeys(zip);
    }

    protected WebElement addProductInCart(String title) {
        return driver.findElement(By.xpath(String.format(GET_PRODUCT_BY_TITLE, title)));
    }
}

