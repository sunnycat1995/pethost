package com.project.pethost.controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.logging.Logger;

public class UserControllerTest {
    private Logger LOGGER = Logger.getLogger(getClass().getName());
    private final WebDriver driver;
    private final String TEST_USERNAME = "irinavyshnikova@gmail.com";
    private final String TEST_PASSWORD = "123";

    public UserControllerTest() {
        this.driver = new FirefoxDriver();
    }

    /*@Before
    public void setup() {
        driver.navigate().to("http://localhost:8091/pethost");
        sleep();

    }

    @Test
    public void adminPage() {
    }

    @Test
    public void positiveLoginPage() {
        fillSignInPage(TEST_USERNAME, TEST_PASSWORD);
        WebElement welcomeElement = null;
        try {
            welcomeElement = driver.findElement(By.xpath("//h3[contains(text(), 'Welcome')]"));
        } catch (final NoSuchElementException e) {
            final String errorMessage = "Not found welcome element";
            LOGGER.log(Level.SEVERE, errorMessage);
            throw new RuntimeException(errorMessage);
        }
        final WebElement emailInfo = driver.findElement(By.id("email"));
        Assert.assertEquals("Incorrect user", TEST_USERNAME, emailInfo.getText().replace("Email:", "").trim());
        driver.quit();
    }

    private void fillSignInPage(final String username, final String password) {
        final WebElement userAccountInfo =
                driver.findElement(By.cssSelector("#navbarNavDropdown a.nav-link[id='userAccountInfo']"));
        userAccountInfo.click();
        sleep();
        final WebElement usernameField = driver.findElement(By.cssSelector("form input[name='username']"));
        final WebElement passwordField = driver.findElement(By.cssSelector("form input[name='password']"));


        usernameField.sendKeys(username);
        sleep();
        passwordField.sendKeys(password);
        sleep();
        final WebElement signinButton = driver.findElement(By.cssSelector("form button[type='submit']"));
        signinButton.click();
    }

    @Test
    public void userInfo() {
    }

    @Test
    public void logoutSuccessfulPage() {
    }

    @Test
    public void mvcConversionService() {
    }

    @Test
    public void getAllUsers() {
    }

    @Test
    public void searchUsersByAnimalPreferences() {
    }

    @Test
    public void viewRegister() {
    }

    @Test
    public void saveRegister() {
    }

    @Test
    public void viewRegisterSuccessful() {
    }

    private void sleep() {
        try {
            Thread.sleep(300);
        } catch (final InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Time out exception");
            throw new RuntimeException("Time out exception");
        }
    }*/
}