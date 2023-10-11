package org.example;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // Login into Saucedemo app by System Variable
        WebDriver webDriver = new ChromeDriver();
        String url = "https://www.saucedemo.com/";
        String credentialsFilePath ="/Users/aadityashah/Desktop/CREDENTIALS.xlsx";
        webDriver.get(url);
        webDriver.manage().window().maximize();

        String username = System.getenv("MY_USERNAME");
        String password = System.getenv("MY_PASSWORD");
        sleep(2000);
        webDriver.findElement(By.id("user-name")).sendKeys(username);
        sleep(2000);
        webDriver.findElement(By.id("password")).sendKeys(password);
        sleep(2000);
        webDriver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

        // Let's say we want to verify the presence of a specific product with a known name
        // assertion can also be used here...
        String expectedProductName = "Sauce Labs Backpack";
        // Wait for the product to load (you may need to adjust the timeout)
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        WebElement productElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='" + expectedProductName + "']")));

        if (productElement != null) {
            System.out.println("Product found on the landing page: " + expectedProductName);
        } else {
            System.out.println("Product not found on the landing page: " + expectedProductName);
        }

        // Login into Saucedemo app by Excel credentials
        /*FileInputStream fis = new FileInputStream(credentialsFilePath);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            String usernameExcel = row.getCell(0).getStringCellValue();
            String passwordExcel = row.getCell(1).getStringCellValue();

            // Fill the registration form
            webDriver.findElement(By.id("user-name")).sendKeys(usernameExcel);
            webDriver.findElement(By.id("password")).sendKeys(passwordExcel);
            webDriver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
        }*/

        // Add any three products to the cart
        sleep(2000);
        addProductToCart(webDriver, "Sauce Labs Backpack");
        sleep(2000);
        addProductToCart(webDriver, "Sauce Labs Bolt T-Shirt");
        sleep(2000);
        addProductToCart(webDriver, "Sauce Labs Onesie");
        sleep(2000);

        // Remove any one product from added web element
        sleep(2000);
        removeProductFromElements(webDriver, "Sauce Labs Bolt T-Shirt");

        // Go to Shopping Cart Link
        sleep(2000);
        goToShoppingCart(webDriver);

        // Verify the product in Cart List
        sleep(2000);
        verifyProductInCart(webDriver, "Sauce Labs Backpack");

        // remove any one product from cartList
        sleep(2000);
        removeProductFromCart(webDriver, "Sauce Labs Onesie");

        // Checkout
        sleep(2000);
        checkout(webDriver);

        // Input values in Checkout Information page
        sleep(2000);
        inputCheckoutInformation(webDriver, "Aaditya", "Shah", "44088");

        //Continue the checkout process
        sleep(2000);
        continueCheckout(webDriver);

        //Finish the checkout process
        sleep(2000);
        finishCheckout(webDriver);

        //Verify the order is successfully placed
        sleep(2000);
        verifyOrderPlaced(webDriver);

        //Go back to Home
        sleep(2000);
        goBackToHome(webDriver);

        //click on menu icon
        sleep(2000);
        clickMenuIcon(webDriver);

        //Logout
        sleep(2000);
        logout(webDriver);

        //Close the browser
        sleep(2000);
        closeWebsite(webDriver);


    }
    // Helper method to add a product to the cart
    private static void addProductToCart(WebDriver driver, String productName) {
        WebElement productElement = driver.findElement(By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']"));
        WebElement addToCartButton = productElement.findElement(By.xpath(".//button[text()='Add to cart']"));
        addToCartButton.click();
        System.out.println(productName);

        //*[@id="add-to-cart-sauce-labs-backpack"]

    }

    // Helper method to remove a product from the cart
    private static void removeProductFromElements(WebDriver driver, String removeProductName) {
        WebElement productElement = driver.findElement(By.xpath("//div[text()='" + removeProductName + "']/ancestor::div[@class='inventory_item']"));
        WebElement removeButton = productElement.findElement(By.xpath(".//button[text()='Remove']"));
        sleep(2000);
        removeButton.click();

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

    // Helper method to remove a product from the cart list
    private static void removeProductFromCart(WebDriver driver, String removeProductCartList) {
        // Open the shopping cart
        WebElement shoppingCartLink = driver.findElement(By.className("shopping_cart_link"));
        shoppingCartLink.click();

        // Locate all product elements in the cart
        List<WebElement> cartProductElements = driver.findElements(By.xpath("//div[@class='cart_item']"));

        for (WebElement cartProductElement : cartProductElements) {
            // Check if the product name in the cart matches the desired product
            String actualProductName = cartProductElement.findElement(By.className("inventory_item_name")).getText();
            if (actualProductName.equals(removeProductCartList)) {
                // Locate the "Remove" button within the current product element in the cart
                WebElement removeButton = cartProductElement.findElement(By.xpath(".//button[text()='Remove']"));
                sleep(2000);
                removeButton.click();
                System.out.println(removeProductCartList);
                break; // Stop searching once the product is removed from the cart
            }
        }
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

    }

    // Helper method to complete the checkout continue process
    private static void continueCheckout(WebDriver driver) {
        // Click the "FINISH" button on the Checkout Overview page
        WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"continue\"]"));

        continueButton.click();
    }

    // Helper method to complete the checkout process
    private static void finishCheckout(WebDriver driver) {
        // Click the "FINISH" button on the Checkout Overview page
        WebElement finishButton = driver.findElement(By.xpath("//button[text()='Finish']"));
        finishButton.click();
    }

    // Helper method to verify that the order is successfully placed
    private static void verifyOrderPlaced(WebDriver driver) {
        // Check for a confirmation message or element indicating a successful order placement
        WebElement confirmationMessage = driver.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/h2"));


        if (confirmationMessage.isDisplayed()) {
            System.out.println("Order is successfully placed.");
        } else {
            System.out.println("Order placement failed.");
        }
    }

    // Helper method to go back to the home page
    private static void goBackToHome(WebDriver driver) {
        driver.navigate().to("https://www.saucedemo.com/inventory.html");
    }

    //Helper method to get click on menu icon
    private static void clickMenuIcon(WebDriver driver) {
        // Locate and click the "Menu Icon" link
        WebElement shoppingCartLink = driver.findElement(By.id("react-burger-menu-btn"));
        shoppingCartLink.click();
    }


    // Helper method to log out
    private static void logout(WebDriver driver) {
        // Locate and click the "LOGOUT" button
        WebElement logoutButton = driver.findElement(By.id("logout_sidebar_link"));
        logoutButton.click();
    }

    // Helper method to close the website
    private static void closeWebsite(WebDriver driver) {
        driver.close();
        driver.quit();
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