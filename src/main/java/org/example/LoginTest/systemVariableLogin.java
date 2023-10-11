package org.example.LoginTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class systemVariableLogin {
    public static void main(String[] args) {
        WebDriver webDriver = new ChromeDriver();
        String url = "https://www.saucedemo.com/";
        webDriver.get(url);
        webDriver.manage().window().maximize();

        String username = System.getenv("MY_USERNAME");
        String password = System.getenv("MY_PASSWORD");
        webDriver.findElement(By.id("user-name")).sendKeys(username);
        webDriver.findElement(By.id("password")).sendKeys(password);
        webDriver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

    }
}
