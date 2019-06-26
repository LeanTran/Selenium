import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;


import java.io.File;
import java.util.concurrent.TimeUnit;

import static java.lang.System.*;
import static java.lang.Thread.*;
import static utilities.Constants.EDGE_DRIVER_PATH;

public class UIB10 {
    private WebDriver driver;

    @Before
    public void setUp()
    {
        setProperty("webdriver.edge.driver", EDGE_DRIVER_PATH);
        driver = new EdgeDriver();
        String webURL = "https://the-internet.herokuapp.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(webURL + "/");

    }

    public boolean isFileDownloaded(String downloadPath, String fileName) {
        boolean flag = false;
        File dir = new File(downloadPath);
        File[] dirContents = dir.listFiles();

        for (int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].getName().equals(fileName))
                return true;
        }

        return flag;
    }

    @Test
    public void verifyMenu() throws InterruptedException {
        driver.findElement(By.linkText("JQuery UI Menus")).click();
        String header = driver.findElement(By.xpath("//div[contains(@class,'example')]/h3")).getText();
        Assert.assertEquals("JQueryUI - Menu",header);
        out.println("This title of page navigated to: "+header);

        /* /////////////////////////////////////// */
        WebElement menuEnabled = driver.findElement(By.xpath("//ul[contains(@id,'menu')]/li[contains(@id,'ui-id-3')]/a"));
        Actions action = new Actions(driver);
        action.moveToElement(menuEnabled).build().perform();
        sleep(500);
        WebElement subMenu = driver.findElement(By.xpath("//*[@id=\"ui-id-8\"]/a"));
        subMenu.click();

        /* /////////////////////////////////////// */

        sleep(1000);

        String jQueryUIHeader =  driver.findElement(By.xpath("//*[@id=\"content\"]/div/h3")).getText();
        Assert.assertEquals("JQuery UI", jQueryUIHeader);
        out.println(jQueryUIHeader+" header title is displayed ");

        WebElement menuLink = driver.findElement(By.linkText("Menu"));
        menuLink.click();

        Assert.assertEquals("JQueryUI - Menu",header);
        out.println(header+ " header title is displayed");


        /* /////////////////////////////////////// */
        WebElement menuEnabled2 = driver.findElement(By.xpath("//ul[contains(@id,'menu')]/li[contains(@id,'ui-id-3')]/a"));
        Actions action1 = new Actions(driver);
        action1.moveToElement(menuEnabled2).build().perform();
        sleep(500);
        WebElement downloadsOption = driver.findElement(By.xpath("//*[@id=\"ui-id-4\"]/a"));
        downloadsOption.click();

        action1.moveToElement(downloadsOption).build().perform();
        sleep(500);
        WebElement subMenu2 = driver.findElement(By.xpath("//*[@id=\"ui-id-6\"]/a"));
        subMenu2.click();

        Assert.assertTrue("Failed to download Expected document", isFileDownloaded("D:\\lantran\\automation", "menu.csv"));

    }



    @After
    public void tearDown() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertAll();
        driver.quit();
    }
}
