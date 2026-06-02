package es.udc.paproject.e2etests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

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

    private static final String CREDIT_CARD = "1234567890123456";

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

    @Test
    public void testBuyTickets() {

        login("testviewer", "pa2526");

        // Acceder a la informacion detallada de la sesion e2e del script (sessionId 15)
        driver.get("http://localhost:5173/catalog/sessions/15");

        // Guardar el nombre de la pelicula
        String movieTitle = driver.findElement(By.id("movieTitle")).getText();

        // Rellenar el formulario para comprar dos entradas
        WebElement numberTickets = driver.findElement(By.id("numberTickets"));
        numberTickets.clear();
        numberTickets.sendKeys("2");
        driver.findElement(By.id("creditCardNumber")).sendKeys(CREDIT_CARD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Guardar el identificador de la compra generada
        String purchaseId = driver.findElement(By.id("purchaseId")).getText();

        // Ver las compras del usuario
        driver.findElement(By.cssSelector("a[href='/catalog/find-purchases']")).click();

        // La primera compra coincide con el identificador y la pelicula guardados
        List<WebElement> cells = driver.findElement(By.cssSelector("table tbody tr"))
            .findElements(By.tagName("td"));
        assertEquals(purchaseId, cells.get(1).getText());
        assertEquals(movieTitle, cells.get(2).getText());
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
