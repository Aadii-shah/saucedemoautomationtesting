package org.example.Cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class verifyProductInCartList {
    public static void main(String[] args) {
        // first we need to logIn to the website. I have used Sys Var to login.
        WebDriver webDriver = new ChromeDriver();
        String url = "https://www.saucedemo.com/";
        webDriver.get(url);
        webDriver.manage().window().maximize();

        String username = System.getenv("MY_USERNAME");
        String password = System.getenv("MY_PASSWORD");
        webDriver.findElement(By.id("user-name")).sendKeys(username);
        webDriver.findElement(By.id("password")).sendKeys(password);
        webDriver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

        //Add any three products to the cart
        sleep(2000);
        addProductToCart(webDriver, "Sauce Labs Backpack");
        sleep(2000);
        addProductToCart(webDriver, "Sauce Labs Bolt T-Shirt");
        sleep(2000);
        addProductToCart(webDriver, "Sauce Labs Onesie");

        //Go to Shopping Cart Link
        goToShoppingCart(webDriver);

        //Verify the product in Cart List
        sleep(2000);
        verifyProductInCart(webDriver, "Sauce Labs Backpack");

    }
    // Helper method to add a product to the cart
    private static void addProductToCart(WebDriver driver, String productName) {
        WebElement productElement = driver.findElement(By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']"));
        WebElement addToCartButton = productElement.findElement(By.xpath(".//button[text()='Add to cart']"));
        addToCartButton.click();
        System.out.println(productName);
    }

    // Helper method to go to the Shopping Cart page
    private static void goToShoppingCart(WebDriver driver) {
        // Locate and click the "Shopping Cart" link
        WebElement shoppingCartLink = driver.findElement(By.className("shopping_cart_link"));
        shoppingCartLink.click();
    }

    // Helper method to verify the presence of a product in the Cart List
    private static void verifyProductInCart(WebDriver driver, String productName) {
        // Locate all product elements in the cart
        List<WebElement> cartProductElements = driver.findElements(By.xpath("//div[@class='cart_item']"));

        for (WebElement cartProductElement : cartProductElements) {
            // Check if the product name in the cart matches the desired product
            String actualProductName = cartProductElement.findElement(By.className("inventory_item_name")).getText();
            if (actualProductName.equals(productName)) {
                System.out.println("Product '" + productName + "' is in the Cart List.");
                return; // Product found, no need to continue searching
            }
        }
        // If the loop completes without finding the product
        System.out.println("Product '" + productName + "' is not in the Cart List.");
    }

    // Helper method to sleep for a specified number of milliseconds
    private static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
