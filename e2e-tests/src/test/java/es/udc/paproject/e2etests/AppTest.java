package es.udc.paproject.e2etests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AppTest {

    WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testLogin() {
        login("testviewer", "pa2526");
    }

    private void login(String userName, String password) {

        driver.get("http://localhost:5173");

        WebElement loginLink = driver.findElement(By.id("loginLink"));
        loginLink.click();

        WebElement userNameInput = driver.findElement(By.id("userName"));
        userNameInput.sendKeys(userName);

        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys(password);

        WebElement loginButton = driver.findElement(By.id("login-submit"));
        loginButton.click();

        WebElement userDropdown = driver.findElement(By.id("user-dropdown"));
        assertEquals(userName, userDropdown.getText());

    }

    @AfterEach
    public final void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
