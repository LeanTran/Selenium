import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;
import static utilities.Constants.FIREFOX_DRIVER_PATH;

public class UIB07 {
    private WebDriver driver;

    @Before
    public void setUp()
    {
        System.setProperty("webdriver.gecko.driver",FIREFOX_DRIVER_PATH);
        driver = new FirefoxDriver();
        String webURL = "https://the-internet.herokuapp.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(webURL + "/");
    }



    @Test
    public void verifyKeyPresses(){
        driver.findElement(By.xpath("//a[@href='/key_presses']")).click();
        String header = driver.findElement(By.xpath("//div[contains(@class,'example')]/h3")).getText();
        Assert.assertEquals("Key Presses",header);
        out.println("This title of page navigated to: "+header);


        WebElement currentElement = driver.switchTo().activeElement();
        currentElement.sendKeys(Keys.TAB);

        WebElement resultElement = driver.findElement(By.id("result"));
        String txtResult = resultElement.getText();
        Assert.assertEquals("You entered: TAB",txtResult);
        out.println("Result_1: "+txtResult);

        currentElement.sendKeys(Keys.ENTER);

        String txtResult2 = resultElement.getText();
        Assert.assertEquals("You entered: ENTER",txtResult2);
        out.println("Result_2: "+txtResult2);

        Actions builder = new Actions(driver);
        builder.keyDown(Keys.SHIFT).sendKeys("g").perform();
        String txtResult3 = driver.findElement(By.id("result")).getText();
        Assert.assertEquals("You entered: G",txtResult3);
        out.println("Result_3: "+txtResult3);
    }


    @After
    public void tearDown() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertAll();
        driver.quit();
    }
}
