package org.example.LoginTest;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class excelLogin {
    public static void main(String[] args) throws IOException {
        String credentialsFilePath ="/Users/aadityashah/Desktop/CREDENTIALS.xlsx";
        String webURL = "https://www.saucedemo.com/";
        WebDriver webDriver = new ChromeDriver();
        webDriver.get(webURL);
        webDriver.manage().window().maximize();

        FileInputStream fis = new FileInputStream(credentialsFilePath);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            String username = row.getCell(0).getStringCellValue();
            String password = row.getCell(1).getStringCellValue();

            // Fill the registration form
            webDriver.findElement(By.id("user-name")).sendKeys(username);
            webDriver.findElement(By.id("password")).sendKeys(password);
            webDriver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
        }

    }
}
