package org.example.CheckOut;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class checkOutInformationPage {
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


        //Checkout
        sleep(2000);
        checkout(webDriver);

       //Input values in Checkout Information page
        inputCheckoutInformation(webDriver, "Aaditya", "Shah", "44088");

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

    // Helper method to go to the Checkout page
    private static void checkout(WebDriver driver) {
        // Locate and click the "CHECKOUT" button on the Shopping Cart page
        WebElement checkoutButton = driver.findElement(By.xpath("//button[text()='Checkout']"));
        checkoutButton.click();
    }

    // Helper method to input values on the Checkout Information page
    private static void inputCheckoutInformation(WebDriver driver, String firstName, String lastName, String postalCode) {
        // Locate and fill in the input fields for first name, last name, and postal code
        WebElement firstNameField = driver.findElement(By.id("first-name"));
        WebElement lastNameField = driver.findElement(By.id("last-name"));
        WebElement postalCodeField = driver.findElement(By.id("postal-code"));

        firstNameField.sendKeys(firstName);
        sleep(2000);
        lastNameField.sendKeys(lastName);
        sleep(2000);
        postalCodeField.sendKeys(postalCode);
        sleep(2000);

        // Click the "CONTINUE" button to proceed to the next step of checkout
        WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"continue-shopping\"]"));

        continueButton.click();
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
