package es.udc.paproject.e2etests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    @Test
    public void testViewSessionDetails() {
        login("testviewer", "pa2526");

        WebElement firstMovieToday = driver.findElement(By.cssSelector("a[href^='/catalog/movies/']"));
        new Select(driver.findElement(By.id("billboardDate"))).selectByIndex(1); // Seleccionar la segunda opción.

        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.stalenessOf(firstMovieToday));

        String movieTitle = driver.findElement(By.cssSelector("a[href^='/catalog/movies/']")).getText();

        WebElement firstSessionLink = driver.findElement(By.cssSelector("a[href^='/catalog/sessions/']"));
        String sessionTime = firstSessionLink.getText();
        firstSessionLink.click();

        assertTrue(driver.findElement(By.id("movieTitle")).isDisplayed());
        assertTrue(driver.findElement(By.id("runtime")).isDisplayed());
        assertTrue(driver.findElement(By.id("price")).isDisplayed());
        assertTrue(driver.findElement(By.id("date")).isDisplayed());
        assertTrue(driver.findElement(By.id("time")).isDisplayed());
        assertTrue(driver.findElement(By.id("room")).isDisplayed());
        assertTrue(driver.findElement(By.id("availableTickets")).isDisplayed());

        assertEquals(movieTitle, driver.findElement(By.id("movieTitle")).getText());
        assertEquals(sessionTime, driver.findElement(By.id("time")).getText());

        // Incluye el formulario para comprar entradas
        assertTrue(driver.findElement(By.id("numberTickets")).isDisplayed());
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
