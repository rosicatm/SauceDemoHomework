package saucedemotests;

import core.BaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductTests extends BaseClass {

    @Test
    public void productAddedToShoppingCart_when_addToCart() {

        WebElement backpack = getProductByTitle(TITLE_BACKPACK);
        backpack.findElement(By.className(BTN_INVENTORY)).click();

        var tshirt = getProductByTitle(TITLE_SHIRT);
        tshirt.findElement(By.className(BTN_INVENTORY)).click();

        driver.findElement(By.className(SHOPPING_CART_LINK)).click();

        var items = driver.findElements(By.className(INVENTORY_ITEM_NAME));

        Assertions.assertEquals(2, items.size(), ITEMS_COUNT_NOT_AS_EXPECTED);

        Assertions.assertEquals(TITLE_BACKPACK, items.get(0).getText(), ITEM_TITLE_NOT_AS_EXPECTED);
        Assertions.assertEquals(TITLE_SHIRT, items.get(1).getText(), ITEM_TITLE_NOT_AS_EXPECTED);
    }

    @Test
    public void userDetailsAdded_when_checkoutWithValidInformation() {

        WebElement backpack = getProductByTitle(TITLE_BACKPACK);
        backpack.findElement(By.className(BTN_INVENTORY)).click();

        var tshirt = getProductByTitle(TITLE_SHIRT);
        tshirt.findElement(By.className(BTN_INVENTORY)).click();

        driver.findElement(By.className(SHOPPING_CART_LINK)).click();

        driver.findElement(By.id(CHECKOUT_ID)).click();

        fillShippingDetails("Fname", "lname", "zip");

        driver.findElement(By.id(CONTINUE_ID)).click();

        var items = driver.findElements(By.className(INVENTORY_ITEM_NAME));
        Assertions.assertEquals(2, items.size(), ITEMS_COUNT_NOT_AS_EXPECTED);

        var total = driver.findElement(By.className(SUMMARY_TOTAL_LABEL)).getText();
        double expectedPrice = BACKPACK_PRICE + TSHIRT_PRICE + PLUS_TAXES;
        String expectedTotal = String.format("Total: $%.2f", expectedPrice);

        Assertions.assertEquals(2, items.size(), ITEMS_COUNT_NOT_AS_EXPECTED);
        Assertions.assertEquals(TITLE_BACKPACK, items.get(0).getText(), ITEM_TITLE_NOT_AS_EXPECTED);
        Assertions.assertEquals(TITLE_SHIRT, items.get(1).getText(), ITEM_TITLE_NOT_AS_EXPECTED);
        Assertions.assertEquals(expectedTotal, total, ITEMS_TOTAL_PRICE_NOT_AS_EXPECTED);
    }

    @Test
    public void orderCompleted_when_addProduct_and_checkout_withConfirm() {

        WebElement backpack = addProductInCart(TITLE_BACKPACK);

        backpack.findElement(By.className(BTN_INVENTORY)).click();

        WebElement tShirt = addProductInCart(TITLE_SHIRT);

        tShirt.findElement(By.className(BTN_INVENTORY)).click();

        driver.findElement(By.className(SHOPPING_CART_LINK)).click();

        driver.findElement(By.id(CHECKOUT_ID)).click();

        fillShippingDetails("Ivan", "Ivanov", "1000");

        driver.findElement(By.xpath(INPUT_DATA_TEST_CONTINUE)).click();
        driver.findElement(By.id(FINISH_ID)).click();

        driver.findElement(By.id(BACK_TO_PRODUCTS_ID)).click();

        WebElement shoppingCartLink = driver.findElement(By.xpath(SHOPPING_CARD_LINK_XPATH));
        Assertions.assertTrue(shoppingCartLink.getText().isEmpty(), CART_IS_NOT_EMPTY);
    }
}
