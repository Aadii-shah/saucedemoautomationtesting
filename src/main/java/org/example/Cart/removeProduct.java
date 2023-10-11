package org.example.Cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class removeProduct {
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

       // Remove any one product added to the cart
        sleep(2000);
        removeProductFromCart(webDriver, "Sauce Labs Bolt T-Shirt");


    }
    // Helper method to add a product to the cart
    private static void addProductToCart(WebDriver driver, String productName) {
        WebElement productElement = driver.findElement(By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']"));
        WebElement addToCartButton = productElement.findElement(By.xpath(".//button[text()='Add to cart']"));
        addToCartButton.click();
        System.out.println(productName);
    }

    // Helper method to remove a product from the cart
    private static void removeProductFromCart(WebDriver driver, String removeProductName) {
        // Open the shopping cart
        WebElement shoppingCartLink = driver.findElement(By.className("shopping_cart_link"));
        shoppingCartLink.click();

        // Locate all product elements in the cart
        List<WebElement> cartProductElements = driver.findElements(By.xpath("//div[@class='cart_item']"));

        for (WebElement cartProductElement : cartProductElements) {
            // Check if the product name in the cart matches the desired product
            String actualProductName = cartProductElement.findElement(By.className("inventory_item_name")).getText();
            if (actualProductName.equals(removeProductName)) {
                // Locate the "Remove" button within the current product element in the cart
                WebElement removeButton = cartProductElement.findElement(By.xpath(".//button[text()='Remove']"));
                sleep(5000);
                removeButton.click();
                System.out.println(removeProductName);
                break; // Stop searching once the product is removed from the cart
            }
        }
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
