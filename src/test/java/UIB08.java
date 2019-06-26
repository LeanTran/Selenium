import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import java.util.concurrent.TimeUnit;
import static java.lang.System.*;
import static utilities.Constants.IE_DRIVER_PATH;

public class UIB08 {
    private static WebDriver driver;

    @Before
    public void setUp()
    {
        setProperty("webdriver.ie.driver",IE_DRIVER_PATH);
        driver = new InternetExplorerDriver();
        driver.manage().window().maximize();
        String webURL = "https://the-internet.herokuapp.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(webURL + "/");
    }

    public static void clickByActions(WebElement element)
    {
        Actions builder;
        builder = new Actions(driver);
        Action click = builder.click(element).build();
        click.perform();

    }

    @Test
    public void verifyColorAndFont(){
        WebElement challengeDomLink = driver.findElement(By.xpath("//a[@href='/challenging_dom']"));
        clickByActions(challengeDomLink);
        out.println("Run till here!!!");

        String header = driver.findElement(By.xpath("//div[contains(@class,'example')]/h3")).getText();
        out.println(header);
        Assert.assertEquals("Challenging DOM",header);
        out.println("This title of page navigated to: "+header);

        WebElement btnbar = driver.findElement(By.xpath("//div[contains(@class,'large-2 columns')]/a[contains(@class,'button')]"));
        String fontsize = btnbar.getCssValue("font-size");
        out.println("Font size is "+fontsize);

        WebElement btnqux = driver.findElement(By.xpath("//div[contains(@class,'large-2 columns')]/a[contains(@class,'button alert')]"));
        String bgColor = btnqux.getCssValue("background-color");
        out.println("Background color is "+bgColor);

        WebElement btnbaz = driver.findElement(By.xpath("//div[contains(@class,'large-2 columns')]/a[contains(@class,'button success')]"));
        String bdcolor = btnbaz.getCssValue("border-color");
        out.println("Border color is "+bdcolor);

    }


    @After
    public void tearDown() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertAll();
        driver.quit();
    }

}
