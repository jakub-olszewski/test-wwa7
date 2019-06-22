package eu.b24u.cucumber.utils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 
public class SeleniumUtil {
 
    private WebDriver driver;
 
    private int timeout = 15;
 
    private static final Logger logger = Logger.getLogger(SeleniumUtil.class.getName());
 
    public boolean isExistElement(org.openqa.selenium.By by) {
        Boolean isPresent = getDriver().findElements(by).size() > 0;
        return isPresent;
    }
 
    public void clickInput(WebElement element) {
        element.submit();
    }
 
    /*
     * public boolean isExistElement(WebElement element) { return
     * isExistElement(webElementToBy(element)); }
     */
    public void wait(int seconds) {
        try {
            logger.log(Level.SEVERE, "wait start time:" + seconds);
            new WebDriverWait(getDriver(), seconds)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("SeleniumUtilsWaitElement")));
        } catch (TimeoutException e) {
            logger.log(Level.SEVERE, "Timeout");
        } catch (WebDriverException e) {
            logger.log(Level.SEVERE, "WebDriverException");
        }
        logger.log(Level.SEVERE, "wait end");
    }
 
    public void waitForLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wd) {
                // this will tel if page is loaded
                return "complete".equals(((JavascriptExecutor) wd).executeScript("return document.readyState"));
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        // wait for page complete
        wait.until(pageLoadCondition);
        // lower implicitly wait time
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
 
    }
 
    public void waitForElement(org.openqa.selenium.By by, int seconds) {
        try {
            new WebDriverWait(driver, seconds).until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (TimeoutException e) {
            logger.log(Level.SEVERE, "element " + by.toString() + " not exist " + e.getMessage());
        }
    }
 
    public boolean isExistElement(WebElement element) {
        boolean result = false;
        try {
            new WebDriverWait(driver, 0).until(ExpectedConditions.visibilityOf(element));
            result = true;
        } catch (TimeoutException e) {
            logger.log(Level.SEVERE, "element " + element.toString() + " not exist " + e.getMessage());
        }
        return result;
    }
 
    public void waitForElement(WebElement element, int seconds) {
        new WebDriverWait(driver, seconds).until(presenceOfElementLocated(element));
    }
 
    private ExpectedCondition<WebElement> presenceOfElementLocated(WebElement element) {
        return new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return element;
            }
 
            @Override
            public String toString() {
                return "presence of element located by: " + element.getTagName();
            }
        };
    }
 
    // return ByType of WebElement
    private By webElementToBy(WebElement we) {
        if (we == null)
            return null;
        // By format = "[foundFrom] -> locator: term"
        // see RemoteWebElement toString() implementation
        String[] data = we.toString().split(" -> ")[1].replace("]", "").split(": ");
        String locator = data[0];
        String term = data[1];
 
        switch (locator) {
        case "xpath":
            return By.xpath(term);
        case "css selector":
            return By.cssSelector(term);
        case "id":
            return By.id(term);
        case "tag name":
            return By.tagName(term);
        case "name":
            return By.name(term);
        case "link text":
            return By.linkText(term);
        case "class name":
            return By.className(term);
        }
        return (By) we;
    }
 
    public void closeAnotherTabs() {
        String originalHandle = getDriver().getWindowHandle();
 
        for (String handle : getDriver().getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                getDriver().switchTo().window(handle);
                getDriver().close();
            }
        }
        getDriver().switchTo().window(originalHandle);
    }
 
    /**
     * Do klikania na EZTV sprawdza i zamyka inne karty jak same sie otwieraja
     * 
     * @param element
     */
    public void clickElementForce(WebElement element) {
        String link = getElementLink(element);
        logger.info("clickElement :" + element.getText() + " link=" + link);
        Actions actions = new Actions(getDriver());
        String currentUrl = "";
        do {
        actions.moveToElement(element).click().perform();
            currentUrl = getDriver().getCurrentUrl();
            closeAnotherTabs();
            waitForElement(element, 5);
            System.out.print("*");
 
        } while (!Objects.equals(currentUrl, link));
        // clickElement(webElementToBy(element));
    }
 
    public void clickElement(org.openqa.selenium.By by) {
        logger.info("try click :" + by.toString());
        waitForElement(by, timeout);
        if (isExistElement(by)) {
            logger.info("element clicked :" + by.toString());
            getDriver().findElement(by).click();
        } else {
            logger.severe("element not exist :" + by.toString());
        }
    }
     
    public void clickElement(WebElement element) {
        logger.info("try click :" + element.toString());
        waitForElement(element, timeout);
        if (isExistElement(element)) {
            logger.info("element clicked :" + element.toString());
            Actions actions = new Actions(getDriver());
            actions.moveToElement(element).click().perform();
            //element.click();
        } else {
            logger.severe("element not exist :" + element.toString());
        }
    }
 
    public void writeTextToElement(org.openqa.selenium.By by, String text) {
        waitForElement(by, timeout);
        if (isExistElement(by)) {
            logger.info("write '" + text + "' to element " + by.toString());
            getDriver().findElement(by).sendKeys(text);
        } else {
            logger.severe("element not exist :" + by.toString());
        }
    }
 
    public String getElementText(org.openqa.selenium.By by) {
        waitForElement(by, timeout);
        if (isExistElement(by)) {
            return getDriver().findElement(by).getText();
        } else {
            logger.severe("element not exist :" + by.toString());
            return "";
        }
    }
 
 
    public String getElementText(WebElement element) {
        waitForElement(element, timeout);
        if (isExistElement(element)) {
            return element.getText();
        } else {
            logger.severe("element not exist :" + element.toString());
            return "";
        }
    }
 
    public String getElementLink(org.openqa.selenium.By by) {
        waitForElement(by, timeout);
        if (isExistElement(by)) {
            return getDriver().findElement(by).getAttribute("href").trim();
        } else {
            logger.severe("element not exist :" + by.toString());
            return "";
        }
    }
     
    public String getElementLink(WebElement element) {
        waitForElement(element, timeout);
        if (isExistElement(element)) {
            return element.getAttribute("href").trim();
        } else {
            logger.severe("element not exist :" + element.toString());
            return "";
        }
    }
 
    public void sendKeys(WebElement element, String text) {
        waitForElement(element, timeout);
        if (isExistElement(element)) {
            element.clear();
            element.sendKeys(text);
        }
    }
 
    public WebDriver getDriver() {
        return driver;
    }
 
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
 
    public SeleniumUtil(WebDriver driver) {
        super();
        this.driver = driver;
    }
 
    public void switchToFrame(String frameId) {
        if (isExistElement(By.id(frameId))) {
            getDriver().switchTo().frame(getDriver().findElement(By.id(frameId)));
        }
    }
 
    public void returnFromFrame() {
        getDriver().switchTo().defaultContent();
    }

}
