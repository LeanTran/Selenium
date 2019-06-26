import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static java.lang.System.*;
import static utilities.Constants.FIREFOX_DRIVER_PATH;

public class UIB05 {
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
    public void verifyFrames(){
        driver.findElement(By.xpath("//a[@href='/tinymce']")).click();
        String header = driver.findElement(By.xpath("//div[contains(@class,'example')]/h3")).getText();
        Assert.assertEquals("An iFrame containing the TinyMCE WYSIWYG Editor",header);
        out.println("This title of page navigated to: "+header);


        WebElement iFrame = driver.findElement(By.xpath("//iframe[contains(@id,'mce_0_ifr')]"));
        driver.switchTo().frame(iFrame);

        WebElement htmlContent = driver.findElement(By.xpath("//body[contains(@id,'tinymce')]/p"));
        String bodyContent = htmlContent.getText();
        Assert.assertEquals("Your content goes here.",bodyContent);
        out.println("Default content is: "+bodyContent);

        htmlContent.clear();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].innerHTML = 'Hello, how are you?'",htmlContent);

        String newBodyContent = driver.findElement(By.xpath("//body[contains(@id,'tinymce')]/p")).getText();
        Assert.assertEquals("Hello, how are you?",newBodyContent);
        out.println("The new content is set:" + newBodyContent);
    }

    @After
    public void tearDown() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertAll();
        driver.quit();
    }
}
