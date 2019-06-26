import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static java.lang.System.*;
import static utilities.Constants.FIREFOX_DRIVER_PATH;

public class UIB06 {
    private WebDriver driver;

    @Before
    public void setUp()
    {
        setProperty("webdriver.gecko.driver",FIREFOX_DRIVER_PATH);
        driver = new FirefoxDriver();
        String webURL = "https://the-internet.herokuapp.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(webURL + "/");
    }



    @Test
    public void verifyAlerts(){
        driver.findElement(By.xpath("//a[@href='/javascript_alerts']")).click();
        String header = driver.findElement(By.xpath("//div[contains(@class,'example')]/h3")).getText();
        Assert.assertEquals("JavaScript Alerts",header);
        out.println("This title of page navigated to: "+header);

        WebElement btnClickForJSAlert = driver.findElement(By.xpath("//button[contains(@onclick,'jsAlert()')]"));
        btnClickForJSAlert.click();
        String alertMessage = driver.switchTo().alert().getText();
        Assert.assertEquals("I am a JS Alert",alertMessage);
        out.println("Alert message is: "+alertMessage);

        driver.switchTo().alert().accept();
        WebElement resultElement = driver.findElement(By.id("result"));
        String txtResult = resultElement.getText();
        Assert.assertEquals("You successfuly clicked an alert",txtResult);
        out.println("Result_1: "+txtResult);

        WebElement btnClickForJConfirm = driver.findElement(By.xpath("//button[contains(@onclick,'jsConfirm()')]"));
        btnClickForJConfirm.click();

        driver.switchTo().alert().dismiss();
        String txtResult2 = resultElement.getText();
        Assert.assertEquals("You clicked: Cancel",txtResult2);
        out.println("Result_2: "+txtResult2);

        WebElement btnClickForJPromt = driver.findElement(By.xpath("//button[contains(@onclick,'jsPrompt()')]"));
        btnClickForJPromt.click();

        driver.switchTo().alert().sendKeys("Hello");

        driver.switchTo().alert().accept();

        String txtResult3 = driver.findElement(By.id("result")).getText();
        Assert.assertEquals("You entered: Hello",txtResult3);
        out.println("Result_3: "+txtResult3);

    }

    @After
    public void tearDown() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertAll();
        driver.quit();
    }
}
