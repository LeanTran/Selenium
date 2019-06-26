import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static java.lang.System.*;
import static utilities.Constants.IE_DRIVER_PATH;

public class UIB09 {
    private WebDriver driver;

    @Before
    public void setUp()
    {
        setProperty("webdriver.ie.driver", IE_DRIVER_PATH);
        driver = new InternetExplorerDriver();
        driver.manage().window().maximize();
        String webURL = "https://the-internet.herokuapp.com/";
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.get(webURL + "/");
    }



    @Test
    public void verifySlider() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Horizontal Slider"))).click();
        String header = driver.findElement(By.xpath("//div[contains(@class,'example')]/h3")).getText();
        Assert.assertEquals("Horizontal Slider",header);
        out.println("This title of page navigated to: "+header);

        WebElement sliderContainer = driver.findElement(By.xpath("//div[contains(@id,'content')]/div/div/input"));
        Assert.assertTrue(sliderContainer.isDisplayed());

        String xpath = "//div[contains(@id,'content')]/div/div/span";
        WebElement displayRange = driver.findElement(By.xpath(xpath));
        String priceValue = displayRange.getText();
        out.println("Current value "+priceValue);

        int sliderW = sliderContainer.getSize().width;
        out.println(sliderW);
        Actions action = new Actions(driver);
        action.dragAndDropBy(sliderContainer,-84,0).build().perform();
        WebElement displayRange1 = driver.findElement(By.xpath(xpath));
        String priceValue1 = displayRange1.getText();
        out.println("Value_1: "+priceValue1);
        Thread.sleep(1000);
        Assert.assertEquals("1", priceValue1);

        action.dragAndDropBy(sliderContainer,0,0).build().perform();
        WebElement displayRange2 = driver.findElement(By.xpath(xpath));
        String priceValue2 = displayRange2.getText();
        out.println("Value_2: "+priceValue2);
        Thread.sleep(1000);
        Assert.assertEquals("2.5", priceValue2);


        action.dragAndDropBy(sliderContainer,112,0).build().perform();
        WebElement displayRange3 = driver.findElement(By.xpath("//div[contains(@id,'content')]/div/div/span"));
        String priceValue3 = displayRange3.getText();
        out.println("Value_3: "+priceValue3);
        Thread.sleep(1000);
        Assert.assertEquals("4.5", priceValue3);

    }


    @After
    public void tearDown() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertAll();
        driver.quit();
    }
}
