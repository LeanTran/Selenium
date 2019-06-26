import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static java.lang.System.*;
import static java.lang.Thread.*;
import static utilities.Constants.EDGE_DRIVER_PATH;

public class UIB11 {
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



    @Test
    public void verifyTable() throws InterruptedException {
        driver.findElement(By.linkText("Sortable Data Tables")).click();
        String header = driver.findElement(By.xpath("//div[contains(@class,'example')]/h3")).getText();
        Assert.assertEquals("Data Tables",header);
        out.println("This title of page navigated to: "+header);

        WebElement colElement = driver.findElement(By.xpath("//*[@id=\"table1\"]/thead/tr/th[3]/span"));
        String colHeader = colElement.getText();
        out.println("The header name is '"+colHeader+"'");

        WebElement r3c2Element = driver.findElement(By.xpath("//*[@id=\"table1\"]/tbody/tr[3]/td[2]"));
        String r3c2Value = r3c2Element.getText();
        out.println("Cell value is '"+r3c2Value+"'");

        WebElement r2c4Element = driver.findElement(By.xpath("//*[@id=\"table1\"]/tbody/tr[2]/td[4]"));
        String r2c4Value = r2c4Element.getText();
        out.println("Cell value is '"+r2c4Value+"'");


        ArrayList<String> obtainedList = new ArrayList<String>();
        out.println("===========List of obtained email: ");
        for(int i=0; i<=3;i++)
        {
            WebElement emailElement = driver.findElement(By.xpath("//*[@id='table2']/tbody/tr[1+"+i+"]/td[3]"));
            obtainedList.add(emailElement.getText());
            out.println(emailElement.getText());
        }
        Collections.sort(obtainedList);

        WebElement emailHeaderCol2T2 = driver.findElement(By.xpath("//*[@id=\"table2\"]/thead/tr/th[3]/span"));
        emailHeaderCol2T2.click();

        sleep(1000);

        ArrayList<String> afterClickHeaderList = new ArrayList<String>();
        out.println("============List of sort email: ");
        for(int i=0; i<=3;i++)
        {
            WebElement emailElement1 = driver.findElement(By.xpath("//*[@id='table2']/tbody/tr[1+"+i+"]/td[3]"));
            afterClickHeaderList.add(emailElement1.getText());
            out.println("" + emailElement1.getText());
        }
        Assert.assertTrue(afterClickHeaderList.equals(obtainedList));
    }


    @After
    public void tearDown() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertAll();
        driver.quit();
    }
}
