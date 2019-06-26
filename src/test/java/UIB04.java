import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.concurrent.TimeUnit;

import static java.lang.System.*;
import static utilities.Constants.CHROME_DRIVER_PATH;
import static utilities.Constants.IMAGE_UPLOAD_PATH;

public class UIB04 {
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
    public void verifyFileUpload()
    {
        driver.findElement(By.xpath("//a[@href='/upload']")).click();
        String header = driver.findElement(By.xpath("//div[contains(@class,'example')]/h3")).getText();
        Assert.assertEquals("File Uploader",header);
        out.println("This title of page navigated to: "+header);

        WebElement btnChooseFile = driver.findElement(By.xpath("//div[contains(@class,'example')]/form/input[contains(@id,'file-upload')]"));
        ((RemoteWebElement) btnChooseFile).setFileDetector(new LocalFileDetector());
        btnChooseFile.sendKeys(IMAGE_UPLOAD_PATH);

        driver.findElement(By.xpath("//div[contains(@class,'example')]/form/input[contains(@class,'button')]")).click();

        String successHeader = driver.findElement(By.xpath("//div[contains(@class,'example')]/h3")).getText();
        Assert.assertEquals("File Uploaded!",successHeader);
        out.println("Header of the page when uploading successfully: "+successHeader);

    }

    @After
    public void tearDown() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertAll();
        driver.quit();
    }
}
