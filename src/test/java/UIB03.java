import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

import static java.lang.System.*;
import static utilities.Constants.CHROME_DRIVER_PATH;


public class UIB03 {

    private WebDriver driver;

    @Before
    public void setUp()
    {
        setProperty("webdriver.chrome.driver",CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        String webURL = "https://the-internet.herokuapp.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(webURL + "/");
    }



    @Test
    public void verifyDropdown()
    {
        driver.findElement(By.xpath("//a[@href='/dropdown']")).click();
        String header = driver.findElement(By.xpath("//div[contains(@class,'example')]/h3")).getText();
        Assert.assertEquals("Dropdown List",header);
        out.println("This title of page navigated to:"+header);

        WebElement mySelectElement = driver.findElement(By.id("dropdown"));
        Select myselect = new Select(mySelectElement);
        myselect.selectByVisibleText("Option 2");
        String currentSelection = myselect.getFirstSelectedOption().getText();
        Assert.assertEquals("Option 2",currentSelection);
        out.println("The first selected option: "+currentSelection);


        myselect.selectByIndex(1);
        String secondSelect = myselect.getFirstSelectedOption().getText();
        Assert.assertEquals("Option 1",secondSelect);
        out.println("The selected option 1: "+secondSelect);

        myselect.selectByValue("2");
        String thirdSelect = myselect.getFirstSelectedOption().getText();
        Assert.assertEquals("Option 2",thirdSelect);
        out.println("The 3rd selected option: "+thirdSelect);
    }

    @After
    public void tearDown() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertAll();
        driver.quit();
    }
}
