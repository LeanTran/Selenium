import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static java.lang.System.*;
import static utilities.Constants.CHROME_DRIVER_PATH;


public class UIB01 {

    private WebDriver driver;

    @Before
    public void setUp()
    {
        setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        String webURL = "https://the-internet.herokuapp.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(webURL + "/");
    }



    @Test
    public void verifyCheckboxes()
    {
        driver.findElement(By.xpath("//a[@href='/checkboxes']")).click();
        String header = driver.findElement(By.xpath("//div[contains(@class,'example')]/h3")).getText();
        Assert.assertEquals("Checkboxes",header);
        out.println("This title of page navigated to: "+header);

        WebElement checkbox1 = driver.findElement(By.xpath("//div[@class='example']/form[@id='checkboxes']/input[1]"));
        WebElement checkbox2 = driver.findElement(By.xpath("//div[@class='example']/form[@id='checkboxes']/input[2]"));

        if(!checkbox1.isSelected())
        {
            out.println("check the checkbox1");
            checkbox1.click();
            Boolean isSelected1 = checkbox1.isSelected();
            Assert.assertTrue(isSelected1);
        }
        else {
            out.println("checkbox1 is checked");
        }

        if(checkbox2.isSelected())
        {
            out.println("uncheck the checkbox2");
            checkbox2.click();
            Boolean isSelected2 = checkbox2.isSelected();
            Assert.assertFalse(isSelected2);
        }
        else
        {
            out.println("checkbox2 is unchecked");
        }

    }

    @After
    public void tearDown() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertAll();
        driver.quit();
    }
}
