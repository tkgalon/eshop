package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {
    /**
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework's test context.
     */
    @LocalServerPort
    private int serverPort;

    /**
     * the base URL for testing. Default to code {@code http://localhost}.
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest(){
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    // Positive Test
    @Test
    void createProductTest(ChromeDriver driver) throws Exception {

        // Go to the Create Product page
        driver.get(baseUrl + "/product/create");

        // Fill out the form and submit
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        nameInput.sendKeys("Samsung");
        quantityInput.sendKeys("200");

        // Submit form and go to list product
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Grab information in list
        WebElement tableList = driver.findElement(By.cssSelector("table"));
        String productList = tableList.getText();

        // Test urls redirect
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/list"), "Expected URL to contain '/product/list' but got: " + currentUrl);

        // Test new product appears in the list
        assertTrue(productList.contains("Samsung"));
        assertTrue(productList.contains("200"));
    }

    // Negative Test
    @Test
    void createProductWithNameEmpty(ChromeDriver driver) {

        // Go to the Create Product page
        driver.get(baseUrl + "/product/create");

        // Fill out the form and submit
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        nameInput.sendKeys("");
        quantityInput.sendKeys("200");

        // Submit form and go to list product
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Test urls stay on page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/create"), "Expected URL to contain '/product/create' but got: " + currentUrl);
    }

    @Test
    void createProductWithQuantityNegative(ChromeDriver driver) {
        // Go to the Create Product page
        driver.get(baseUrl + "/product/create");

        // Fill out the form and submit
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        nameInput.sendKeys("Samsung");
        quantityInput.sendKeys("-10");

        // Submit form and go to list product
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Test urls stay on page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/create"), "Expected URL to contain '/product/create' but got: " + currentUrl);
    }



}
