package org.example.Cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class verifyProductPresence {
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

        // Let's say we want to verify the presence of a specific product with a known name
        String expectedProductName = "Sauce Labs Backpack";
        // Wait for the product to load (you may need to adjust the timeout)
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        WebElement productElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='" + expectedProductName + "']")));

        if (productElement != null) {
            System.out.println("Product found on the landing page: " + expectedProductName);
        } else {
            System.out.println("Product not found on the landing page: " + expectedProductName);
        }





    }
}
