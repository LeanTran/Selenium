import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

import static java.lang.System.*;
import static utilities.Constants.CHROME_DRIVER_PATH;

public class UIB02 {
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
    public void verifyDragAndDrop() {
        driver.findElement(By.xpath("//a[@href='/drag_and_drop']")).click();
        String header = driver.findElement(By.xpath("//div[contains(@class,'example')]/h3")).getText();
        Assert.assertEquals("Drag and Drop", header);
        out.println("This title of page navigated to:" + header);

        String script = "(function( $ ) {" +
                "    $.fn.simulateDragDrop = function(options) {" +
                "        return this.each(function() {" +
                "            new $.simulateDragDrop(this, options);" +
                "        });" +
                "    };" +
                "    $.simulateDragDrop = function(elem, options) {" +
                "        this.options = options;" +
                "        this.simulateEvent(elem, options);" +
                "    };" +
                "    $.extend($.simulateDragDrop.prototype, {" +
                "        simulateEvent: function(elem, options) {" +
                "            /*Simulating drag start*/" +
                "            var type = 'dragstart';" +
                "            var event = this.createEvent(type);" +
                "            this.dispatchEvent(elem, type, event);" +
                "            /*Simulating drop*/" +
                "            type = 'drop';" +
                "            var dropEvent = this.createEvent(type, {});" +
                "            dropEvent.dataTransfer = event.dataTransfer;" +
                "            this.dispatchEvent($(options.dropTarget)[0], type, dropEvent);" +
                "            /*Simulating drag end*/" +
                "            type = 'dragend';" +
                "            var dragEndEvent = this.createEvent(type, {});" +
                "            dragEndEvent.dataTransfer = event.dataTransfer;" +
                "            this.dispatchEvent(elem, type, dragEndEvent);" +
                "        }," +
                "        createEvent: function(type) {" +
                "            var event = document.createEvent(\"CustomEvent\");" +
                "            event.initCustomEvent(type, true, true, null);" +
                "            event.dataTransfer = {" +
                "                data: {" +
                "                }," +
                "                setData: function(type, val){" +
                "                    this.data[type] = val;" +
                "                }," +
                "                getData: function(type){" +
                "                    return this.data[type];" +
                "                }" +
                "            };" +
                "            return event;" +
                "        }," +
                "        dispatchEvent: function(elem, type, event) {" +
                "            if(elem.dispatchEvent) {" +
                "                elem.dispatchEvent(event);" +
                "            }else if( elem.fireEvent ) {" +
                "                elem.fireEvent(\"on\"+type, event);" +
                "            }" +
                "        }" +
                "    });" +
                "})(jQuery);";

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script+"$('#column-a').simulateDragDrop({ dropTarget: '#column-b'});");

        WebElement element1 = driver.findElement(By.xpath("//*[@id='column-a']"));
        String expectedHeaderPosition1 = element1.getText();
        out.println("Header of first Column: "+expectedHeaderPosition1);
        Assert.assertEquals("B",expectedHeaderPosition1);

        WebElement element2 = driver.findElement(By.xpath("//*[@id='column-b']"));
        String expectedHeaderPosition2 = element2.getText();
        out.println("Header of first Column: "+expectedHeaderPosition2);
        Assert.assertEquals("A",expectedHeaderPosition2);
    }



    @After
    public void tearDown() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertAll();
        driver.quit();
    }
}
